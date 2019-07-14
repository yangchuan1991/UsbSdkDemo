package com.wiseasy.communication.listener;

/**
 * usb发送数据成功或失败的回调
 */
public interface SendMessageListener {
    void onSuccess();
    void onFaild(String msg);
}
