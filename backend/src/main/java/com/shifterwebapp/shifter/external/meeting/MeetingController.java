package com.shifterwebapp.shifter.external.meeting;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.external.meeting.service.MeetingService;
import com.shifterwebapp.shifter.account.user.User;
import com.shifterwebapp.shifter.account.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.base.path}/meetings")
public class MeetingController {

    private final UserService userService;
    private final MeetingService meetingService;
    private final Validate validate;

    @GetMapping("/free-time-slots")
    public ResponseEntity<?> getExpertFreeTimeSlots(
            @RequestParam String userTimeZone,
            Authentication authentication
    ) {
        validate.validateUserIsAuthenticated(authentication);

        Map<String, List<String>> freeSlots = meetingService.getExpertFreeTimeSlots(userTimeZone);
        return ResponseEntity.ok(freeSlots);
    }

    @PostMapping("/schedule-free-consultation")
    public ResponseEntity<?> scheduleMeeting (
            @RequestBody UserMeetingInfoRequest userMeetingInfoRequest,
            @RequestParam String startTime,
            @RequestParam String userTimeZone,
            @RequestParam String date,
            Authentication authentication
    ) {
        Long userId = validate.extractUserId(authentication);
        User user = userService.getUserEntityById(userId);
        userMeetingInfoRequest.setEmail(user.getEmail());
        userMeetingInfoRequest.setName(user.getName());
        userMeetingInfoRequest.setCompanySize(user.getCompanySize());
        userMeetingInfoRequest.setWorkPosition(user.getWorkPosition());

        meetingService.scheduleMeeting(date, startTime, userTimeZone, userMeetingInfoRequest);


        return ResponseEntity.ok("Meeting successfully arranged!");
    }
}
