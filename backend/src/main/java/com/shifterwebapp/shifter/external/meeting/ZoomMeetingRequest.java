package com.shifterwebapp.shifter.external.meeting;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ZoomMeetingRequest {

    @JsonProperty("topic")
    private String topic;

    @JsonProperty("start_time")
    private String startTime; // ISO-8601 format: 2025-08-14T15:00:00Z

    @JsonProperty("duration")
    private int duration; // in minutes

    @JsonProperty("type")
    private int type; // 2 = scheduled meeting

    @JsonProperty("timezone")
    private String timezone;

    // Optional
    @JsonProperty("agenda")
    private String agenda;

}

