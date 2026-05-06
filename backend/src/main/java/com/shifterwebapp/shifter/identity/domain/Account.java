package com.shifterwebapp.shifter.identity.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shifterwebapp.shifter.identity.domain.enums.LoginProvider;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long id;

    protected String name;

    @NotNull(message = "Account email cannot be null")
    @Column(nullable = false) // will make unique with index
    protected String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected LoginProvider loginProvider;

    @NotNull(message = "Account password hash cannot be null")
    @Column(nullable = false)
    @JsonIgnore
    protected String passwordHash;    // SHOULD I USE JSON IGNORE HERE? OR IS IT ENOUGH TO NOT EXPOSE IT IN DTO?

}
