package com.glueball.snoop.module.main.app;

import com.glueball.snoop.module.main.ui.MainWindow;
import com.glueball.snoop.module.main.ui.SnoopHead;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.RootPanel;

public class MainApp implements EntryPoint {

    final MainWindow window = new MainWindow();
    final SnoopHead head = new SnoopHead(window.getHits());

    public void onModuleLoad() {

        window.getHeadPanel().add(head.asWidget());
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {

            public void execute() {

                head.searchBox.setFocus(true);
            }
        });
        RootPanel.get().add(window.asWidget());
    }

}
