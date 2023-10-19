package util;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtils {
    /**
     * 获取 HttpURLConnection 链接对象
     * @param url
     * @return
     */
    public static HttpURLConnection getHttpURLConnection(String url) throws MalformedURLException {
        URL httpUrl = new URL(url);
    }
}
