package com.example.jjangushrine.config.security.sevice;

import com.example.jjangushrine.config.security.entity.CustomUserDetails;
import com.example.jjangushrine.domain.seller.entity.Seller;
import com.example.jjangushrine.domain.seller.service.SellerService;
import com.example.jjangushrine.domain.user.entity.User;
import com.example.jjangushrine.domain.user.service.UserService;
import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.common.InvalidException;
import com.example.jjangushrine.exception.common.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;
    private final SellerService sellerService;

    @Override
    public UserDetails loadUserByUsername(String email)  {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()) {
            return new CustomUserDetails(userOptional.get());
        }

        Optional<Seller> sellerOptional = sellerService.findByEmail(email);
        if (sellerOptional.isPresent()) {
            return new CustomUserDetails(sellerOptional.get());
        }

        throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
    }

    public Authentication createAuthentication(Object principal) {
        CustomUserDetails userDetails;

        if (principal instanceof User) {
            userDetails = new CustomUserDetails((User) principal);
        } else if (principal instanceof Seller) {
            userDetails = new CustomUserDetails((Seller) principal);
        } else {
            throw new InvalidException(ErrorCode.INVALID_PRINCIPAL_TYPE);
        }

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }
}
