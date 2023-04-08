package cz.my.snemovna.service;

import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The util class for work with urls.
 */
@Service
public class UrlUtils {

    /**
     * The method checks if given url exist.
     * @param urlData the url.
     * @return 'true' if url exist, otherwise 'false'.
     */
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

    /**
     * The method get the vote url.
     * @param voteId vote id.
     * @return vote url.
     */
    public String getVoteUrl(final Long voteId) {
        return String.format("https://www.psp.cz/sqw/hlasy.sqw?g=%s&l=cz", voteId);
    }

    /**
     * The method get the member photo url.
     * @param year start year of user period.
     * @param personId person id.
     * @return member photo url.
     */
    public String getMemberPhotoUrl(final int year, final Long personId) {
        return String.format("https://www.psp.cz/eknih/cdrom/%sps/eknih/%sps/poslanci/i%s.jpg", year, year, personId)
                .replace("1992", "1993"); // fix old years
    }

    /**
     * The method get the member url.
     * @param personId person id.
     * @return member url.
     */
    public String getPspMemberUrl(final Long personId) {
        return String.format("https://www.psp.cz/sqw/detail.sqw?id=%s", personId);
    }
}
