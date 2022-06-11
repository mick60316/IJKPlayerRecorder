package com.example.ijkplayerrecorder.Components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ijkplayerrecorder.DataStruct.VideoPreviewInfo;
import com.example.ijkplayerrecorder.Helper.VideoPreviewHelper;
import com.example.ijkplayerrecorder.R;

import java.util.List;


public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>  {
    private Context context;
    private List<VideoPreviewInfo> videoInfos;


    public RecycleViewAdapter (Context context, List<VideoPreviewInfo> videoInfos)
    {
        this.context = context;
        this.videoInfos = videoInfos;

    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        ImageView video_preview;
        TextView video_url;
        private ViewHolder(View itemView){
            super(itemView);
            video_preview= itemView.findViewById(R.id.video_preview);
            video_url= itemView.findViewById(R.id.video_url);

        }

    }
    @NonNull
    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.vidoe_preview_item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapter.ViewHolder holder, int position) {
        final VideoPreviewInfo videoInfo =videoInfos.get(position);
        holder.video_url.setText(videoInfo.url);
        VideoPreviewHelper videoPreviewHelper =new VideoPreviewHelper(holder.video_preview);
        videoPreviewHelper.execute(new VideoPreviewInfo(videoInfo.url,videoInfo.frameIndex));
    }

    @Override
    public int getItemCount() {
        return videoInfos.size();
    }
}
