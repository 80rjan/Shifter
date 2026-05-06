package com.shifterwebapp.shifter.consultation.application;

import com.shifterwebapp.shifter.consultation.web.request.UserMeetingInfoRequest;

import java.util.List;
import java.util.Map;

public interface MeetingService {

    Map<String, List<String>> getExpertFreeTimeSlots(String userTimeZone);

    void scheduleMeeting(String userDate, String userTime, String userTimeZone, UserMeetingInfoRequest userMeetingInfoRequest);

}
