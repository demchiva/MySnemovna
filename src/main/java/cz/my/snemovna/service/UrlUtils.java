package cz.my.snemovna.service;

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

    public String getVoteUrl(final Long voteId) {
        return String.format("https://www.psp.cz/sqw/hlasy.sqw?g=%s&l=cz", voteId);
    }

    public String getMemberPhotoUrl(final int year, final Long memberId) {
        return String.format("https://www.psp.cz/eknih/cdrom/%sps/eknih/%sps/poslanci/i%s.jpg", year, year, memberId);
    }
}
