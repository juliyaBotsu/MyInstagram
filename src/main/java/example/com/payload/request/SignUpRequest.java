package example.com.payload.request;

import example.com.annotations.PasswordMatches;
import example.com.annotations.ValidEmail;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class SignUpRequest {

    @Email(message = "could be email format")
    @NotBlank(message = "User email is required")
    @ValidEmail
    private String email;
    @NotEmpty(message = "Please enter your name")
    private String name;
    @NotEmpty(message = "Please enter your lastname")
    private String lastname;
    @NotEmpty(message = "Please enter your username")
    private String username;
    @NotEmpty(message = "Please enter your password")
    @Size(min = 8)
    private String password;
    @NotEmpty(message = "Please enter your password again")
    @Size(min = 8)
    private String confirmPassword;
}
