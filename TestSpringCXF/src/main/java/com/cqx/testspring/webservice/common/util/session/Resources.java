package com.cqx.testspring.webservice.common.util.session;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 * Resources
 *
 * @author chenqixu
 */
public class Resources {
    private static String rootPath = null;
    private static String CLASSNAME = "com.cqx.testspring.util.Resources";
    private static String CLASS = "/com/cqx/testspring/util/Resources.class";
    private static final String WINDOWS = "WINDOWS";
    private static ClassLoader defaultClassLoader;

    private Resources() {
    }

    public static ClassLoader getDefaultClassLoader() {
        return defaultClassLoader;
    }

    public static void setDefaultClassLoader(ClassLoader defaultClassLoader) {
        defaultClassLoader = defaultClassLoader;
    }

    public static URL getResourceURL(String resource) throws IOException {
        return getResourceURL(getClassLoader(), resource);
    }

    public static URL getResourceURL(ClassLoader loader, String resource) throws IOException {
        URL url = null;
        if (loader != null) {
            url = loader.getResource(resource);
        }

        if (url == null) {
            url = ClassLoader.getSystemResource(resource);
        }

        if (url == null) {
            throw new IOException("Could not find resource " + resource);
        } else {
            return url;
        }
    }

    public static InputStream getResourceAsStream(String resource) throws IOException {
        return getResourceAsStream(getClassLoader(), resource);
    }

    public static InputStream getResourceAsStream(ClassLoader loader, String resource) throws IOException {
        InputStream in = null;
        if (loader != null) {
            in = loader.getResourceAsStream(resource);
        }

        if (in == null) {
            in = ClassLoader.getSystemResourceAsStream(resource);
        }

        if (in == null) {
            throw new IOException("Could not find resource " + resource);
        } else {
            return in;
        }
    }

    public static Properties getResourceAsProperties(String resource) throws IOException {
        Properties props = new Properties();
        InputStream in = null;
        in = getResourceAsStream(resource);
        props.load(in);
        in.close();
        return props;
    }

    public static Properties getResourceAsProperties(ClassLoader loader, String resource) throws IOException {
        Properties props = new Properties();
        InputStream in = null;
        in = getResourceAsStream(loader, resource);
        props.load(in);
        in.close();
        return props;
    }

    public static Reader getResourceAsReader(String resource) throws IOException {
        return new InputStreamReader(getResourceAsStream(resource));
    }

    public static Reader getResourceAsReader(ClassLoader loader, String resource) throws IOException {
        return new InputStreamReader(getResourceAsStream(loader, resource));
    }

    public static File getResourceAsFile(String resource) throws IOException {
        return new File(getResourceURL(resource).getFile());
    }

    public static File getResourceAsFile(ClassLoader loader, String resource) throws IOException {
        return new File(getResourceURL(loader, resource).getFile());
    }

    public static InputStream getUrlAsStream(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        return conn.getInputStream();
    }

    public static Reader getUrlAsReader(String urlString) throws IOException {
        return new InputStreamReader(getUrlAsStream(urlString));
    }

    public static Properties getUrlAsProperties(String urlString) throws IOException {
        Properties props = new Properties();
        InputStream in = null;
        in = getUrlAsStream(urlString);
        props.load(in);
        in.close();
        return props;
    }

    public static Class classForName(String className) throws ClassNotFoundException {
        Class clazz = null;

        try {
            clazz = getClassLoader().loadClass(className);
        } catch (Exception var3) {
        }

        if (clazz == null) {
            clazz = Class.forName(className);
        }

        return clazz;
    }

    public static Object instantiate(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        return instantiate(classForName(className));
    }

    public static Object instantiate(Class clazz) throws InstantiationException, IllegalAccessException {
        return clazz.newInstance();
    }

    private static ClassLoader getClassLoader() {
        return defaultClassLoader != null ? defaultClassLoader : Thread.currentThread().getContextClassLoader();
    }

    public static String getRootPath() {
        if (null == rootPath) {
            try {
                Class.forName(CLASSNAME);
                URL url = Resources.class.getResource(CLASS);
                if (null != url && null != url.getPath()) {
                    rootPath = url.getPath();
                    String os = System.getProperty("os.name").toUpperCase();
                    if (os.indexOf("WINDOWS") > -1) {
                        rootPath = rootPath.replaceFirst("file:/", "");
                    } else {
                        rootPath = rootPath.replaceFirst("file:", "");
                    }

                    rootPath = rootPath.replaceAll("\\\\", "/");
                    int index = rootPath.indexOf(".jar!");
                    if (index > -1) {
                        rootPath = rootPath.substring(0, index);
                        index = rootPath.lastIndexOf("/");
                        if (index > -1) {
                            rootPath = rootPath.substring(0, index) + "/";
                        }
                    }
                }
            } catch (ClassNotFoundException var3) {
                var3.printStackTrace();
                return "";
            }
        }

        return rootPath;
    }
}
