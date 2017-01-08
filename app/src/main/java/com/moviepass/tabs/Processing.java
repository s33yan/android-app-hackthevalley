package com.moviepass.tabs;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

public class Processing extends AppCompatActivity {

    boolean switched = false;
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://52.229.115.195");
        } catch (Exception e) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing);
        mSocket.connect();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent i = new Intent(getApplicationContext(), Watching.class);
//                startActivity(i);
//                finish();
//            }
//        }, 4000);
        mSocket.on("purchased", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(!switched) {
                                    Intent intent = new Intent(getApplicationContext(), Watching.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                }
        );
    }
}
