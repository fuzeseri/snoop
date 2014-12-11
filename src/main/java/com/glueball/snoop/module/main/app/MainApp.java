package com.glueball.snoop.module.main.app;

import com.glueball.snoop.module.main.ui.CrmHead;
import com.glueball.snoop.module.main.ui.MainWindow;
import com.glueball.snoop.module.util.URLHelper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class MainApp implements EntryPoint {

	MainWindow window = new MainWindow();
	CrmHead	   head	  = new CrmHead();
	
	public void onModuleLoad() {

		window.getHeadPanel().add(head.asWidget());
		window.getBody().setUrl(URLHelper.getURL("/page/Client/?id=535c373a87f235b684d5ef8a"));
		RootPanel.get().add(window);

	}

}
