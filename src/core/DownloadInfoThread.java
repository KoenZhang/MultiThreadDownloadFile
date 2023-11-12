package core;

import constant.CommonConstant;

public class DownloadInfoThread implements Runnable{

    // 下载文件总大小
    private long httpFileContentLength;

    // 本地已下载总大小
    private double finishdSize;

    // 当前下载的总数
    public volatile double downSize;

    // 前一秒下载的总数
    private double prevSize;

    public DownloadInfoThread(long httpFileContentLength) {
        this.httpFileContentLength = httpFileContentLength;
    }


    @Override
    public void run() {
        // 计算文件总大小 单位：mb
        String httpFileSize = String.format("%.2f", httpFileContentLength / CommonConstant.MB);

        // 计算每秒下载速度 kb
        int speed = (int) ((downSize - prevSize) / 1024d);
        prevSize = downSize;

        // 剩余文件的大小
        double remianSize = httpFileContentLength - finishdSize - downSize;

        // 计算剩余时间
        String remainTime = String.format("%1f", remianSize / 1024d / speed);
        if ("Infinity".equalsIgnoreCase(remainTime)) {
            remainTime = "-";
        }

        // 已下载大小
        String currentFileSize =  String.format("%.2f", (downSize - finishdSize) / CommonConstant.MB);

        String downInfo = String.format("已下载 %smb/%smb, 速度 %skb/s, 剩余时间 %ss", currentFileSize,  httpFileSize, speed, remainTime);

        System.out.print("\r");
        System.out.print(downInfo);
    }
}
