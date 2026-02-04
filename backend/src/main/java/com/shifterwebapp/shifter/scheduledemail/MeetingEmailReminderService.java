package com.shifterwebapp.shifter.scheduledemail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingEmailReminderService {

    private final MeetingEmailReminderRepository meetingEmailReminderRepository;

    public List<MeetingEmailReminder> getPendingEmails() {
        return meetingEmailReminderRepository.findPendingEmails();
    }

    public void saveScheduledEmail(MeetingEmailReminder email) {
        meetingEmailReminderRepository.save(email);
    }

}
