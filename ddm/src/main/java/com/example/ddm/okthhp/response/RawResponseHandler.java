package com.example.ddm.okthhp.response;
/**
 * Created by MiyuShiroki
 * on 2017/2/24.
 *  raw 字符串结果回调
 */
public abstract class RawResponseHandler implements IResponseHandler {

    public abstract void onSuccess(int statusCode, String response);

    @Override
    public void onProgress(long currentBytes, long totalBytes) {

    }
}
