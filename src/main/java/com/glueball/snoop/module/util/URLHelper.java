package com.glueball.snoop.module.util;

import com.google.gwt.user.client.Window;

public final class URLHelper {

	public static final String GET_CLIENT    = getURL("client/getById/");
	public static final String SAVE_CLIENT   = getURL("client/save/");
	public static final String MODIFY_CLIENT = getURL("client/modify/");

	public static final String GET_ADDRESS    = getURL("client/address/getByClientId/");
	public static final String SAVE_ADDRESS   = getURL("client/address/save/");
	public static final String MODIFY_ADDRESS = getURL("client/address/modify/");

	public static final String GET_CLIENT_PERSON    = getURL("client/person/getByClientId/");
	public static final String SAVE_CLIENT_PERSON   = getURL("client/person/save/");
	public static final String MODIFY_CLIENT_PERSON = getURL("client/person/modify/");
	
	public static final String GET_CLIENT_PERSON_NAME  = getURL("client/person/name/getByClientId/");
	public static final String SAVE_CLIENT_PERSON_NAME = getURL("client/person/name/save/");

	public static final String GET_CLIENT_PERSON_MAIDENNAME  = getURL("client/person/maidenname/getByClientId/");
	public static final String SAVE_CLIENT_PERSON_MAIDENNAME = getURL("client/person/maidenname/save/");

	public static final String GET_CLIENT_PERSON_MOTHERSNAME  = getURL("client/person/mothersname/getByClientId/");
	public static final String SAVE_CLIENT_PERSON_MOTHERSNAME = getURL("client/person/mothersname/save/");

	public static final String GET_CLIENT_PERSON_DATEOFBITH  = getURL("client/person/dateofbirth/getByClientId/");
	public static final String SAVE_CLIENT_PERSON_DATEOFBITH = getURL("client/person/dateofbirth/save/");

	public static final String MODIFY_CLIENT_DATA = getURL("client/data/modify/");

	public static final String GET_CLIENT_COMPANY_PHONENUMBER  = getURL("client/company/phonenumber/getByClientId/");
	public static final String SAVE_CLIENT_COMPANY_PHONENUMBER = getURL("client/company/phonenumber/save/");

	public static final String GET_CLIENT_COMPANY_EMAILADDRESS  = getURL("client/company/emailaddress/getByClientId/");
	public static final String SAVE_CLIENT_COMPANY_EMAILADDRESS = getURL("client/company/emailaddress/save/");

	public static final String GET_BANKACCOUNT  = getURL("client/bankaccount/getById");
	public static final String GET_BANKACCOUNTS = getURL("client/bankaccount/getByClientId/");
	public static final String SAVE_BANKACCOUNT = getURL("client/bankaccount/save/");

	public static final String GET_CONTACT  = getURL("client/contact/getById/");
	public static final String GET_CONTACTS = getURL("client/contact/getByClientId/");
	public static final String SAVE_CONTACT = getURL("client/contact/save/");

	public static final String GET_MOBILE  = getURL("client/contact/mobile/getById/");
	public static final String GET_MOBILES = getURL("client/contact/mobile/getByContactId/");
	public static final String SAVE_MOBILE = getURL("client/contact/mobile/save/");

	public static final String GET_PHONE  = getURL("client/contact/phone/getById/");
	public static final String GET_PHONES = getURL("client/contact/phone/getByContactId/");
	public static final String SAVE_PHONE = getURL("client/contact/phone/save/");

	public static final String GET_EMAIL  = getURL("client/contact/email/getById/");
	public static final String GET_EMAILS = getURL("client/contact/email/getByContactId/");
	public static final String SAVE_EMAIL = getURL("client/contact/email/save/");

	public static final String GET_BANK_ACCOUNT  = getURL("client/bankAccount/getById/");
	public static final String GET_BANK_ACCOUNTS = getURL("client/bankAccount/getByContactId/");
	public static final String SAVE_BANK_ACCOUNT = getURL("client/bankAccount/save/");

	public static final String GET_BILLING_PERIOD  = getURL("client/billingPeriod/getById/");
	public static final String GET_BILLING_PERIODS = getURL("client/billingPeriod/getByClientId/");
	public static final String SAVE_BILLING_PERIOD = getURL("client/billingPeriod/save/");

	public static final String GET_PAYMENT_METHOD  = getURL("client/paymentMethod/getById/");
	public static final String GET_PAYMENT_METHODS = getURL("client/paymentMethod/getByClientId/");
	public static final String SAVE_PAYMENT_METHOD = getURL("client/paymentMethod/save/");

	public static String getURL(String path) {
		return Window.Location.getProtocol()+"//"+Window.Location.getHost()+"/"+path;
	}

	public static String getImageUrl(String image) {
		return Window.Location.getProtocol()+"//"+Window.Location.getHost()+"/page/image/"+image;
	}

}
