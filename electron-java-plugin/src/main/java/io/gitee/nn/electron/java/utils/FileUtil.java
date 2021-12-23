package io.gitee.nn.electron.java.utils;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileUtil {
    private static final Logger log = Logger.getLogger(FileUtil.class);

    public static void replaceTextContent(String path, String origStr, String targetStr) throws IOException {
        File file = new File(path);
        if (!file.exists()) return;
        FileReader fr = new FileReader(file);
        BufferedReader bfr = new BufferedReader(fr);
        CharArrayWriter tempStream = new CharArrayWriter();

        String line;
        while ((line = bfr.readLine()) != null) {
            line = line.replaceAll(origStr, targetStr);
            tempStream.write(line);
            tempStream.append(System.getProperty("line.separator"));
        }

        bfr.close();
        FileWriter fw = new FileWriter(file);
        tempStream.writeTo(fw);
        fw.close();
    }

    public static void loadResourceFromJarByFolder(String folderPath, String targetFolderPath, Class<?> clazz) throws IOException {
        URL url = clazz.getResource(folderPath);
        if (url == null) {
            throw new FileNotFoundException("文件 " + folderPath + "在JAR包中没有找到.");
        }
        URLConnection urlConnection = url.openConnection();
        if (urlConnection instanceof JarURLConnection) {
            copyJarResources((JarURLConnection) urlConnection, folderPath, targetFolderPath, clazz);
        }
    }

    private static void copyJarResources(JarURLConnection jarURLConnection, String folderPath, String targetFolderPath, Class<?> clazz) throws IOException {
        JarFile jarFile = jarURLConnection.getJarFile();
        Enumeration<JarEntry> entrys = jarFile.entries();
        while (entrys.hasMoreElements()) {
            JarEntry entry = entrys.nextElement();
            if (entry.getName().startsWith(jarURLConnection.getEntryName()) && !entry.getName().endsWith("/")) {
                loadResourceFromJar("/" + entry.getName(), targetFolderPath, clazz);
            }
        }
        jarFile.close();
    }

    public static void loadResourceFromJar(String path, String targetFolderPath, Class<?> clazz) throws IOException {
        if (!path.startsWith("/")) {
            throw new IllegalArgumentException("文件路径必须是绝对路径(开始于'/').");
        }

        if (path.endsWith("/")) {
            throw new IllegalArgumentException("文件路径必须是文件(不能结束于'/').");
        }

        int lastSeparatorIndex = path.lastIndexOf("/");
        var filename = path.substring(lastSeparatorIndex + 1);
        var folderPath = targetFolderPath + path.substring(0, lastSeparatorIndex + 1);

        var dir = new File(folderPath);
        if (!dir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            dir.mkdirs();
        }

        filename = folderPath + filename;
        var file = new File(filename);
        if (!file.exists() && !file.createNewFile()) {
            log.error("创建文件：{" + filename + "} 失败.");
            return;
        }

        var url = clazz.getResource(path);
        if (url == null) {
            throw new FileNotFoundException("文件 " + path + "在JAR包中没有找到.");
        }
        var urlConnection = url.openConnection();
        var fis = urlConnection.getInputStream();
        if (fis == null) {
            throw new FileNotFoundException("文件 " + path + "在JAR包中没有找到.");
        }
        var fos = new FileOutputStream(file);
        try {
            fis.transferTo(fos);
        } finally {
            fos.close();
            fis.close();
        }
    }
}
