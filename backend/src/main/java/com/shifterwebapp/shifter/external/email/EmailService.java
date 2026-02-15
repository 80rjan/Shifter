package com.shifterwebapp.shifter.external.email;

import com.shifterwebapp.shifter.external.meeting.UserMeetingInfoRequest;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.internet.MimeMessage;
import org.springframework.util.FileCopyUtils;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailUsername;

    private final String shifterEmail = "contact@mail.shift-er.com";
    private final String noreplyEmail = "noreply@shift-er.com";
    private final String expertEmail = "aco@shift-er.com";

    public void contactExpert(String userEmail, ContactReq contactReq) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(noreplyEmail);
        message.setTo(expertEmail);
        message.setReplyTo(userEmail);
        message.setSubject("New Contact Message: " + contactReq.getSubject());
        String body = "From: " + userEmail + "\n\n" + contactReq.getText();
        message.setText(body);
        int maxRetries = 3;
        int attempt = 0;
        while (true) {
            try {
                mailSender.send(message);
                return;
            } catch (Exception e) {
                attempt++;
                if (attempt >= maxRetries) {
                    System.out.println(e.getMessage());
                    throw new RuntimeException("Failed to send email to expert after " + attempt + " attempts", e);
                }
            }
        }
    }

    public void sendCoursePurchaseConfirmation(String to, String courseName, String courseDescription, String accessUrl, String userName) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Welcome to " + courseName + "! Start Learning Now");
            helper.setFrom(noreplyEmail);
            helper.setReplyTo(shifterEmail);

            int currentYear = Year.now().getValue();

            String htmlTemplate;
            try {
                ClassPathResource resource = new ClassPathResource("email-templates/course_purchase_confirmation.html");
                htmlTemplate = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
            } catch (IOException e) {
                // Throw a runtime exception if the template file can't be loaded
                throw new UncheckedIOException("Failed to load email template: course_purchase_confirmation.html", e);
            }

            String htmlContent = htmlTemplate
                    .replace("${courseName}", courseName)
                    .replace("${courseDescription}", courseDescription)
                    .replace("${accessUrl}", accessUrl)
                    .replace("${currentYear}", String.valueOf(currentYear));

            helper.setText(htmlContent, true);

            int maxRetries = 3;
            int attempt = 0;
            while (true) {
                try {
                    mailSender.send(mimeMessage);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    attempt++;
                    if (attempt >= maxRetries) {
                        throw new RuntimeException("Failed to send HTML email to " + to + " after " + attempt + " attempts", e);
                    }
                }
            }

        } catch (MessagingException e) {
            throw new RuntimeException("Error preparing email message for " + to, e);
        }
    }

    public void sendFreeConsultationConfirmation(String to, String date, String time, String zoomLink) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            String subject = "Your Free Consultation Session is Scheduled - " + date + " at " + time;

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(noreplyEmail);
            helper.setReplyTo(shifterEmail);

            String currentYear = String.valueOf(java.time.Year.now().getValue());

            String htmlTemplate;
            try {
                ClassPathResource resource = new ClassPathResource("email-templates/free_consultation_confirmation.html");
                htmlTemplate = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new UncheckedIOException("Failed to load email template: free_consultation_confirmation.html", e);
            }

            String htmlContent = htmlTemplate
                    .replace("${date}", date)
                    .replace("${time}", time)
                    .replace("${zoomLink}", zoomLink)
                    .replace("${currentYear}", currentYear);

            helper.setText(htmlContent, true);

            int maxRetries = 3;
            int attempt = 0;
            while (true) {
                try {
                    mailSender.send(mimeMessage);
                    return;
                } catch (Exception e) {
                    attempt++;
                    if (attempt >= maxRetries) {
                        throw new RuntimeException("Failed to send HTML email to " + to + " after " + attempt + " attempts", e);
                    }
                }
            }

        } catch (MessagingException e) {
            throw new RuntimeException("Error preparing email message for " + to, e);
        }
    }

    public void sendFreeConsultationReminder(String to, String meetingDate, String meetingTime, String zoomLink) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE; // e.g., "2025-10-01"

        LocalDate today = LocalDate.now();
        LocalDate meetingLocalDate = LocalDate.parse(meetingDate, dateFormatter);

        String subject;
        String reminderHeading;
        String reminderText;

        if (meetingLocalDate.isEqual(today.plusDays(1))) {
            subject = "Reminder: Tomorrow is your Free Consultation Session at " + meetingTime;
            reminderHeading = "Your Session is Tomorrow!";
            reminderText = "This is a friendly reminder that your free consultation session is scheduled for tomorrow. We look forward to meeting with you and helping you plan your next steps!";
        }
        else if (meetingLocalDate.isEqual(today)) {
            subject = "Reminder: Free Consultation Session in 2 hours";
            reminderHeading = "Your Session is in 2 Hours!";
            reminderText = "This is a crucial final reminder. Your free consultation session is starting soon. Please use the link below to join promptly!";
        }
        else {
            System.out.println("Scheduler error: Reminder function called outside of expected time window for meeting on: " + meetingDate);
            return;
        }

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(noreplyEmail);
            helper.setReplyTo(shifterEmail);

            String currentYear = String.valueOf(Year.now().getValue());

            String htmlTemplate;
            try {
                ClassPathResource resource = new ClassPathResource("email-templates/consultation_reminder.html");
                htmlTemplate = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new UncheckedIOException("Failed to load email template: consultation_reminder.html", e);
            }

            String htmlContent = htmlTemplate
                    .replace("${subject}", subject)
                    .replace("${meetingDate}", meetingDate)
                    .replace("${meetingTime}", meetingTime)
                    .replace("${zoomLink}", zoomLink)
                    .replace("${reminderHeading}", reminderHeading)
                    .replace("${reminderText}", reminderText)
                    .replace("${currentYear}", currentYear);

            helper.setText(htmlContent, true);

            int maxRetries = 3;
            int attempt = 0;
            while (true) {
                try {
                    mailSender.send(mimeMessage);
                    return;
                } catch (Exception e) {
                    attempt++;
                    if (attempt >= maxRetries) {
                        throw new RuntimeException("Failed to send HTML reminder email to " + to + " after " + attempt + " attempts", e);
                    }
                }
            }

        } catch (MessagingException e) {
            throw new RuntimeException("Error preparing email message for " + to, e);
        }
    }

    public void sendExpertMeetingInformation(UserMeetingInfoRequest userMeetingInfoRequest, String time, String date, String userTimeZone, String zoomLink) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        String subject = "You Have an Upcoming Free Consultation Session - " + date + " at " + time;

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(expertEmail); // Send to the expert's email
            helper.setSubject(subject);
            helper.setFrom(noreplyEmail);

            String currentYear = String.valueOf(Year.now().getValue());

            String htmlTemplate;
            try {
                ClassPathResource resource = new ClassPathResource("email-templates/expert_meeting_info.html");
                htmlTemplate = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new UncheckedIOException("Failed to load email template: expert_meeting_info.html", e);
            }

            String htmlContent = htmlTemplate
                    .replace("${subject}", subject)
                    .replace("${date}", date)
                    .replace("${time}", time)
                    .replace("${zoomLink}", zoomLink)
                    .replace("${name}", userMeetingInfoRequest.getName())
                    .replace("${email}", userMeetingInfoRequest.getEmail())
                    .replace("${companyType}", userMeetingInfoRequest.getCompanySize() != null ? userMeetingInfoRequest.getCompanySize().toString() : "N/A")
                    .replace("${workPosition}", userMeetingInfoRequest.getWorkPosition())
                    .replace("${userTimeZone}", userTimeZone)
                    // Using ternary operators directly for "N/A" fallback
                    .replace("${basicInfo}", userMeetingInfoRequest.getBasicInfo() != null ? userMeetingInfoRequest.getBasicInfo() : "N/A")
                    .replace("${aboutCompany}", userMeetingInfoRequest.getAboutCompany() != null ? userMeetingInfoRequest.getAboutCompany() : "N/A")
                    .replace("${challenges}", userMeetingInfoRequest.getChallenges() != null ? userMeetingInfoRequest.getChallenges() : "N/A")
                    .replace("${otherInfo}", userMeetingInfoRequest.getOtherInfo() != null ? userMeetingInfoRequest.getOtherInfo() : "N/A")
                    .replace("${currentYear}", currentYear);


            helper.setText(htmlContent, true);

            int maxRetries = 3;
            int attempt = 0;
            while (true) {
                try {
                    mailSender.send(mimeMessage);
                    return;
                } catch (Exception e) {
                    attempt++;
                    if (attempt >= maxRetries) {
                        throw new RuntimeException("Failed to send expert email with meeting info after " + attempt + " attempts", e);
                    }
                }
            }

        } catch (MessagingException e) {
            throw new RuntimeException("Error preparing email message for expert", e);
        }
    }

    public void sendVerificationToken(String to, String verificationUrl) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Your Shifter Account");
            helper.setFrom(noreplyEmail);
            helper.setReplyTo(shifterEmail);

            String htmlTemplate;
            try {
                ClassPathResource resource = new ClassPathResource("email-templates/verify_account.html");
                htmlTemplate = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
            } catch (IOException e) {
                // Throw a runtime exception if the template file can't be loaded
                throw new UncheckedIOException("Failed to load email template: verify_account.html", e);
            }

            String htmlContent = htmlTemplate
                    .replace("${verificationUrl}", verificationUrl)
                    .replace("${currentYear}", String.valueOf(Year.now().getValue()));

            helper.setText(htmlContent, true);

            int maxRetries = 3;
            int attempt = 0;
            while (true) {
                try {
                    mailSender.send(mimeMessage);
                    return;
                } catch (Exception e) {
                    attempt++;
                    if (attempt >= maxRetries) {
                        throw new RuntimeException("Failed to send HTML email to " + to + " after " + attempt + " attempts", e);
                    }
                }
            }

        } catch (MessagingException e) {
            throw new RuntimeException("Error preparing email message for " + to, e);
        }
    }
}
