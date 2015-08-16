package com.glueball.snoop.main;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

public class ClientMain extends Application {

    public static void main(String[] args) {

        Application.launch(args);
    }

    final Scene createScene() {

        final WebView webView = new WebView();
        final WebEngine webEngine = webView.getEngine();

        webEngine.getLoadWorker().stateProperty()
                .addListener(new ChangeListener<Worker.State>() {

                    @Override
                    public void changed(ObservableValue<? extends State> p,
                            State oldState, State newState) {

                        if (newState == Worker.State.SUCCEEDED) {

                        }
                    }
                });

        final JSObject win = (JSObject) webEngine
                .executeScript("window");

        win.setMember("snoopObj", new Bridge(this));

        webEngine.load("http://localhost:8082");

        final Scene scene = new Scene(new Group(webView));
        return scene;
    }

    @Override
    public void start(Stage stage) {

        stage.setScene(createScene());
        stage.sizeToScene();
        stage.show();
    }
}
