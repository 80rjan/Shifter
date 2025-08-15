package com.shifterwebapp.shifter.scheduledemail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduledEmailRepository extends JpaRepository<ScheduledEmail, Long> {

    @Query("select se from ScheduledEmail se where se.sent = false and se.scheduledDateTime <= CURRENT_TIMESTAMP")
    List<ScheduledEmail> findPendingEmails();
}
