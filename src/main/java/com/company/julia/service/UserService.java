package com.company.julia.service;

import com.company.julia.dto.UserDTO;
import com.company.julia.entity.UserApp;
import com.company.julia.entity.enums.ERole;
import com.company.julia.exeptions.UserExistException;
import com.company.julia.payload.request.SignUpRequest;
import com.company.julia.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserService {
    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserApp createUser(SignUpRequest userIn) {
        UserApp user = new UserApp();
        user.setEmail(userIn.getEmail());
        user.setName(userIn.getName());
        user.setLastname(userIn.getLastname());
        user.setUsername(userIn.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userIn.getPassword()));
        user.getRoles().add(ERole.ROLE_USER);

        try {
            LOG.info("Saving user{}", userIn.getEmail());
            return userRepository.save(user);
        } catch (Exception e) {
            LOG.error("Error of the registration" + e.getMessage());
            throw new UserExistException("The user has already exist");
        }


    }

    //todo principal will consist datta about user
    public UserApp updateUser(UserDTO userDTO, Principal principal) {
        UserApp user  = getUserByPrincipal(principal);
        user.setName(userDTO.getName());
        user.setLastname(userDTO.getLastname());
        user.setBio(userDTO.getBio());

        return userRepository.save(user);
    }

    public UserApp getCurrentUser(Principal principal){
        return getUserByPrincipal(principal);
    }

    private UserApp getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserAppByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User can't found"));
    }
}
