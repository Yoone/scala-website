package util;

import java.io.*;
import java.net.URLDecoder;

/**
 * Created by mecavity on 10/07/15.
 */
public class URLHandler {
    public static String decode(String url) {
        try {
            return URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
