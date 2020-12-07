package com.example.ijkplayerrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextureView textureView;
    MikeVideoPlayer mikeVideoPlayer;
    private Button recordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mikeVideoPlayer=new MikeVideoPlayer(this,(TextureView)findViewById(R.id.video_textureview));
        recordButton =findViewById(R.id.record_button);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isRecord =  mikeVideoPlayer.RecordVideo(getDateAndTime());
                String buttonText = isRecord?"Stop":"Record";
                recordButton.setText(buttonText);


            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (OpenCVLoader.initDebug()) {
            Log.i("mike", "opencv loaded");
            baseLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);



        } else {
            Log.i("mike", "opencv not loaded");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, baseLoaderCallback);
        }
    }

    BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            super.onManagerConnected(status);
            switch (status) {
                case BaseLoaderCallback.SUCCESS: {
                    Log.i("mike","load success");

                    break;
                }
                default: {
                    super.onManagerConnected(status);
                    break;
                }
            }
        }
    };

    private String getDateAndTime ()
    {
        String currentTime = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
        return currentTime;
    }


}