package com.glueball.snoop.module.main.app;

/*
 * Licensed to Glueball Ltd. under one or more contributor license agreements.
 * See the README file distributed with this work for additional information
 * regarding copyright ownership. You may obtain a copy of the License at
 * 
 * http://www.glueball.hu/licenses/snoop/sourcecode
 */
import com.glueball.snoop.module.main.ui.MainWindow;
import com.glueball.snoop.module.main.ui.SnoopHead;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.RootPanel;

public class MainApp implements EntryPoint {

    final MainWindow window = new MainWindow();
    final SnoopHead head = new SnoopHead(window.getHits());

    @Override
    public void onModuleLoad() {

        window.getHeadPanel().add(head.asWidget());
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {

            @Override
            public void execute() {

                head.searchBox.setFocus(true);
            }
        });
        RootPanel.get().add(window.asWidget());
    }

}
