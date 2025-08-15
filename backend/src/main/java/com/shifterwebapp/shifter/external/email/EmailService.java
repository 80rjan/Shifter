package com.shifterwebapp.shifter.external.email;

import com.shifterwebapp.shifter.meeting.UserMeetingInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendFreeConsultationConfirmation(String to, String date, String time, String zoomLink) {
        String subject = "Your Free Consultation Session is Scheduled - " + date + " at " + time;
        String text = """
                Hello,
                
                Your free consultation session has been successfully scheduled! 🎉
                
                📅 Date: {date} \s
                ⏰ Time: {time} \s
                📍 Location: Online - Zoom \s
                🔗 Meeting Link: {zoomLink} \s
                
                This session is designed to understand your current challenges, goals, and preferences.
                During this session, our expert will provide valuable insights based on your situation.
                After the session, you will receive a personalized program recommendation tailored to your needs.
                
                If you have any questions or need to reschedule, please reply to this email.
                
                Excited to help you take the next step! \s
                
                Best regards, \s
                The Shifter Team
                """;
        text = text
                .replace("{date}", date)
                .replace("{time}", time)
                .replace("{zoomLink}", zoomLink);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        int maxRetries = 3;
        int attempt = 0;
        while (true) {
            try {
                mailSender.send(message);
                return;
            } catch (Exception e) {
                attempt++;
                if (attempt >= maxRetries) {
                    throw new RuntimeException("Failed to send confirmation email after " + attempt + " attempts", e);
                }
            }
        }
    }

    public void sendFreeConsultationReminder(String to, String meetingDate, String meetingTime, String zoomLink) {
        LocalDate today = LocalDate.now();

        String subject;
        if (LocalDate.parse(meetingDate).isBefore(today))
            subject = "Reminder: Tomorrow is your Free Consultation Session at " + meetingTime;
        else
            subject = "Reminder: Free Consultation Session in 2 hours";

        String text = """
                Hello,
                
                This is a friendly reminder for your upcoming free consultation session! ⏰
                
                📅 Date: {meetingDate} \s
                ⏰ Time: {meetingTime} \s
                📍 Location: Online - Zoom \s
                🔗 Meeting Link: {zoomLink} \s
                
                This session is designed to understand your current challenges, goals, and preferences.
                During this session, our expert will provide valuable insights based on your situation.
                After the session, you will receive a personalized program recommendation tailored to your needs.
                
                If you have any questions or need to reschedule, please reply to this email.
                
                Excited to help you take the next step! \s
                
                Best regards, \s
                The Shifter Team
                """;
        text = text
                .replace("{meetingDate}", meetingDate)
                .replace("{meetingTime}", meetingTime)
                .replace("{zoomLink}", zoomLink);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        int maxRetries = 3;
        int attempt = 0;
        while (true) {
            try {
                mailSender.send(message);
                return;
            } catch (Exception e) {
                attempt++;
                if (attempt >= maxRetries) {
                    throw new RuntimeException("Failed to send reminder email after " + attempt + " attempts", e);
                }
            }
        }
    }

    public void sendExpertMeetingInformation(UserMeetingInfoRequest userMeetingInfoRequest, String time, String date, String userTimeZone, String zoomLink) {
        String subject = "You Have an Upcoming Free Consultation Session - " + date + " at " + time;

        String text = """
        Hello,

        A new user has booked a free consultation session. Here are their details and the meeting information:

        📅 Date: {date}
        ⏰ Time: {time}
        🔗 Zoom Meeting Link: {zoomLink}

        --- User Information ---

        Name: {name}
        Email: {email}
        Company Type: {companyType}
        Work Position: {workPosition}
        Time Zone: {userTimeZone}

        About the Company:
        {aboutCompany}

        Current Challenges:
        {challenges}

        Expectations from the Session:
        {expectations}

        Additional Information:
        {otherInfo}

        Please review this information before the session to provide the best personalized guidance.

        Best regards,
        The Shifter Team
        """;

        text = text
                .replace("{date}", date)
                .replace("{time}", time)
                .replace("{zoomLink}", zoomLink)
                .replace("{name}", userMeetingInfoRequest.getName())
                .replace("{email}", userMeetingInfoRequest.getEmail())
                .replace("{companyType}", userMeetingInfoRequest.getCompanyType().toString())
                .replace("{workPosition}", userMeetingInfoRequest.getWorkPosition())
                .replace("{userTimeZone}", userTimeZone)
                .replace("{aboutCompany}", userMeetingInfoRequest.getAboutCompany() != null ? userMeetingInfoRequest.getAboutCompany() : "N/A")
                .replace("{challenges}", userMeetingInfoRequest.getChallenges() != null ? userMeetingInfoRequest.getChallenges() : "N/A")
                .replace("{expectations}", userMeetingInfoRequest.getExpectations() != null ? userMeetingInfoRequest.getExpectations() : "N/A")
                .replace("{otherInfo}", userMeetingInfoRequest.getOtherInfo() != null ? userMeetingInfoRequest.getOtherInfo() : "N/A");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(System.getProperty("EMAIL_USERNAME"));
        message.setSubject(subject);
        message.setText(text);
        int maxRetries = 3;
        int attempt = 0;
        while (true) {
            try {
                mailSender.send(message);
                return;
            } catch (Exception e) {
                attempt++;
                if (attempt >= maxRetries) {
                    throw new RuntimeException("Failed to send expert email with meeting info after " + attempt + " attempts", e);
                }
            }
        }
    }
}
