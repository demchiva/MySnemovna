package cz.my.snemovna.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * User detail service for authentication.
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final String TEST_USER = "test";

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        LOGGER.debug("Entering in loadUserByUsername Method.");

        if(!TEST_USER.equals(username)) {
            LOGGER.error("Username not found: " + username);
            throw new UsernameNotFoundException("Could not found user with username: " + username);
        }

        LOGGER.info("User Authenticated Successfully.");

        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(new SimpleGrantedAuthority("USER"));
            }

            @Override
            public String getPassword() {
                return "qwer228";
            }

            @Override
            public String getUsername() {
                return TEST_USER;
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}
