package com.example.ijkplayerrecorder.Helper;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.ijkplayerrecorder.DataStruct.VideoPreviewInfo;

import java.util.HashMap;

public class VideoPreviewHelper extends AsyncTask<VideoPreviewInfo,Integer ,Bitmap> {
    private final int GET_VIDEO_PREVIEW=0;
    private ImageView imageView;
    private Handler UIHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_VIDEO_PREVIEW:
                    imageView.setImageBitmap((Bitmap)msg.obj);
                    break;
                default:
                    break;
            }
        }
    };


    public VideoPreviewHelper (ImageView imageView)
    {
        this.imageView=imageView;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected Bitmap doInBackground(VideoPreviewInfo... videoPreviewInfos) {
        MediaMetadataRetriever mediaMetadataRetriever=new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(videoPreviewInfos[0].url,new HashMap());
            Bitmap bp=mediaMetadataRetriever.getFrameAtIndex(videoPreviewInfos[0].frameIndex);
            return bp;
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
            Log.e("VideoPreviewHelper","Get Video fail");
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (bitmap ==null)
        {
            Log.e("VideoPreviewHelper","");
            return ;
        }
        Message msg = new Message();
        msg.what = GET_VIDEO_PREVIEW;
        msg.obj=bitmap;
        UIHandler.sendMessage(msg);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}


