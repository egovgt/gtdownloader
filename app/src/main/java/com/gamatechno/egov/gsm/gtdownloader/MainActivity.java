package com.gamatechno.egov.gsm.gtdownloader;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.gamatechno.egov.gsm.downloader.GTDownloadRequest;
import com.gamatechno.egov.gsm.gtdownloader.contract.MainContract;
import com.gamatechno.egov.gsm.gtdownloader.model.DownloadItem;
import com.gamatechno.egov.gsm.gtdownloader.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private MainContract.Presenter mPresenter;
    private ArrayList<DownloadItem> mData;
    private ListAdapter mAdapter;

    @BindView(R.id.rv_list)
    RecyclerView mRVList;
    @BindView(R.id.rl_empty)
    RelativeLayout mRlEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initPresenter();
        initListAdapter();
    }

    private void initPresenter(){
        mPresenter = new MainPresenter(this);
        onAttach();
    }

    private void initListAdapter(){
        mData = new ArrayList<>();

        mRVList.setHasFixedSize(true);
        mRVList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ListAdapter(this, mData);
        mRVList.setAdapter(mAdapter);
        mPresenter.getData();
    }

    @Override
    public void onAttach() {
        mPresenter.attachView(this);
    }

    @Override
    public void onDetach() {
        mPresenter.detachView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDetach();
    }

    @Override
    public void onGetData(ArrayList<DownloadItem> data) {
        mData.clear();
        mData.addAll(data);
        mAdapter.setAllData(mData);
        mAdapter.setCallback(new DownloadAction() {
            @Override
            public void onClickDownload(String uri, String fileName) {
                mPresenter.setDownload(Uri.parse(uri),fileName);
            }

            @Override
            public void onClickStop(String uri) {
                Log.d("MainActivity", "onClickStop()");
                mPresenter.cancelDownload(Uri.parse(uri));
            }
        });
        mRlEmpty.setVisibility(View.GONE);
    }

    @Override
    public void onDataEmpty() {
        mRlEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void onGetDataFailed(String message) {
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProcessDownload(GTDownloadRequest request) {
        mAdapter.changeProcessByUrl(request.getUri(),true);
    }

    @Override
    public void onCancelDownload(GTDownloadRequest request) {
        mAdapter.changeProcessByUrl(request.getUri(),false);
    }

    @Override
    public void onSuccessDownload(GTDownloadRequest request) {
        mAdapter.changeProcessByUrl(request.getUri(),false);
    }

    public interface DownloadAction{
        void onClickDownload(String uri, String fileName);
        void onClickStop(String uri);
    }
}
