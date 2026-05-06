package com.shifterwebapp.shifter.identity.infrastructure;

import com.shifterwebapp.shifter.identity.domain.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ExpertRepository extends JpaRepository<Expert, Long> {
    boolean existsByEmail(String email);

    @Query("select e from Expert e where e.email = :email")
    Optional<Expert> findByEmail(@Param("email") String email);
}
