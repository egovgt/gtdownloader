package com.gamatechno.egov.gsm.gtdownloader.model;

import org.json.JSONException;
import org.json.JSONObject;

public class DownloadItem {

    public static String NAME = "name";
    public static String FILE_NAME = "file";
    public static String FILE_TYPE = "type";
    public static String URL = "url";

    private String name;
    private String fileName;
    private String type;
    private String url;
    private boolean inProcess;

    public DownloadItem() {
    }

    public DownloadItem(JSONObject obj) {
        try {
            this.name = obj.has(NAME) ? !obj.isNull(NAME) ? obj.getString(NAME) : "" : "";
            this.fileName = obj.has(FILE_NAME) ? !obj.isNull(FILE_NAME) ? obj.getString(FILE_NAME) : "" : "";
            this.type = obj.has(FILE_TYPE) ? !obj.isNull(FILE_TYPE) ? obj.getString(FILE_TYPE) : "" : "";
            this.url = obj.has(URL) ? !obj.isNull(URL) ? obj.getString(URL) : "" : "";
            this.inProcess=false;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isProcess() {
        return inProcess;
    }

    public void setProcess(boolean inProcess) {
        this.inProcess = inProcess;
    }
}
