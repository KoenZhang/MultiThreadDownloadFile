package util;

import constant.RequestConstant;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtils {


    /**
     * 获取 HttpURLConnection 链接对象
     * @param url
     * @return
     */
    public static HttpURLConnection getHttpURLConnection(String url) throws IOException {
        URL httpUrl = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();

        // 向文件服务器发送标识信息
        httpURLConnection.setRequestProperty(RequestConstant.USER_AGENT_KEY, RequestConstant.USER_AGENT_VALUE);


        return httpURLConnection;
    }

    /**
     * 获取下载链接文件名
     * @param url
     * @return
     */
    public static String getHttpFileName (String url) {
        int index = url.lastIndexOf(RequestConstant.LEFT_SLASH);
        return url.substring(index + 1);
    }
}
