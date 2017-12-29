package com.yushilei.commonapp.ui.mqtt;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.widget.RemoteViews;

import com.ibm.mqtt.MqttClient;
import com.ibm.mqtt.MqttException;
import com.ibm.mqtt.MqttSimpleCallback;
import com.yushilei.commonapp.common.async.ThreadPools;

/**
 * @author shilei.yu
 */
public class MqttService extends Service {

    private MqttClient mClient;

    public MqttService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new Sender();
    }

    private String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

    private boolean connect() {
        try {
            mClient = (MqttClient) MqttClient.createMqttClient("tcp://tiefighter.loc:1883", null);
            mClient.registerSimpleHandler(new MessageHandler());
            mClient.connect("droid-" + android_id, true, (short) 240);
            String topics[] = {"TV/Status"};
            int qos[] = {1};
            mClient.subscribe(topics, qos);
            return true;
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
    }

    private final class MessageHandler implements MqttSimpleCallback {

        @Override
        public void connectionLost() throws Exception {
            mClient = null;
            ThreadPools.newThread(new Runnable() {
                @Override
                public void run() {
                    do {
                        SystemClock.sleep(5000);
                    } while (!connect());
                }
            });
        }

        @Override
        public void publishArrived(String topic, byte[] payload, int qos, boolean retained) throws Exception {
            String message = new String(payload);
        }
    }

    private final class Sender extends Binder {
        public void send(String msg) {
            if (mClient != null) {
                try {
                    mClient.publish("text", msg.getBytes(), 1, true);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
