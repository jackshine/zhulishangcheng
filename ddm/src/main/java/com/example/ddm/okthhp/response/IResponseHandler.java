package com.example.ddm.okthhp.response;

/**
 * Created by MiyuShiroki
 * on 2017/2/24.
 * 回调
 */
public interface IResponseHandler {

    void onFailure(int statusCode, String error_msg);

    void onProgress(long currentBytes, long totalBytes);
}
