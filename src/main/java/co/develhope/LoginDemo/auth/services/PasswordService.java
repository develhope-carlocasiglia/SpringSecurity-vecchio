package co.develhope.LoginDemo.auth.services;

import co.develhope.LoginDemo.auth.entities.RequestPasswordDTO;
import co.develhope.LoginDemo.auth.entities.RestorePasswordDTO;
import co.develhope.LoginDemo.notification.services.MailNotificationService;
import co.develhope.LoginDemo.user.entities.User;
import co.develhope.LoginDemo.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PasswordService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailNotificationService mailNotificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User request(RequestPasswordDTO requestPasswordDTO) throws Exception {
        // verifico se sul DB è presente un user con una certa email
        User userFromDB = userRepository.findByEmail(requestPasswordDTO.getEmail());
        // se user è uguale a null lancio un'eccezione
        if(userFromDB == null || !userFromDB.isActive()) throw new Exception("Cannot find user");
        //assegno un codice temporaneo
        userFromDB.setPasswordResetCode(UUID.randomUUID().toString());
        mailNotificationService.sendPasswordResetMail(userFromDB);
        //salvo user
        return userRepository.save(userFromDB);
    }

    public User restore(RestorePasswordDTO restorePasswordDTO) throws Exception{
        User userFromDB = userRepository.findByPasswordResetCode(restorePasswordDTO.getResetPasswordCode());
        if(userFromDB == null) throw new Exception("Cannot find user");
        userFromDB.setPassword(passwordEncoder.encode(restorePasswordDTO.getNewPassword()));
        userFromDB.setPasswordResetCode(null);

        //activate the user
        userFromDB.setActive(true);
        userFromDB.setActivationCode(null);

        return userRepository.save(userFromDB);
    }
}
