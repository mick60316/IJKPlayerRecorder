package com.example.ijkplayerrecorder.Fragment;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.example.ijkplayerrecorder.R;

public class HomeFragment extends Fragment {
    private NavController navController;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button recordVideoButton, videoPreviewButton;
        recordVideoButton =view.findViewById(R.id.record_video_button);
        videoPreviewButton =view.findViewById(R.id.video_preview_button);
        recordVideoButton.setOnClickListener(onClickListener);
        videoPreviewButton.setOnClickListener(onClickListener);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        navController = findNavController(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }
    View.OnClickListener onClickListener =new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.record_video_button:
                    navController.navigate(R.id.action_homeFragment_to_recordVideoFragment);
                    break;
                case R.id.video_preview_button:
                    navController.navigate(R.id.action_homeFragment_to_videoPreviewFragment);
                    break;
                default:
                    break;

            }

        }
    };
}
