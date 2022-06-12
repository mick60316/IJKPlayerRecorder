package com.example.ijkplayerrecorder.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ijkplayerrecorder.Helper.MikeVideoPlayer;
import com.example.ijkplayerrecorder.R;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.videoio.VideoCapture;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RecordVideoFragment extends Fragment {
    private TextureView textureView;
    private MikeVideoPlayer mikeVideoPlayer;
    private Button recordButton;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.record_video_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linkUserInterface(view);

    }


    void linkUserInterface (View view)
    {
        mikeVideoPlayer=new MikeVideoPlayer(context,(TextureView)view.findViewById(R.id.video_textureview));
        recordButton =view.findViewById(R.id.record_button);
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
        if (OpenCVLoader.initDebug()) {
            Log.i("mike", "opencv loaded");
            baseLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        } else {
            Log.i("mike", "opencv not loaded");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, context, baseLoaderCallback);
        }
        AppCompatActivity activity =(AppCompatActivity) context;
        activity.getSupportActionBar().setTitle("Record Video");

    }

    BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(context) {
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