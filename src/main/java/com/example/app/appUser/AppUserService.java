package com.example.app.appUser;

import com.example.app.Registration.RegistrationController;
import com.example.app.Registration.RegistrationToken.ConfirmationToken;
import com.example.app.Registration.RegistrationToken.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AppUserService implements UserDetailsService {
    //reference to repository
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final static String User_Not_Found = "User with email %s not found";
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(String.format(User_Not_Found, email)));
    }

    public String signUpUser(AppUser appUser){
        boolean userExist = userRepository.findByEmail(appUser.getEmail()).isPresent();

        if(userExist){
            throw new IllegalStateException("Email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        userRepository.save(appUser);

        //TODO: Send confirmation token
        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusSeconds(50),
                appUser
        );

        confirmationTokenService.saveConfirmationtoken(confirmationToken);

        //TODO: Email Send

        return token;
    }
}
