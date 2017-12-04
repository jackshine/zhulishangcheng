package com.example.ddm.okthhp.response;

import java.io.File;
/**
 * Created by MiyuShiroki
 * on 2017/2/24.
 * 下载回调
 */
public abstract class DownloadResponseHandler {

    public abstract void onFinish(File download_file);
    public abstract void onProgress(long currentBytes, long totalBytes);
    public abstract void onFailure(String error_msg);
}
