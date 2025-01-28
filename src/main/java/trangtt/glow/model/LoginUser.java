package trangtt.glow.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginUser {
    private String username;
    private String password;
}
