package com.glueball.snoop.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author karesz
 */
public class ClientMain extends Application {

    /**
     * javaFX Scene object.
     */
    private Scene scene;

    /**
     * @param args
     */
    public static void main(String[] args) {

        Application.launch(args);
    }

    /*
     * (non-Javadoc)
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(final Stage stage) {

        stage.setTitle("Snoop");
        scene = new Scene(new Browser(this), 1024, 768, Color.web("#666970"));
        stage.setScene(scene);
        scene.getStylesheets().add("css/bootstrap.min.css");
        stage.show();
    }
}
