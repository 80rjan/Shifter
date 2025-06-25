package com.shifterwebapp.shifter.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsAccountByEmail(String email);

    Optional<Account> findByEmail(String email);
}
