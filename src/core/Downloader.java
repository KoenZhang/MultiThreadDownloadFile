package core;

import constant.CommonConstant;
import util.HttpUtils;

import java.io.*;
import java.net.HttpURLConnection;

public class Downloader {
    public void download(String url) {
        // 获取文件名
        String httpFileName = HttpUtils.getHttpFileName(url);
        httpFileName = CommonConstant.DOWNLOAD_PATH + httpFileName;

        // 获取链接对象
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = HttpUtils.getHttpURLConnection(url);
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
            while ((len = bis.read()) != -1) {
                bos.write(len);
            }

        } catch (FileNotFoundException e) {
            System.out.println("下载的文件不存在");
        } catch (Exception e) {
            System.out.println("下载失败");
        } finally {
            // 关闭链接对象
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }
}
