package com.shifterwebapp.shifter.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shifterwebapp.shifter.enums.LoginProvider;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.userdetails.UserDetails;

@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public abstract class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String name;

    @Column(unique = true, nullable = false)
    protected String email;

    @Enumerated(EnumType.STRING)
    protected LoginProvider loginProvider;

    @Column(nullable = false)
    @JsonIgnore
    protected String passwordHash;    // SHOULD I USE JSON IGNORE HERE? OR IS IT ENOUGH TO NOT EXPOSE IT IN DTO?

}
