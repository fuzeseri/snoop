package com.glueball.snoop.module.main.app;

import com.glueball.snoop.module.main.ui.SnoopHead;
import com.glueball.snoop.module.main.ui.MainWindow;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

public class MainApp implements EntryPoint {

	final MainWindow window = new MainWindow();
	final SnoopHead	 head   = new SnoopHead(window.getHits());

	public void onModuleLoad() {
		Window.alert("Hello");
		window.getHeadPanel().add(head.asWidget());
		RootPanel.get().add(window.asWidget());
	}

}
