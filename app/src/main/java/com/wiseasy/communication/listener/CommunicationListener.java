package com.wiseasy.communication.listener;

/**
 * usb连接开启成功或失败的回调
 */
public interface CommunicationListener {
//    void onSuccess(int code, String msg);
    void onSuccess();

    void onFaild(String msg);
}
