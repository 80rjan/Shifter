package com.shifterwebapp.shifter.meeting.service;

import com.shifterwebapp.shifter.meeting.UserMeetingInfoRequest;

import java.util.List;
import java.util.Map;

public interface ImplMeetingService {

    Map<String, List<String>> getExpertFreeTimeSlots(String userTimeZone);

    void scheduleMeeting(String userDate, String userTime, String userTimeZone, UserMeetingInfoRequest userMeetingInfoRequest);

}
