const {app, BrowserWindow, protocol} = require("electron");
const path = require("path")
const cProcess = require('child_process').spawn;
const portScanner = require("portscanner");
const {imageSize} = require('image-size');

let io, server, apiProcess, loadURL;
let appApi, browserWindowsApi, commandLineApi, autoUpdaterApi, ipcApi, menuApi, dialogApi;
let notificationApi, trayApi, webContentsApi, globalShortcutApi, shellApi, screenApi;
let clipboardApi, browserViewApi, powerMonitorApi, nativeThemeApi, dockApi;
let splashScreen, hostHook;
let mainWindowId;
let launchFile;
let launchUrl;

let manifestJsonFileName = 'electron.manifest.json';
if (app.commandLine.hasSwitch("manifest")) {
    manifestJsonFileName = app.commandLine.getSwitchValue("manifest");
}

let currentBinPath = path.join(__dirname.replace('app.asar', ''), 'bin');
let manifestJsonFilePath = path.join(currentBinPath, manifestJsonFileName);

//  handle macOS events for opening the app with a file, etc
app.on('will-finish-launching', () => {
    app.on('open-file', (evt, file) => {
        evt.preventDefault();
        launchFile = file;
    })
    app.on('open-url', (evt, url) => {
        evt.preventDefault();
        launchUrl = url;
    })
});

const manifestJsonFile = require(manifestJsonFilePath);
if (manifestJsonFile.singleInstance || manifestJsonFile.backendPort) {
    const mainInstance = app.requestSingleInstanceLock();
    app.on("second-instance", (events, args = []) => {
        args.forEach(parameter => {
            const words = parameter.split("=");

            if (words.length > 1) {
                app.commandLine.appendSwitch(words[0].replace('--', ''), words[1]);
            } else {
                app.commandLine.appendSwitch(words[0].replace('--', ''));
            }
        });

        const windows = BrowserWindow.getAllWindows();
        if (windows.length) {
            if (windows[0].isMinimized()) {
                windows[0].restore();
            }
            windows[0].focus();
        }
    });
    if (!mainInstance) {
        app.quit();
    }
}

app.on("ready", () => {
    protocol.registerFileProtocol('file', (request, callback) => {
        const pathname = request.url.replace('file:///', '');
        callback(pathname);
    });
    if (isSplashScreenEnabled()) {
        startSplashScreen();
    }
    let defaultElectronPort = 8000;
    if (manifestJsonFile.electronPort) {
        defaultElectronPort = (manifestJsonFile.electronPort)
    }
    portScanner.findAPortNotInUse(defaultElectronPort, 65535, 'localhost', function (error, port) {
        console.log('Electron Socket IO Port: ' + port);
        startSocketApiBridge(port);
    });
})

app.on('quit', async (event, exitCode) => {
    await server.close();
    apiProcess.kill();
});

function isSplashScreenEnabled() {
    if (manifestJsonFile.hasOwnProperty("splashscreen")) {
        if (manifestJsonFile.splashscreen.hasOwnProperty('imageFile')) {
            return Boolean(manifestJsonFile.splashscreen.imageFile);
        }
    }
    return false;
}

function startSplashScreen() {
    let imageFile = path.join(currentBinPath, manifestJsonFile.splashscreen.imageFile);
    imageSize(imageFile, (error, dimensions) => {
        if (error) {
            console.log(`load splashscreen error:`);
            console.error(error);

            throw new Error(error.message);
        }
        splashScreen = new BrowserWindow({
            width: dimensions.width,
            height: dimensions.height,
            transparent: true,
            center: true,
            frame: false,
            closable: false,
            resizable: false,
            skipTaskbar: true,
            alwaysOnTop: true,
            show: true
        });
        splashScreen.setIgnoreMouseEvents(true);
        app.once("browser-window-created", () => {
            splashScreen.destroy();
        })

        const loadSplashscreenUrl = path.join(__dirname, 'splashscreen', 'index.html') + '?imgPath=' + imageFile;
        splashScreen.loadURL('file://' + loadSplashscreenUrl).then(r => {
        });

        splashScreen.once('closed', () => {
            splashScreen = null;
        });
    });
}

