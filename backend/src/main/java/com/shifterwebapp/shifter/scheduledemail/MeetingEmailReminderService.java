package com.shifterwebapp.shifter.scheduledemail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingEmailReminderService {

    private final MeetingEmailReminderRepository meetingEmailReminderRepository;

    @Transactional(readOnly = true)
    public List<MeetingEmailReminder> getPendingEmails() {
        return meetingEmailReminderRepository.findPendingEmails();
    }

    @Transactional
    public void saveScheduledEmail(MeetingEmailReminder email) {
        meetingEmailReminderRepository.save(email);
    }

}
