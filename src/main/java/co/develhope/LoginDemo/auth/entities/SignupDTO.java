package co.develhope.LoginDemo.auth.entities;

import lombok.Data;


//serve solo per trasferire i dati del signup
@Data
public class SignupDTO {

    /** User name */
    private String name;
    /** User surname */
    private String surname;

    /** User email */
    private String email;
    /** User password clear */
    private String password;

}
