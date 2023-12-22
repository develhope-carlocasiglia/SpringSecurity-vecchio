package co.develhope.LoginDemo.auth.entities;

import co.develhope.LoginDemo.user.entities.User;
import lombok.Data;

@Data
public class LoginRTO {

    private User user;
    private String JWT;
}
