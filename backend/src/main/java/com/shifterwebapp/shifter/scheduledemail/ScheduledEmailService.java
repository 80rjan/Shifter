package com.shifterwebapp.shifter.scheduledemail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduledEmailService {

    private final ScheduledEmailRepository scheduledEmailRepository;

    public List<ScheduledEmail> getPendingEmails() {
        return scheduledEmailRepository.findPendingEmails();
    }

    public void saveScheduledEmail(ScheduledEmail email) {
        scheduledEmailRepository.save(email);
    }

}