function startSocketApiBridge(port) {
    server = require('http').createServer();
    io = require("socket.io")();
    io.attach(server);

    server.listen(port, 'localhost');
    server.on("listening", function () {
        startJavaBackend(port);
    });

    // prototype
    app['mainWindowURL'] = "";
    app['mainWindow'] = null;

    // @ts-ignore
    io.on('connection', (socket) => {
        socket.on('disconnect', function (reason) {
            console.log("Got disconnect! Reason:" + reason);
        });

        if (global['electronsocket'] === undefined) {
            global['electronsocket'] = socket;
            global['electronsocket'].setMaxListeners(0);
        }

        console.log('Java Application connected...', 'global.electronsocket', global['electronsocket'].id, new Date());

        if (appApi === undefined) appApi = require('./api/appApi')(socket, app);
        if (browserWindowsApi === undefined) appApi = require('./api/browserWindowsApi')(socket, app);
        if (commandLineApi === undefined) commandLineApi = require('./api/commandLineApi')(socket, app);
        if (autoUpdaterApi === undefined) autoUpdaterApi = require('./api/autoUpdaterApi')(socket);
        if (ipcApi === undefined) ipcApi = require('./api/ipcApi')(socket);
        if (menuApi === undefined) menuApi = require('./api/menuApi')(socket);
        if (dialogApi === undefined) dialogApi = require('./api/dialogApi')(socket);
        if (notificationApi === undefined) notificationApi = require('./api/notificationApi')(socket);
        if (trayApi === undefined) trayApi = require('./api/trayApi')(socket);
        if (webContentsApi === undefined) webContentsApi = require('./api/webContentsApi')(socket);
        if (globalShortcutApi === undefined) globalShortcutApi = require('./api/globalShortcutApi')(socket);
        if (shellApi === undefined) shellApi = require('./api/shellApi')(socket);
        if (screenApi === undefined) screenApi = require('./api/screenApi')(socket);
        if (clipboardApi === undefined) clipboardApi = require('./api/clipboardApi')(socket);
        if (browserViewApi === undefined) browserViewApi = require('./api/browserViewApi').browserViewApi(socket);
        if (powerMonitorApi === undefined) powerMonitorApi = require('./api/powerMonitorApi')(socket);
        if (nativeThemeApi === undefined) nativeThemeApi = require('./api/nativeThemeApi')(socket);
        if (dockApi === undefined) dockApi = require('./api/dockApi')(socket);

        socket.on('register-app-open-file-event', (id) => {
            global['electronsocket'] = socket;

            app.on('open-file', (event, file) => {
                event.preventDefault();

                global['electronsocket'].emit('app-open-file' + id, file);
            });

            if (launchFile) {
                global['electronsocket'].emit('app-open-file' + id, launchFile);
            }
        });

        socket.on('register-app-open-url-event', (id) => {
            global['electronsocket'] = socket;

            app.on('open-url', (event, url) => {
                event.preventDefault();

                global['electronsocket'].emit('app-open-url' + id, url);
            });

            if (launchUrl) {
                global['electronsocket'].emit('app-open-url' + id, launchUrl);
            }
        });
    });
}

function startJavaBackend(electronPort) {
    if (manifestJsonFile.aspCoreBackendPort) {
        startBackend(manifestJsonFile.backendPort)
    } else {
        // hostname needs to be localhost, otherwise Windows Firewall will be triggered.
        portScanner.findAPortNotInUse(electronPort + 1, 65535, 'localhost', function (error, electronWebPort) {
            startBackend(electronWebPort);
        });
    }

    function startBackend(backendPort) {
        console.log("Java Backend Port: " + backendPort);
        loadURL = `http://localhost:${backendPort}`;
        const parameters = [`--electron-port=${electronPort}`, `--server.port=${backendPort}`]
        let binaryFile = manifestJsonFile.executable;

        const os = require("os");
        if (os.platform() === "win32") {
            binaryFile = binaryFile + '.bat';
        }

        let binFilePath = path.join(currentBinPath, "app-boot/bin", binaryFile);
        console.log(binFilePath)
        const options = {cwd: currentBinPath};
        apiProcess = cProcess(binFilePath, parameters, options);

        apiProcess.stdout.on('data', (data) => {
            console.log(`stdout: ${data.toString()}`);
        });
    }
}
