# IJKPlayerRecorder

## Overview

提供IJKPlayer串接Http的[影片](http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4)，並且利用OpenCV將影片側錄下來。

## 程式流程:

 IJKPlayer串接HTTP影片

 利用Textureview將IJK的影像接出來轉成Bitmap

 Bitmap轉換成OpenCV所使用的Mat

 利用OpenCV VideoWriter將影像寫成video

 因播放格式的關係，需要再利用ffmpeg將video recoode一次，轉換成手機能撥放的格式

## Install:

[OpenCV for android](https://www.mdeditor.tw/pl/pYbq/zh-tw)

[IJKplayer](https://www.jianshu.com/p/c5d972ab0309)

[FFmpeg](/tanersener/mobile-ffmpeg)
