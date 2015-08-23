/**
 * 
 */
package com.glueball.snoop.main;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

class Browser extends Region {

    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();

    final Application application;

    final String httpHost = "localhost";
    final int httpPort = 8080;

    public Browser(final Application pApplication) {

        this.application = pApplication;

        getStyleClass().add("browser");

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

        win.setMember("snoopObj", new Bridge(this.application));

        webEngine.load("http://" + httpHost + ":" + httpPort + "/index.html");
        getChildren().add(browser);
    }

    private Node createSpacer() {

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    @Override
    protected void layoutChildren() {

        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser, 0, 0, w, h, 0, HPos.CENTER, VPos.CENTER);
    }

    @Override
    protected double computePrefWidth(double height) {

        return 1024;
    }

    @Override
    protected double computePrefHeight(double width) {

        return 768;
    }
}
