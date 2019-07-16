package com.wiseasy.communication.listener;

/**
 * usb接受数据成功或失败的回调
 */
public interface ReciverMessageListener {
//    void onSuccess(byte[] bytes);
    void onSuccess(String  bytes);

    void onFaild(String msg);
}
