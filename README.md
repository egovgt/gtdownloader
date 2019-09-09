# gtdownloader

How to start download from url :

~~~
mDownloadManager = new GTDownloadManager(mContext, new GTDownloadCallback() {
            @Override
            public void onProcess(GTDownloadRequest request) {
                // mView.onProcessDownload(request);
            }

            @Override
            public void onCancel(GTDownloadRequest request) {
                // mView.onCancelDownload(request);
            }

            @Override
            public void onSuccess(GTDownloadRequest request) {
                // mView.onSuccessDownload(request);
            }
        });
        
        mDownloadManager.startRequest(new GTDownloadRequest(uri,fileName).setDestinationUri(destination));
~~~

How to cancel download :

~~~
mDownloadManager.cancelDownload(new GTDownloadRequest(uri));
~~~
