package cz.my.snemovna.dto.members;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * The dto class of member detail.
 */
public record MemberDetailDto(Long memberId, String photo, String name, String party,
                              String region, LocalDate dateFrom, LocalDate dateTo,
                              OfficeAddress officeAddress, String ownPageUrl, String facebook,
                              String pspUrl, String email, LocalDate birthDate) implements Serializable {

    public record OfficeAddress(String street, String municipality, String zip, String phone) {
    }
}
