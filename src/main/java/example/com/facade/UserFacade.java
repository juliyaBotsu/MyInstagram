package example.com.facade;

import example.com.dto.UserDTO;
import example.com.entity.UserApp;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {
    public UserDTO userToUserDTO(UserApp user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setLastname(user.getLastname());
        userDTO.setBio(user.getBio());
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }
}
