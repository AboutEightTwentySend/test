package com.example.lambo.activity;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
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
import java.io.InputStream;
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
    RecordThread rec;
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
//                    try {
//                        thread1.ou.write(et1.getText().toString().getBytes());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }else{
                    thread1 = new MyThread("lambo");
                    thread1.start();
                    rec = new RecordThread();
                    rec.start();
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thread2 != null){
//                    try {
//                        thread2.ou.write(et2.getText().toString().getBytes());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
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
                socket.connect(new InetSocketAddress("192.168.31.106", 9000), 3000);
                ou = socket.getOutputStream();
                //向服务器发送信息
//                ou.write(("用户" + name + "连接上来了").getBytes());
                //读取发来服务器信息
                BufferedReader  bff = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                String line = null;
                String buffer = "";
                while (true) {
                    if((line = bff.readLine()) == null) return;
                    buffer = line + buffer;
                    rec.audioTrack.write(rec.recBuf, 0, toInt(buffer.getBytes()));
                    Log.d(TAG, name+":" + line.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    class RecordThread extends Thread{
        static final int frequency = 44100;
        static final int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
        static final int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
        public AudioTrack audioTrack;
        public AudioRecord audioRecord;
        int recBufSize,plyBufSize;
        byte[] recBuf;
        @Override
        public void run() {
            // TODO Auto-generated method stub
            recBufSize = AudioRecord.getMinBufferSize(frequency,
                    channelConfiguration, audioEncoding)*2;
            plyBufSize = AudioTrack.getMinBufferSize(frequency,
                    channelConfiguration, audioEncoding)*2;
            recBuf = new byte[recBufSize];
            audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, frequency,
                    channelConfiguration, audioEncoding, recBufSize);

            audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, frequency,
                    channelConfiguration, audioEncoding, plyBufSize, AudioTrack.MODE_STREAM);

            audioRecord.startRecording();
            audioTrack.play();
            while(true){
                try {
                    int readLen = audioRecord.read(recBuf, 0, recBufSize);
                    thread1.ou.write(recBuf);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public byte[] toByteArray(int iSource, int iArrayLen) {
        byte[] bLocalArr = new byte[iArrayLen];
        for (int i = 0; (i < 4) && (i < iArrayLen); i++) {
            bLocalArr[i] = (byte) (iSource >> 8 * i & 0xFF);
        }
        return bLocalArr;
    }
    public int toInt(byte[] bRefArr) {
        int iOutcome = 0;
        byte bLoop;

        for (int i = 0; i < bRefArr.length; i++) {
            bLoop = bRefArr[i];
            iOutcome += (bLoop & 0xFF) << (8 * i);
        }
        return iOutcome;
    }
}
