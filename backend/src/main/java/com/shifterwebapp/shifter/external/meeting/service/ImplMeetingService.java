package com.shifterwebapp.shifter.external.meeting.service;

import com.shifterwebapp.shifter.external.meeting.UserMeetingInfoRequest;

import java.util.List;
import java.util.Map;

public interface ImplMeetingService {

    Map<String, List<String>> getExpertFreeTimeSlots(String userTimeZone);

    void scheduleMeeting(String userDate, String userTime, String userTimeZone, UserMeetingInfoRequest userMeetingInfoRequest);

}
