package com.shifterwebapp.shifter.external.email;

import com.shifterwebapp.shifter.scheduledemail.ScheduledEmail;
import com.shifterwebapp.shifter.scheduledemail.ScheduledEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class EmailScheduler {

    private final EmailService emailService;
    private final ScheduledEmailService scheduledEmailService;

    @Scheduled(timeUnit = TimeUnit.MINUTES, fixedRate = 1) // every minute
    public void sendScheduledEmails() {
        List<ScheduledEmail> pendingEmails = scheduledEmailService.getPendingEmails();
        for (ScheduledEmail email : pendingEmails) {
            emailService.sendFreeConsultationReminder(
                    email.getRecipientEmail(),
                    email.getMeetingDateTime().toLocalDate().toString(),
                    email.getMeetingDateTime().toLocalTime().toString(),
                    email.getZoomLink()
            );
            email.setSent(true);
            scheduledEmailService.saveScheduledEmail(email);
        }
    }
}
