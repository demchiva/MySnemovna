package cz.my.snemovna.service.loader;

import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class UrlUtils {

    public boolean isUrlExist(final String urlData) {
        try {
            final URL url = new URL(urlData);
            final HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("HEAD");
            int responseCode = huc.getResponseCode();
            return HttpURLConnection.HTTP_OK == responseCode;
        } catch (Exception e) {
            return false;
        }
    }
}
