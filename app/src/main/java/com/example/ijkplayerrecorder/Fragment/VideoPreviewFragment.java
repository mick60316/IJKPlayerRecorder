package com.example.ijkplayerrecorder.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ijkplayerrecorder.Components.RecycleViewAdapter;
import com.example.ijkplayerrecorder.DataStruct.VideoPreviewInfo;
import com.example.ijkplayerrecorder.Helper.VideoPreviewHelper;
import com.example.ijkplayerrecorder.R;

import java.util.Arrays;
import java.util.List;

public class VideoPreviewFragment extends Fragment {
    private Context context;
    private ConstraintLayout rootLayout;
    List<VideoPreviewInfo> testData = Arrays.asList(
            new VideoPreviewInfo("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",50),
            new VideoPreviewInfo("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",50),
            new VideoPreviewInfo("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",50),
            new VideoPreviewInfo("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",50),
            new VideoPreviewInfo("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",50),
            new VideoPreviewInfo("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4",50),
            new VideoPreviewInfo("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",50),
            new VideoPreviewInfo("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",50),
            new VideoPreviewInfo("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",50),
            new VideoPreviewInfo("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/VolkswagenGTIReview.mp4",50),
            new VideoPreviewInfo("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4",50),
            new VideoPreviewInfo("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4",50)
    );
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rootLayout=view.findViewById(R.id.root_layout);

        ScrollView scrollView=new ScrollView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(layoutParams);

        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(linearParams);

        for (VideoPreviewInfo videoPreviewInfo: testData)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            View _view = inflater.inflate(R.layout.vidoe_preview_item, null, false);
            TextView _textview =_view.findViewById(R.id.video_url);
            ImageView _imageview =_view.findViewById(R.id.video_preview);
            _textview.setText(videoPreviewInfo.url);
            VideoPreviewHelper videoPreviewHelper =new VideoPreviewHelper(_imageview);
            videoPreviewHelper.execute(new VideoPreviewInfo(videoPreviewInfo.url,videoPreviewInfo.frameIndex));
            linearLayout.addView(_view);
        }
        scrollView.addView(linearLayout);
        rootLayout.addView(scrollView);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.video_preview_fragment, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
        AppCompatActivity activity =(AppCompatActivity) context;
        activity.getSupportActionBar().setTitle("Video Preview");

    }
}
