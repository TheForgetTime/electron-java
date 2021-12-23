package io.gitee.nn.electron.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public final class Electron {
    static final ObjectMapper objectMapper = new ObjectMapper();
    static final TypeFactory typeFactory = objectMapper.getTypeFactory();

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public static <T> T fromJsonString(String str, Class<T> elementClass) {
        return fromJsonString(str, elementClass, null);
    }

    public static <T> T fromJsonString(String str, Class<T> elementClass, T defaultValue) {
        try {
            return objectMapper.readValue(str, elementClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    @SuppressWarnings("rawtypes")
    public static <E> List<E> fromJsonString(String str, Class<? extends List> listClass, Class<E> elementClass) {
        try {
            List<E> result = objectMapper.readValue(str, typeFactory.constructCollectionType(listClass, elementClass));
            return result.stream().filter(Objects::nonNull).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @SuppressWarnings("rawtypes")
    public static <K, V> Map<K, V> fromJsonString(JsonParser parser, Class<? extends Map> mapClass, Class<K> keyClass, Class<V> valueClass) {
        try {
            return objectMapper.readValue(parser, typeFactory.constructMapType(mapClass, keyClass, valueClass));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    @SuppressWarnings("rawtypes")
    public static <K, V> Map<K, V> fromJsonString(String str, Class<? extends Map> mapClass, Class<K> keyClass, Class<V> valueClass) {
        try {
            return objectMapper.readValue(str, typeFactory.constructMapType(mapClass, keyClass, valueClass));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    public static ArrayNode toJsonArray(Object obj) {
        return Electron.objectMapper.convertValue(obj, ArrayNode.class);
    }

    public static ObjectNode toJsonObject(Object obj) {
        return Electron.objectMapper.convertValue(obj, ObjectNode.class);
    }

    public static String toJsonString(Object obj) {
        try {
            return Electron.objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "{}";
    }

    public static void UseElectron(String[] args) {
        UseElectron(args, LoggerFactory.getLogger(Electron.class));
    }

    public static void UseElectron(String[] args, Logger log) {
        Map<String, String> parameters = new HashMap<>();
        Arrays.stream(args).map(it -> it.replace("--", "").split("="))
                .filter(it -> it.length > 1)
                .forEach(it -> parameters.put(it[0], it[1]));
        String socketPort = parameters.getOrDefault("electron-port", "8000");
        String webPort = parameters.getOrDefault("server.port", "8080");
        log.info("Java App stated on " + webPort + " and socket on " + socketPort);
        BridgeSettings.setSocketPort(socketPort);
        BridgeSettings.setWebPort(webPort);

        if (HybridSupport.IsElectronActive()) {
            getApp().setIsReady(true);
        }
    }

    public static IpcMain getIpcMain() {
        return IpcMain.getInstance();
    }

    public static App getApp() {
        return App.getInstance();
    }

    public static AutoUpdater getAutoUpdater() {
        return AutoUpdater.getInstance();
    }

    public static WindowManager getWindowManager() {
        return WindowManager.getInstance();
    }

    public static Menu getMenu() {
        return Menu.getInstance();
    }

    public static Dialog getDialog() {
        return Dialog.getInstance();
    }

    public static Notification getNotification() {
        return Notification.getInstance();
    }

    public static Tray getTray() {
        return Tray.getInstance();
    }

    public static GlobalShortcut getGlobalShortcut() {
        return GlobalShortcut.getInstance();
    }

    public static Shell getShell() {
        return Shell.getInstance();
    }

    public static Screen getScreen() {
        return Screen.getInstance();
    }

    public static Clipboard getClipboard() {
        return Clipboard.getInstance();
    }

//    public static HostHook getHostHook() {
//        return HostHook.getInstance();
//    }

    public static PowerMonitor getPowerMonitor() {
        return PowerMonitor.getInstance();
    }

    public static NativeTheme getNativeTheme() {
        return NativeTheme.getInstance();
    }

    public static Dock getDock() {
        return Dock.getInstance();
    }
}
