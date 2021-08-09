package com.example.app.Registration.RegistrationToken;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    public void saveConfirmationtoken(ConfirmationToken token){
        confirmationTokenRepository.save(token);
    }
}
