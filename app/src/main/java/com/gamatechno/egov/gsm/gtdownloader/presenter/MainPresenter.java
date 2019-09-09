package com.gamatechno.egov.gsm.gtdownloader.presenter;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.gamatechno.egov.gsm.downloader.Exception.GTDownloadException;
import com.gamatechno.egov.gsm.downloader.GTDownloadCallback;
import com.gamatechno.egov.gsm.downloader.GTDownloadManager;
import com.gamatechno.egov.gsm.downloader.GTDownloadRequest;
import com.gamatechno.egov.gsm.gtdownloader.contract.MainContract;
import com.gamatechno.egov.gsm.gtdownloader.model.DownloadItem;
import lib.almuwahhid.utils.UiLibRequest;

public class MainPresenter implements MainContract.Presenter {
    private static final String TAG = MainPresenter.class.getSimpleName();

    private MainContract.View mView;
    private GTDownloadManager mDownloadManager;
    private Context mContext;

    public MainPresenter(Context context) {
        this.mContext = context;
        mDownloadManager = new GTDownloadManager(mContext, new GTDownloadCallback() {
            @Override
            public void onProcess(GTDownloadRequest request) {
                mView.onProcessDownload(request);
            }

            @Override
            public void onCancel(GTDownloadRequest request) {
                mView.onCancelDownload(request);
            }

            @Override
            public void onSuccess(GTDownloadRequest request) {
                mView.onSuccessDownload(request);
            }
        });
    }

    @Override
    public void attachView(MainContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        if(mDownloadManager!=null)
            this.mDownloadManager.onDestroy();
    }

    @Override
    public void getData() {
        UiLibRequest.GET("http://tutorial-sourcecode.com/gtdownloader", mContext, new UiLibRequest.OnGetRequest() {
            @Override
            public void onPreExecuted() {

            }

            @Override
            public void onSuccess(JSONObject response) {
                ArrayList<DownloadItem> data = new ArrayList<>();
                try {
                    JSONArray array = response.getJSONArray("data");
                    for(int i=0;i<array.length();i++){
                        data.add(new DownloadItem(array.getJSONObject(i)));
                    }
                    if(data.size()>0){
                        mView.onGetData(data);
                    } else {
                        mView.onDataEmpty();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    mView.onGetDataFailed(e.getMessage());
                }
            }

            @Override
            public void onFailure(String error) {
                mView.onGetDataFailed(error);
            }

            @Override
            public Map<String, String> requestParam() {
                return new HashMap<>();
            }

            @Override
            public Map<String, String> requestHeaders() {
                return new HashMap<>();
            }
        });
    }

    @Override
    public void setDownload(Uri uri, String fileName) {
        try {
            Uri destination = Uri.withAppendedPath(Uri.fromFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)),"GTDownloader");
            Log.d(TAG,destination.toString());
            mDownloadManager.startRequest(new GTDownloadRequest(uri,fileName).setDestinationUri(destination));
        } catch (GTDownloadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancelDownload(Uri uri) {
        mDownloadManager.cancelDownload(new GTDownloadRequest(uri));
    }
}
