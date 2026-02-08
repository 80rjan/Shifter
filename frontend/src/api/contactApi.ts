import axios from "axios";


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