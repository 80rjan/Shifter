package com.shifterwebapp.shifter.scheduledemail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MeetingEmailReminderRepository extends JpaRepository<MeetingEmailReminder, Long> {

    @Query("select se from MeetingEmailReminder se where se.sent = false and se.scheduledAt <= CURRENT_TIMESTAMP")
    List<MeetingEmailReminder> findPendingEmails();
}
