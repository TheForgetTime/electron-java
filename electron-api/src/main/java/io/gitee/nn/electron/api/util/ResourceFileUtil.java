package io.gitee.nn.electron.api.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class ResourceFileUtil {
    private static String byte2Hex(byte[] bytes) {
        StringBuilder stringBuffer = new StringBuilder();
        String temp;
        for (byte aByte : bytes) {
            temp = Integer.toHexString(aByte & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    private static String sha256(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    public static String UnzipFromJar(String path) {
        return UnzipFromJar(ResourceFileUtil.class, path);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static String UnzipFromJar(Class<?> clazz, String path) {
        if (path == null) {
            return null;
        }
        var fis = clazz.getResourceAsStream(path);
        if (fis == null) {
            return path;
        }
        String fileName = sha256(path);
        File iconFile = new File(System.getProperty("java.io.tmpdir") + File.separator + fileName + ".tmp");
        if (!iconFile.exists()) {
            try {
                iconFile.createNewFile();
                iconFile.deleteOnExit();
                FileOutputStream fos = new FileOutputStream(iconFile);
                fis.transferTo(fos);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return iconFile.getAbsolutePath();
    }
}
