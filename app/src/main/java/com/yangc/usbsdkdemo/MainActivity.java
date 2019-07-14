package com.yangc.usbsdkdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.wiseasy.communication.listener.CommunicationListener;
import com.wiseasy.communication.listener.ReciverMessageListener;
import com.wiseasy.communication.listener.SendMessageListener;
import com.wiseasy.communication.usb.UsbCommunication;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView error, log;
    private EditText message;
    private Button sendMessage;
    private RadioButton host, accessory;
    private boolean isReceiverMessage = true;
    private UsbCommunication usbCommunication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initUsb();
    }

    private void getUsbMessage() {
        //接收数据
        usbCommunication.receiveMessage(new ReciverMessageListener() {
            @Override
            public void onSuccess(byte[] bytes) {
                usbMessage = new String(bytes);
                log.setText(usbMessage);
            }

            @Override
            public void onFaild(String msg) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        error = findViewById(R.id.error);
        log = findViewById(R.id.log);
        message = findViewById(R.id.message);
        sendMessage = findViewById(R.id.sendmessage);
        sendMessage.setEnabled(false);
        sendMessage.setOnClickListener(this);
    }

    private String usbMessage;

    private void initUsb() {
        //初始化usbCommunication对象
        usbCommunication =  UsbCommunication.getInstance(this);
        //开启usb连接
       usbCommunication.openCommunication(new CommunicationListener() {
            @Override
            public void onSuccess(int code, String msg) {
                sendMessage.setEnabled(true);
                getUsbMessage();
            }

            @Override
            public void onFaild(String msg) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * 发送消息
             */
            case R.id.sendmessage:
                String msg = message.getText().toString().trim();
                if (TextUtils.isEmpty(msg)) {
                    Toast.makeText(MainActivity.this, "请输入要发送的信息", Toast.LENGTH_LONG).show();
                    return;
                }
                usbCommunication.sendMessage(msg.getBytes(), new SendMessageListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(MainActivity.this, "信息发送成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFaild(String msg) {
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                    }
                });
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UsbCommunication.getInstance(this).closeCommunication();
    }
}