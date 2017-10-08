package com.gmail.blackbull8810.noiseapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Jun on 2017. 9. 10..
 */

public class Intro extends Activity {

    private Handler handler;

    Runnable r = new Runnable() {
        @Override
        public void run() {
    // 4초뒤에 다음화면(MainActivity)으로 넘어가기 Handler 사용
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent); // 다음화면으로 넘어가기
            finish(); // Activity 화면 제거
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro); // xml과 java소스를 연결
        init();
        handler.postDelayed(r, 1000);
    } // end of onCreate

    @Override
    protected void onResume() {
        super.onResume();
    // 다시 화면에 들어어왔을 때 예약 걸어주기
        //handler.postDelayed(r, 2000); // 4초 뒤에 Runnable 객체 수행
    }

    public void init() {
        handler = new Handler();
    }

    @Override
    protected void onPause() {
        super.onPause();
    // 화면을 벗어나면, handler 에 예약해놓은 작업을 취소하자
        handler.removeCallbacks(r); // 예약 취소
    }
}