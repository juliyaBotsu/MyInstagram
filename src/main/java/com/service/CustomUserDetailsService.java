package com.service;

import com.entity.UserApp;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * FOR SECURITY CONFIG
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static UserApp build(UserApp userApp) {
        List<GrantedAuthority> authorities = userApp.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
        return new UserApp(
                userApp.getId(),
                userApp.getUsername(),
                userApp.getEmail(),
                userApp.getPassword(),
                authorities);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserApp userApp = userRepository.findUserAppByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return build(userApp);
    }

    public UserApp loadUserById(Long id) {
        return userRepository.findUserAppById(id).orElse(null);
    }
}
