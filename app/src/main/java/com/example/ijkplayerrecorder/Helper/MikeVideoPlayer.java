package com.example.ijkplayerrecorder.Helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.FFmpeg;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoWriter;

import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;


public class MikeVideoPlayer implements TextureView.SurfaceTextureListener {


    private Surface surface;
    private Context context;
    private TextureView textureView;
    private IMediaPlayer mMediaPlayer=null;


    private final static String videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
    private String saveVideoPath  = "/storage/emulated/0/Test/";
    private String fileName = "";



    private VideoWriter videoWriter;
    private boolean isRecord =false;
    private ArrayList<Mat> videoBuffer = new ArrayList<Mat>();
    private Size videoSize =new Size(1280,720);
    private Long startRecordTime;
    public MikeVideoPlayer (Context context,TextureView textureView)
    {
        this.context=context;
        this.textureView=textureView;
        textureView.setSurfaceTextureListener(this);
    }
    private void createPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.setDisplay(null);
            mMediaPlayer.release();
        }
        IjkMediaPlayer ijkMediaPlayer = new IjkMediaPlayer();
        ijkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG);


        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);

        mMediaPlayer = ijkMediaPlayer;

    }



    @Override
    public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {
        surface =new Surface(surfaceTexture);
        createPlayer();
        try {
            mMediaPlayer.setDataSource(context, Uri.parse(videoUrl));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.setSurface(surface);
        mMediaPlayer.prepareAsync();
    }

    @Override
    public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surfaceTexture) {
        //System.out.println("Update");
        if (isRecord) {
            Bitmap bitmap = textureView.getBitmap();
            Mat mat = new Mat();
            Bitmap bmp32 = bitmap.copy(Bitmap.Config.RGB_565, true);
            Utils.bitmapToMat(bmp32, mat);
            Imgproc.resize(mat,mat,videoSize);
            videoBuffer.add(mat);
        }
    }

    public boolean RecordVideo(String fileName)
    {
        this.fileName =fileName;
        isRecord =!isRecord;
        if (isRecord) {
            startRecordTime =System.currentTimeMillis();
        }
        else
        {
            Long time = System.currentTimeMillis()-startRecordTime;
            double fps  = videoBuffer.size()/(time/1000.0);
            videoWriter =new VideoWriter();
            videoWriter.open(saveVideoPath+"videoIndex.avi",VideoWriter.fourcc('M','J','P','G'),fps,videoSize);
            for (int i =0;i<videoBuffer.size();i++) {
                videoWriter.write(videoBuffer.get(i));
            }
            videoBuffer.clear();
            videoWriter.release();
            videoWriter=null;
            Timer timer =new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    convertFFmpegConverVideo();
                }
            },3000);
        }
        return isRecord;
    }



    public void setVideoSize (int width,int height)
    {
        videoSize.width =width;
        videoSize.height =height;
    }
    public void setSaveVideoPath (String path)
    {
        saveVideoPath =path;
    }

    private void convertFFmpegConverVideo ()
    {
        String inputFile =saveVideoPath+"videoIndex.avi";
        String outputFile=saveVideoPath +fileName+".mp4";
        int rc = FFmpeg.execute(String .format("-i %s -vcodec libx264 -s %sx%s %s",inputFile,(int)videoSize.width,(int)videoSize.height,outputFile,inputFile));
        if (rc == RETURN_CODE_SUCCESS) {
            Log.i(Config.TAG, "Command execution completed successfully.");
        } else if (rc == RETURN_CODE_CANCEL) {
            Log.i(Config.TAG, "Command execution cancelled by user.");
        } else {
            Log.i(Config.TAG, String.format("Command execution failed with rc=%d and the output below.", rc));
            Config.printLastCommandOutput(Log.INFO);
        }
    }






}
