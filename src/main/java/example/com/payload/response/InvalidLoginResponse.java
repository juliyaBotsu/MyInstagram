package example.com.payload.response;

import lombok.Getter;

@Getter
public class InvalidLoginResponse {
    private String login;
    private String password;

    public InvalidLoginResponse(){
        this.login = "Invalid username";
        this.password = "Invalid password";
    }
}
