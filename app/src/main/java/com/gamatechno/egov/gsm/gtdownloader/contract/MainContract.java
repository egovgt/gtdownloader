package com.gamatechno.egov.gsm.gtdownloader.contract;

import android.net.Uri;

import java.util.ArrayList;

import com.gamatechno.egov.gsm.downloader.GTDownloadRequest;
import com.gamatechno.egov.gsm.gtdownloader.contract.base.BasePresenter;
import com.gamatechno.egov.gsm.gtdownloader.contract.base.BaseView;
import com.gamatechno.egov.gsm.gtdownloader.model.DownloadItem;

public class MainContract {
    public interface View extends BaseView{
        void onGetData(ArrayList<DownloadItem> data);
        void onDataEmpty();
        void onGetDataFailed(String message);
        void onProcessDownload(GTDownloadRequest request);
        void onCancelDownload(GTDownloadRequest request);
        void onSuccessDownload(GTDownloadRequest request);
    }

    public interface Presenter extends BasePresenter<View> {
        void getData();
        void setDownload(Uri uri, String fileName);
        void cancelDownload(Uri uri);
    }
}
