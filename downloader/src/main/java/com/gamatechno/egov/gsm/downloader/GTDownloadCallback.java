package com.gamatechno.egov.gsm.downloader;

public interface GTDownloadCallback {
    void onProcess(GTDownloadRequest request);
    void onCancel(GTDownloadRequest request);
    void onSuccess(GTDownloadRequest request);
}
