package com.nisum.challenge.security;

import com.nisum.challenge.entity.User;
import com.nisum.challenge.exception.UserNotFundException;
import com.nisum.challenge.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        log.debug("Entering in loadUserByUsername Method...");
        Optional<User> user = userRepository.findByEmailAndIsActiveTrue(email);
        if(user.isEmpty()){
            log.error("User email not found: " + email);
            throw new UsernameNotFoundException("could not found user..!!");
        }
        log.info("User Authenticated Successfully..!!!");
        return new CustomUserDetails(user.orElseThrow(() -> new UserNotFundException("User not found")));
    }

}
