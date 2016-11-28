package com.example.lambo.activity;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lambo.R;
import com.example.lambo.ui.MyListView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by sEEyOU on 2016/11/24.
 */
public class VoiceChatActivity extends Activity {
    final static String TAG = "lambo";
    MyThread thread1,thread2;
    EditText et1,et2;
    Button btn1,btn2;
    MyListView lv1,lv2;
    Socket socket;
    MediaRecorder recorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socket);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn1.setText("lambo");
        btn2.setText("rose");
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        lv1 = (MyListView) findViewById(R.id.lv1);
        lv2 = (MyListView) findViewById(R.id.lv2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thread1 != null){
                    try {
                        thread1.ou.write(et1.getText().toString().getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    thread1 = new MyThread("lambo");
                    thread1.start();
//                    short bs = (short) AudioRecord.getMinBufferSize(22050,
//                            AudioFormat.CHANNEL_CONFIGURATION_MONO,
//                            AudioFormat.ENCODING_PCM_16BIT);
//                    AudioRecord ar = new AudioRecord(MediaRecorder.AudioSource.MIC, 3000,
//                            AudioFormat.CHANNEL_CONFIGURATION_MONO,
//                            AudioFormat.ENCODING_PCM_16BIT, bs);
//                    recorder=new MediaRecorder();
//                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);//声音来源是话筒
//                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);//设置格式
//                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//设置解码方式
//                    try {
//                        recorder.prepare();
//                        recorder.start();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thread2 != null){
                    try {
                        thread2.ou.write(et2.getText().toString().getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    thread2 = new MyThread("rose");
                    thread2.start();
                }
            }
        });
    }

    public class MyThread extends Thread {
        String name;
        public MyThread(String name){
            this.name = name;
        }
        public OutputStream ou;
        @Override
        public void run() {
            try {
                //获取输入输出流
                socket = new Socket();
                socket.connect(new InetSocketAddress("192.168.1.112", 9000), 3000);
                ou = socket.getOutputStream();
                //向服务器发送信息
                ou.write(("用户" + name + "连接上来了").getBytes());
                //读取发来服务器信息
                BufferedReader  bff = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                String line = null;
                String buffer = "";
                while ((line = bff.readLine()) != null) {
                    buffer = line + buffer;
                    Log.d(TAG, name+":" + line.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
