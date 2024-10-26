package org.indulge.hom8.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.SecureRandom;
import java.util.Collection;

public interface SecurityUtil {

    static String extractUserRole(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        if (!authorities.isEmpty()) {
            GrantedAuthority firstAuthority = authorities.iterator().next();
            return firstAuthority.getAuthority();
        }
        return null;
    }

    static String generateRandomOtp() {
        SecureRandom random = new SecureRandom();
        int randomNumber = random.nextInt(999999);
        return String.format("%06d", randomNumber);
    }
}
