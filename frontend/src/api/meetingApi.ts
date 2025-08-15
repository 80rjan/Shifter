import axios from "axios";
import type {UserMeetingInfoRequest} from "../models/UserMeetingInfoRequest.tsx";

// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
const backendUrl = import.meta.env.VITE_BACKEND_URL;

export const fetchExpertFreeTimeSlotsApi = async (accessToken: string): Promise<Record<string, string[]>> => {
    const userTimeZone = encodeURIComponent(Intl.DateTimeFormat().resolvedOptions().timeZone);
    const res = await axios.get(
        `${backendUrl}/api/meetings/free-time-slots?userTimeZone=${userTimeZone}`,
        {
            headers: {
                Authorization: `Bearer ${accessToken}`
            }
        }
    );

    return res.data;
}

export const scheduleMeetingApi = async (accessToken: string, startTime: string, date: string, userMeetingInfoRequest: UserMeetingInfoRequest): Promise<void> => {
    const userTimeZone = encodeURIComponent(Intl.DateTimeFormat().resolvedOptions().timeZone);
    await axios.post(
        `${backendUrl}/api/meetings/schedule-free-consultation?startTime=${startTime}&date=${date}&userTimeZone=${userTimeZone}`,
        userMeetingInfoRequest,
        {
            headers: {
                Authorization: `Bearer ${accessToken}`
            }
        }
    );
}