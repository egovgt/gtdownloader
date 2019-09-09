package com.gamatechno.egov.gsm.gtdownloader;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.gamatechno.egov.gsm.gtdownloader.model.DownloadItem;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ItemHolder> {

    private Activity mActivity;
    private ArrayList<DownloadItem> mList;
    private MainActivity.DownloadAction mCallback;

    public ListAdapter(Activity mActivity, ArrayList<DownloadItem> mList) {
        this.mActivity = mActivity;
        this.mList = mList;
    }

    public void setAllData(ArrayList<DownloadItem> list){
        this.mList = list;
        notifyDataSetChanged();
    }

    public void setCallback(MainActivity.DownloadAction callback){
        this.mCallback = callback;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mActivity.getLayoutInflater().inflate(R.layout.item_download,null);
        return new ItemHolder(view);
    }

    public void changeProcessByUrl(Uri path, boolean inProcess){
        for(int i=0; i<mList.size(); i++){
            if(mList.get(i).getUrl().equals(path.toString())){
                mList.get(i).setProcess(inProcess);
                break;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder itemHolder, int i) {
        final DownloadItem item = mList.get(i);
        final int index = i;
        itemHolder.tvName.setText(item.getName());
        itemHolder.tvNote.setText(item.getUrl());
        itemHolder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onClickDownload(item.getUrl(),item.getFileName());
            }
        });
        itemHolder.btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemHolder.tvNote.setVisibility(View.VISIBLE);
                itemHolder.btnStop.setVisibility(View.GONE);
                itemHolder.btnDownload.setVisibility(View.VISIBLE);
                itemHolder.pbProgress.setVisibility(View.GONE);
                mCallback.onClickStop(item.getUrl());
            }
        });
        if(item.isProcess()){
            itemHolder.tvNote.setText("Downloading");
            itemHolder.btnStop.setVisibility(View.VISIBLE);
            itemHolder.btnDownload.setVisibility(View.GONE);
        } else {
            itemHolder.tvNote.setText(item.getUrl());
            itemHolder.btnStop.setVisibility(View.GONE);
            itemHolder.btnDownload.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.pb_progress)
        ProgressBar pbProgress;
        @BindView(R.id.tv_note)
        TextView tvNote;
        @BindView(R.id.btn_download)
        RelativeLayout btnDownload;
        @BindView(R.id.btn_stop)
        RelativeLayout btnStop;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
