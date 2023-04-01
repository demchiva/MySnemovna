package cz.my.snemovna;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TestUtils {

    public static String getResourceContent(String resourceName) {
        byte[] res = getResourceBytes(resourceName);
        return res == null ? null : new String(res, StandardCharsets.UTF_8);
    }

    public static byte[] getResourceBytes(String resourceName) {
        ClassLoader contextCL = Thread.currentThread().getContextClassLoader();

        InputStream is = contextCL.getResourceAsStream("/" + resourceName);
        if (is == null) {
            is = contextCL.getResourceAsStream(resourceName);
        }

        byte[] content;
        try {
            try {
                content = readFile(resourceName, is);
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading resource: " + resourceName, e);
        }

        return content;
    }

    public static byte[] readFile(String name, InputStream is) {
        ByteArrayOutputStream bout = null;
        try {
            if (is == null) {
                try {
                    is = new URL(name).openStream();
                } catch (MalformedURLException e) {
                    is = new FileInputStream(name);
                }
            }

            bout = new ByteArrayOutputStream();

            int len = -1;
            byte[] buff = new byte[2048];  // temp buffer

            while ((len = is.read(buff)) > 0) {
                bout.write(buff, 0, len);
            }

            bout.close();
            is.close();

            return bout.toByteArray();
        } catch (IOException ignored) {
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {
                }
            }
            if (bout != null) {
                try {
                    bout.close();
                } catch (IOException ignored) {
                }
            }
        }

        return null;
    }
}
