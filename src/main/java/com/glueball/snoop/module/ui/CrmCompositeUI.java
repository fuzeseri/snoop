package com.glueball.snoop.module.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public abstract class CrmCompositeUI extends Composite {

    protected final Composite crmApp;
    protected Widget widget;

    protected CrmCompositeUI(final Composite app) {
	this.crmApp = app;
    }

    protected Composite getCrmApp() {
	return crmApp;
    }

}
