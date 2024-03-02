import androidx.compose.ui.res.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MyWindow extends Canvas implements ActionListener {
    static {
        // Load the library that contains the paint code.
//        System.loadLibrary("MyWindow");
        String userHomePath = System.getProperty("user.home");
        File appDir = new File(userHomePath, ".compose-test");
        if (!appDir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            appDir.mkdirs();
        }

        File logDir = new File(appDir, "log");
        if (!logDir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            logDir.mkdirs();
        }

        File logFile = new File(logDir, "log.txt");

        try {
            FileWriter writer = new FileWriter(logFile);
            writer.write("userHomePath:" + userHomePath);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File resourcesDir = new File(appDir, "resources");
        if (!resourcesDir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            resourcesDir.mkdirs();
        }
        File libDir = new File(resourcesDir, "lib");
        if (!libDir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            libDir.mkdirs();
        }

        File nativeLibFile = new File(libDir, "MyWindow.dll");
        if (!nativeLibFile.exists()) {
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                inputStream = ResourceLoader.Companion.getDefault().load("lib/MyWindow.dll");
                outputStream = new FileOutputStream(nativeLibFile);
                byte[] bytes = inputStream.readAllBytes();
                outputStream.write(bytes);
            } catch (Exception e) {
                //noinspection CallToPrintStackTrace
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        //noinspection CallToPrintStackTrace
                        e.printStackTrace();
                    }
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        //noinspection CallToPrintStackTrace
                        e.printStackTrace();
                    }
                }
            }
        }

        System.out.println("nativeLibFile:" + nativeLibFile.getAbsolutePath());
        System.load(nativeLibFile.getAbsolutePath());
    }

    public final static int TIMER_MILL_SECONDS = 10;


    private boolean bInitOpenGL = false;

    public void actionPerformed(ActionEvent evt) {
        if (!bInitOpenGL) {
            bInitOpenGL = true;
            initializeOpenGL();
//            System.out.println("initializeOpenGL();");
        }
        paintOpenGL();
//        System.out.println("paintOpenGL();");
    }

    public void addNotify() {
        super.addNotify();
        javax.swing.Timer timer = new Timer(TIMER_MILL_SECONDS, this);
        timer.start();
    }

    public void removeNotify() {
        super.removeNotify();
        cleanupOpenGL();
//        System.out.println("cleanupOpenGL();");
    }

    // native entry point for Painting
    public native void paintOpenGL();

    // native entry point for enabling OpenGL calls.
    public native void initializeOpenGL();

    // native entry point for disabling OpenGL calls.
    public native void cleanupOpenGL();
}
