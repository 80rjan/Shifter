package com.shifterwebapp.shifter.external.email;

import com.shifterwebapp.shifter.scheduledemail.MeetingEmailReminder;
import com.shifterwebapp.shifter.scheduledemail.MeetingEmailReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class EmailScheduler {

    private final EmailService emailService;
    private final MeetingEmailReminderService meetingEmailReminderService;

    @Transactional
    @Scheduled(timeUnit = TimeUnit.MINUTES, fixedRate = 1) // every minute
    public void sendScheduledEmails() {
        List<MeetingEmailReminder> pendingEmails = meetingEmailReminderService.getPendingEmails();
        for (MeetingEmailReminder email : pendingEmails) {
            emailService.sendFreeConsultationReminder(
                    email.getUser().getEmail(),
                    email.getMeetingAt().toLocalDate().toString(),
                    email.getMeetingAt().toLocalTime().toString(),
                    email.getMeetingLink()
            );
            email.setSent(true);
            meetingEmailReminderService.saveScheduledEmail(email);
        }
    }
}
