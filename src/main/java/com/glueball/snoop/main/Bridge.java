package com.glueball.snoop.main;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javafx.application.Application;

public class Bridge {

    final Application app;

    public Bridge(final Application pApp) {

        this.app = pApp;
    }

    public void openFile(final String fileUri) {

        new Thread(new Runnable() {

            @Override
            public void run() {

                final File file = new File(fileUri);

                if (!file.exists()) {

                    System.out.println("File is not exists: "
                            + file.getAbsolutePath());
                }

                if (!Desktop.isDesktopSupported()) {

                    System.out.println("Desktop is not supported");
                } else {

                    try {

                        Desktop.getDesktop().open(file);
                    } catch (IOException e) {

                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }

        }).start();
    }
}
