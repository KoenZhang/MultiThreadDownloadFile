package core;

import constant.CommonConstant;
import util.FileUtils;
import util.HttpUtils;
import util.LogUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Downloader {

    public ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public void download(String url) {
        // 获取文件名
        String httpFileName = HttpUtils.getHttpFileName(url);
        httpFileName = CommonConstant.DOWNLOAD_PATH + httpFileName;

        // 获取文件的大小
        long localFileLength = FileUtils.getFileContentLength(httpFileName);

        // 获取链接对象
        HttpURLConnection httpURLConnection = null;
        DownloadInfoThread downloadInfoThread = null;
        try {
            httpURLConnection = HttpUtils.getHttpURLConnection(url);
            // 获取下载文件的总大小
            int contentLength = httpURLConnection.getContentLength();
            if (localFileLength >= contentLength) {
                LogUtils.info("{}已下载完毕，无需重新下载", httpFileName);
            }

            // 创建获取下载信息的任务对象
            downloadInfoThread = new DownloadInfoThread(contentLength);
            // 将任务交给线程执行，每隔 1 秒执行一次
            scheduledExecutorService.scheduleAtFixedRate(downloadInfoThread, 1, 1, TimeUnit.SECONDS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (
                InputStream input = httpURLConnection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(input);
                FileOutputStream fos = new FileOutputStream(httpFileName);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
        ) {
            int len = -1;
            byte[] buffer = new byte[CommonConstant.BYTE_SIZE];
            while ((len = bis.read(buffer)) != -1) {
                downloadInfoThread.downSize += len;
                bos.write(buffer, 0, len);
            }

        } catch (FileNotFoundException e) {
            LogUtils.error("下载的文件不存在{}", url);
        } catch (Exception e) {
            LogUtils.error("下载失败");
        } finally {
            System.out.println("\r");
            System.out.println("下载完成");
            // 关闭链接对象
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }

            // 关闭线程池
            scheduledExecutorService.shutdown();
        }
    }
}
