# gtdownloader
## Module Setup :
### Add to gradle dependency
~~~
implementation 'com.github.egovgt:gtdownloader:1.0'
~~~

### Add it in your root build.gradle at the end of repositories:
~~~
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
~~~

## How to? 

### Start download from url :

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

### Cancel download :

~~~
mDownloadManager.cancelDownload(new GTDownloadRequest(uri));
~~~
