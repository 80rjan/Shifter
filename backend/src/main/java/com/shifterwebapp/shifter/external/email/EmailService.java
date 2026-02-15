package com.shifterwebapp.shifter.external.email;

import com.shifterwebapp.shifter.external.meeting.UserMeetingInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${zeptomail.api.url}")
    private String zeptomailApiUrl;

    @Value("${zeptomail.api.key}")
    private String zeptomailApiKey;

    private final String contactEmail = "contact@mail.shift-er.com";
    private final String noreplyEmail = "noreply@shift-er.com";
    private final String expertEmail = "aco@shift-er.com";

    /**
     * Helper method to send email via ZeptoMail HTTP API
     */
    private void sendEmail(String fromAddress, String to, String subject, String htmlContent, String replyTo) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Zoho-enczapikey " + zeptomailApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Build ZeptoMail API request body
        Map<String, Object> from = Map.of("address", fromAddress);
        Map<String, Object> toAddress = Map.of("email_address", Map.of("address", to));

        Map<String, Object> emailBody;
        if (replyTo != null && !replyTo.isEmpty()) {
            emailBody = Map.of(
                    "from", from,
                    "to", List.of(toAddress),
                    "subject", subject,
                    "htmlbody", htmlContent,
                    "reply_to", List.of(Map.of("address", replyTo))
            );
        } else {
            emailBody = Map.of(
                    "from", from,
                    "to", List.of(toAddress),
                    "subject", subject,
                    "htmlbody", htmlContent
            );
        }

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(emailBody, headers);

        // Send with retries
        int maxRetries = 3;
        int attempt = 0;

        while (true) {
            try {
                ResponseEntity<String> response = restTemplate.postForEntity(
                        zeptomailApiUrl,
                        request,
                        String.class
                );

                if (response.getStatusCode().is2xxSuccessful()) {
                    return; // Success
                }

                throw new RuntimeException("ZeptoMail API returned status: " + response.getStatusCode());

            } catch (Exception e) {
                attempt++;
                if (attempt >= maxRetries) {
                    throw new RuntimeException("Failed to send email to " + to + " after " + attempt + " attempts", e);
                }

                // Wait a bit before retry
                try {
                    Thread.sleep(1000 * attempt); // Exponential backoff
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrupted during email retry", ie);
                }
            }
        }
    }

    /**
     * Helper method to load HTML email template
     */
    private String loadTemplate(String templateName) {
        try {
            ClassPathResource resource = new ClassPathResource("email-templates/" + templateName);
            return FileCopyUtils.copyToString(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)
            );
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to load email template: " + templateName, e);
        }
    }

    public void contactExpert(String userEmail, ContactReq contactReq) {
        String subject = "New Contact Message: " + contactReq.getSubject();
        String textBody = "From: " + userEmail + "\n\n" + contactReq.getText();

        // Convert text to simple HTML
        String htmlContent = "<html><body><pre>" + textBody + "</pre></body></html>";

        sendEmail(noreplyEmail, expertEmail, subject, htmlContent, userEmail);
    }

    public void sendCoursePurchaseConfirmation(
            String to,
            String courseName,
            String courseDescription,
            String accessUrl,
            String userName) {

        String subject = "Welcome to " + courseName + "! Start Learning Now";
        int currentYear = Year.now().getValue();

        String htmlTemplate = loadTemplate("course_purchase_confirmation.html");
        String htmlContent = htmlTemplate
                .replace("${courseName}", courseName)
                .replace("${courseDescription}", courseDescription)
                .replace("${accessUrl}", accessUrl)
                .replace("${currentYear}", String.valueOf(currentYear));

        sendEmail(noreplyEmail, to, subject, htmlContent, contactEmail);
    }

    public void sendFreeConsultationConfirmation(
            String to,
            String date,
            String time,
            String zoomLink) {

        String subject = "Your Free Consultation Session is Scheduled - " + date + " at " + time;
        String currentYear = String.valueOf(Year.now().getValue());

        String htmlTemplate = loadTemplate("free_consultation_confirmation.html");
        String htmlContent = htmlTemplate
                .replace("${date}", date)
                .replace("${time}", time)
                .replace("${zoomLink}", zoomLink)
                .replace("${currentYear}", currentYear);

        sendEmail(noreplyEmail, to, subject, htmlContent, contactEmail);
    }

    public void sendFreeConsultationReminder(
            String to,
            String meetingDate,
            String meetingTime,
            String zoomLink) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate today = LocalDate.now();
        LocalDate meetingLocalDate = LocalDate.parse(meetingDate, dateFormatter);

        String subject;
        String reminderHeading;
        String reminderText;

        if (meetingLocalDate.isEqual(today.plusDays(1))) {
            subject = "Reminder: Tomorrow is your Free Consultation Session at " + meetingTime;
            reminderHeading = "Your Session is Tomorrow!";
            reminderText = "This is a friendly reminder that your free consultation session is scheduled for tomorrow. We look forward to meeting with you and helping you plan your next steps!";
        } else if (meetingLocalDate.isEqual(today)) {
            subject = "Reminder: Free Consultation Session in 2 hours";
            reminderHeading = "Your Session is in 2 Hours!";
            reminderText = "This is a crucial final reminder. Your free consultation session is starting soon. Please use the link below to join promptly!";
        } else {
            System.out.println("Scheduler error: Reminder function called outside of expected time window for meeting on: " + meetingDate);
            return;
        }

        String currentYear = String.valueOf(Year.now().getValue());
        String htmlTemplate = loadTemplate("consultation_reminder.html");
        String htmlContent = htmlTemplate
                .replace("${subject}", subject)
                .replace("${meetingDate}", meetingDate)
                .replace("${meetingTime}", meetingTime)
                .replace("${zoomLink}", zoomLink)
                .replace("${reminderHeading}", reminderHeading)
                .replace("${reminderText}", reminderText)
                .replace("${currentYear}", currentYear);

        sendEmail(noreplyEmail, to, subject, htmlContent, contactEmail);
    }

    public void sendExpertMeetingInformation(
            UserMeetingInfoRequest userMeetingInfoRequest,
            String time,
            String date,
            String userTimeZone,
            String zoomLink) {

        String subject = "You Have an Upcoming Free Consultation Session - " + date + " at " + time;
        String currentYear = String.valueOf(Year.now().getValue());

        String htmlTemplate = loadTemplate("expert_meeting_info.html");
        String htmlContent = htmlTemplate
                .replace("${subject}", subject)
                .replace("${date}", date)
                .replace("${time}", time)
                .replace("${zoomLink}", zoomLink)
                .replace("${name}", userMeetingInfoRequest.getName())
                .replace("${email}", userMeetingInfoRequest.getEmail())
                .replace("${companyType}", userMeetingInfoRequest.getCompanySize() != null ?
                        userMeetingInfoRequest.getCompanySize().toString() : "N/A")
                .replace("${workPosition}", userMeetingInfoRequest.getWorkPosition())
                .replace("${userTimeZone}", userTimeZone)
                .replace("${basicInfo}", userMeetingInfoRequest.getBasicInfo() != null ?
                        userMeetingInfoRequest.getBasicInfo() : "N/A")
                .replace("${aboutCompany}", userMeetingInfoRequest.getAboutCompany() != null ?
                        userMeetingInfoRequest.getAboutCompany() : "N/A")
                .replace("${challenges}", userMeetingInfoRequest.getChallenges() != null ?
                        userMeetingInfoRequest.getChallenges() : "N/A")
                .replace("${otherInfo}", userMeetingInfoRequest.getOtherInfo() != null ?
                        userMeetingInfoRequest.getOtherInfo() : "N/A")
                .replace("${currentYear}", currentYear);

        sendEmail(noreplyEmail, expertEmail, subject, htmlContent, null);
    }

    public void sendVerificationToken(String to, String verificationUrl) {
        String subject = "Your Shifter Account";
        String currentYear = String.valueOf(Year.now().getValue());

        String htmlTemplate = loadTemplate("verify_account.html");
        String htmlContent = htmlTemplate
                .replace("${verificationUrl}", verificationUrl)
                .replace("${currentYear}", currentYear);

        sendEmail(noreplyEmail, to, subject, htmlContent, contactEmail);
    }
}