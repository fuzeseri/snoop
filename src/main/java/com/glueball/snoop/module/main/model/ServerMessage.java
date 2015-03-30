package com.glueball.snoop.module.main.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "server_message")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServerMessage implements Serializable {

    public static final int MESSAGE_TYPE_OK = 0;
    public static final int MESSAGE_TYPE_ERROR = 1;

    public static final ServerMessage MESSAGE_NO_HITS = new ServerMessage(MESSAGE_TYPE_ERROR, "No documents found int the search term.");

    public static final ServerMessage MESSAGE_INDEX_NOT_READY = new ServerMessage(MESSAGE_TYPE_ERROR, "The index is not ready yet. Please come back later.");

    public static final ServerMessage MESSAGE_CANT_OPEN_INDEX = new ServerMessage(MESSAGE_TYPE_ERROR, "Error opening index.");

    private static final long serialVersionUID = 1L;

    private int messageType;
    private String message = "";

    public ServerMessage() {

    }

    public ServerMessage(final int _messageType, final String _message) {

        this.messageType = _messageType;
        this.message = _message;
    }

    public int getMessageType() {

        return messageType;
    }

    public void setMessageType(int messageType) {

        this.messageType = messageType;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

    public static String getTypeText(int messageType) {

        if (messageType == MESSAGE_TYPE_OK) {

            return "OK";
        }
        if (messageType == MESSAGE_TYPE_ERROR) {

            return "ERROR";
        }

        return "";
    }

    public static String getIcon(int messageType) {

        if (messageType == MESSAGE_TYPE_OK) {

            return "ok.png";
        }

        if (messageType == MESSAGE_TYPE_ERROR) {

            return "error.png";
        }

        return "";
    }

    public boolean isServerMessage() {

        return true;
    }
}
