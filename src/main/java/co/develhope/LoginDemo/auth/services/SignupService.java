package co.develhope.LoginDemo.auth.services;

import co.develhope.LoginDemo.auth.entities.SignupActivationDTO;
import co.develhope.LoginDemo.auth.entities.SignupDTO;
import co.develhope.LoginDemo.notification.services.MailNotificationService;
import co.develhope.LoginDemo.user.entities.Role;
import co.develhope.LoginDemo.user.entities.User;
import co.develhope.LoginDemo.user.repositories.RoleRepository;
import co.develhope.LoginDemo.user.repositories.UserRepository;
import co.develhope.LoginDemo.user.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class SignupService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MailNotificationService mailNotificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User signup(SignupDTO signupDTO) throws Exception { //SignupDTO rappresenta in Java l'oggetto body su Postman
        User userInDB = userRepository.findByEmail(signupDTO.getEmail());
        if(userInDB != null) throw new Exception("User already exist");
        // creo nuovo user e setto i parametri necessari
        User user = new User();
        user.setName(signupDTO.getName());
        user.setEmail(signupDTO.getEmail());
        user.setSurname(signupDTO.getSurname());
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));// la password viene codificata con PasswordEncoder
        //user.setActive(false); // aggiunto parametro in User class

        //genera un codice univoco di 36 caratteri
        user.setActivationCode(UUID.randomUUID().toString());

        Set<Role> roles = new HashSet<>();
        Optional<Role> userRole =roleRepository.findByName(Roles.REGISTERED);
        if(!userRole.isPresent()) throw new Exception("Cannot set user role");
        roles.add(userRole.get());
        user.setRoles(roles);

        mailNotificationService.sendActivationEmail(user);// invio mail di attivazione
        return userRepository.save(user); // ritorniamo l'user salvato
    }

    //possiamo dare una scadenza a questo activation code activationCodeExpirationDate
    public User activate(SignupActivationDTO signupActivationDTO) throws Exception {
        User user = userRepository.findByActivationCode(signupActivationDTO.getActivationCode());
        if(user == null) throw  new Exception("User not found");
        user.setActive(true);
        user.setActivationCode(null);
        return userRepository.save(user);
    }
}
