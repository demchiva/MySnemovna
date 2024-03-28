package cz.my.snemovna.jpa.model.users;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The application user permissions.
 */
@Getter
@RequiredArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete");

    private final String permission;
}
