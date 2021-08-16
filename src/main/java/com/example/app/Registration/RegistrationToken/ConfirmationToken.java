package com.example.app.Registration.RegistrationToken;

import com.example.app.appUser.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {

    @Id
    @SequenceGenerator(name = "confirmation_token_sequence", sequenceName = "confirmation_token_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "confirmation_token_sequence")
    private long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdLocalDateTime;

    @Column(nullable = false)
    private LocalDateTime expiredLocalDateTime;

    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(nullable = false, name = "app_user_id")
    private AppUser appUser;


    public ConfirmationToken(String token, LocalDateTime createdLocalDateTime, LocalDateTime expiredLocalDateTime,AppUser appUser) {
        this.token = token;
        this.createdLocalDateTime = createdLocalDateTime;
        this.expiredLocalDateTime = expiredLocalDateTime;
        this.appUser = appUser;
    }

}
