package com.glueball.snoop.bean;

import java.util.List;

import com.glueball.snoop.entity.DocumentPath;

public interface DocumentPathBean {

	void updateDocuments(final String shareName, final List<DocumentPath> docs);
}
