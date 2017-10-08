package com.gmail.blackbull8810.noiseapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.anastr.speedviewlib.PointerSpeedometer;
import com.github.anastr.speedviewlib.ProgressiveGauge;
import com.github.anastr.speedviewlib.SpeedView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class GauseFragment extends Fragment {

    //TCP 연결 관련..
    Socket socket;
    private int port = 5000;
    private final String serverIp = "172.20.10.5";
    //private final String serverIp = "192.168.0.145";
    //private final String serverIp = "172.17.46.3";
    //private final String serverIp = "locahost";
    //private final String serverIp = "192.168.0.4";
    private PrintWriter socketOut;
    DataOutputStream out;
    private MyHandler myHandler;
    String time;

    TextView textView;

    SpeedView speedView;
    PointerSpeedometer pointerSpeedometer;
    ProgressiveGauge progressiveGauge;
    View view;
    FrameLayout fl;

    SQLiteDatabase database;

    int sum,avg,max;
    ArrayList<String> listvalue,listday,listvaluekorea = null;
    private ThirdFragment.ListViewAdapter mAdapter = null;

    public GauseFragment() {
        // Required empty public constructor
    }

    public static GauseFragment newInstance() {

        Bundle args = new Bundle();

        GauseFragment fragment = new GauseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_gause, container, false);

        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat nowtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time = nowtime.format(date);

        // StrictMode는 개발자가 실수하는 것을 감지하고 해결할 수 있도록 돕는 일종의 개발 툴
        // - 메인 스레드에서 디스크 접근, 네트워크 접근 등 비효율적 작업을 하려는 것을 감지하여
        //   프로그램이 부드럽게 작동하도록 돕고 빠른 응답을 갖도록 함, 즉  Android Not Responding 방지에 도움
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        view = inflater.inflate(R.layout.test_layout, container, false);

        myHandler = new MyHandler();

//        textView = (TextView) view.findViewById(R.id.textView);
        fl = (FrameLayout) view.findViewById(R.id.layout);

//        pointerSpeedometer= (PointerSpeedometer) view.findViewById(R.id.pointerSpeedometer);
//        pointerSpeedometer.setMaxSpeed(120);

        progressiveGauge = (ProgressiveGauge) view.findViewById(R.id.progressiveGauge);
        progressiveGauge.setMaxSpeed(120);

//        textView.setVisibility(View.GONE);

        try {

            socket = new Socket();
//            socket.connect(new InetSocketAddress(ip, port), 1000);
//            socket = new Socket(serverIp, port); // 소켓을 생성하여 연결을 요청한다.
            socket.connect(new InetSocketAddress(serverIp, port), 1000);
            System.out.println("서버에 연결되었습니다.");

            //보내는 PrintWriter..
            out = new DataOutputStream(socket.getOutputStream());

            // 메시지 전송용 Thread 생성
            Thread sender = new Thread(new ClientSender(socket));
            // 메시지 수신용 Thread 생성
            Thread receiver = new Thread(new ClientReceiver(socket));
            System.out.println("메시지 송수신 Thread 생성");

            sender.start();
            receiver.start();

        }
         catch (IOException e) {

            Toast.makeText(view.getContext(), "Connection problem..\n 서버와의 연결이 안됩니다..", Toast.LENGTH_LONG).show();
        }

        return view;

    }

    class ClientReceiver implements Runnable {
        Socket socket;
        DataInputStream in;
        String FromServer;
        BufferedReader inFromServer;
        String eData;
//        TextView textView = (TextView) findViewById(R.id.textView);

        // 생성자
        ClientReceiver(Socket socket) {
            this.socket = socket;

            try {
                // 서버로 부터 데이터를 받을 수 있도록 DataInputStream 생성
                //this.in = new DataInputStream(socket.getInputStream());
                inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
            }
        }

        public void run() {

            while(inFromServer != null) {
                try {
                    System.out.println("111111111111111");
                    // InputStream의 값을 읽어와서 data에 저장
                    FromServer = inFromServer.readLine();

                    // Message 객체를 생성, 핸들러에 정보를 보낼 땐 이 메세지 객체를 이용
                    Message msg = myHandler.obtainMessage();
                    msg.obj = FromServer;
                    myHandler.sendMessage(msg);

                } catch (IOException e) {
                    e.printStackTrace();

                    break;
                }

            }

        }
    }//thread_end

    //쓰레드 핸들러
    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            String data1 = msg.obj.toString();
            //String to int 형변환.
            float data2 = Float.parseFloat(data1);

            textView.setText(data1);
            //speedView.speedTo(data2);
            //pointerSpeedometer.speedTo(data2);
            progressiveGauge.speedTo(data2);

            //80이상 소음 발생시 노티발생.
            if (data2 > 80) {

                AlarmManager alarm = (AlarmManager) view.getContext().getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(view.getContext(),AlarmReceive.class);
                PendingIntent pender = PendingIntent.getBroadcast(view.getContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                Calendar calendar = Calendar.getInstance();
                calendar.set(2017,2,25,1,20,0);
                calendar.set(Calendar.MILLISECOND,0);
                alarm.set(AlarmManager.RTC, calendar.getTimeInMillis(), pender);

                NoiseBackgroundChange_alarm();

                // 잠든 단말을 깨워라.
                PushWakeLock.acquireCpuWakeLock(view.getContext());

                // WakeLock 해제. (wakeup 다음 꼭 해제 해줘야함.)
                PushWakeLock.releaseCpuLock();
//                SystemClock.sleep(1000);

                DBHelper test = new DBHelper(getContext());
                database = test.getReadableDatabase();

                int data3 = (int)data2;

                String sql = "insert into noisevalue values(null,'"+data3+"','"+time+"','위험 수치');";
                database.execSQL(sql);

//                mAdapter.notifyDataSetChanged();

            } else {
                NoiseBackgroundChange_base();
//                Toast.makeText(view.getContext(), data1, Toast.LENGTH_LONG).show();
            }//노티
        }
    }//Handler end..

    void InsertNoiseDatabase() {

    }

    void NoiseBackgroundChange_base() {

        fl.setBackgroundColor(Color.parseColor("#50CCE7"));
        pointerSpeedometer.setBackgroundCircleColor(Color.parseColor("#50CCE7"));

    }
    void NoiseBackgroundChange_alarm() {

        fl.setBackgroundColor(Color.RED);
//        pointerSpeedometer.setCenterCircleColor(Color.RED);
//        pointerSpeedometer.setBackgroundCircleColor(Color.RED);
        progressiveGauge.setBackgroundColor(Color.RED);
//        fl.setVisibility();
//        mp.start();
        Toast.makeText(view.getContext(), "층간 소음이 심합니다.!!!!!", Toast.LENGTH_SHORT).show();
//        SystemClock.sleep(3000);
    }

}
