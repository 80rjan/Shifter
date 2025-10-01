import axios from "axios";

// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
const backendUrl = import.meta.env.VITE_BACKEND_URL;

export const sendEmailApi = async (accessToken: string, subject: string, text: string) => {
    await axios.post(
        `${backendUrl}/api/emails/contact-us`,
        {subject, text},
        {
            headers: {
                Authorization: `Bearer ${accessToken}`
            }
        }
    );
}