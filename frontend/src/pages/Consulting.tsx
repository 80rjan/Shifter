import { Mail, MapPin } from "lucide-react";
import { useState } from "react";
import { useAuthContext } from "../context/AuthContext.tsx";
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import Arrow from "../../public/Shifter-Arrow-White.png";
import { sendEmailApi } from "../api/contactApi.ts";
import { useTranslation } from "react-i18next";

function Consulting() {
    const { t } = useTranslation("consulting");
    const { user, accessToken } = useAuthContext();
    const [subject, setSubject] = useState("");
    const [message, setMessage] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [isMessageSent, setIsMessageSent] = useState(false);

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();

        setLoading(true);
        sendEmailApi(accessToken || "", "Consulting Inquiry - " + subject, message)
            .then(() => {
                setIsMessageSent(true);
                setError("");
            })
            .catch((err) => {
                setError(t("emailError"));
                console.error("Error sending email for consulting inquiry:", err);
            })
            .finally(() => setLoading(false));
    }

    return (
        <main className="bg-gradient-to-b from-shifter via-shifter/20 via-40% to-beige">
            {/*Hero*/}
            <section className="flex flex-col items-center gap-4 w-full pb-60 pt-top-nav-lg px-horizontal-lg text-white-text">
                <h1 className="text-5xl font-bold">{t("heroTitle")}</h1>
                <p className="text-xl font-light" dangerouslySetInnerHTML={{ __html: t("heroDescription") }} />
            </section>

            {/*Contact Form*/}
            <section className="flex items-center justify-center w-full px-horizontal-lg">
                <div className="relative -top-40 grid grid-cols-3 gap-x-20 rounded-lg bg-white p-4 shadow-md shadow-black/10">
                    {/*Contact Info*/}
                    <div className="border-1 border-white/40 relative overflow-clip col-span-1 flex flex-col gap-8 py-8 px-8 rounded-lg bg-shifter text-white">
                        <div className="flex flex-col gap-2 items-start text-left">
                            <h2 className="text-2xl font-semibold whitespace-nowrap">{t("contactInfoTitle")}</h2>
                            <p className="text-white/80 font-light text-sm">{t("contactInfoDescription")}</p>
                        </div>
                        <div className="flex gap-4 items-center">
                            <Mail size={24} color="var(--color-white)" />
                            {t("contactEmail")}
                        </div>
                        <div className="flex gap-4 items-center">
                            <MapPin size={24} color="var(--color-white)" />
                            {t("contactLocation")}
                        </div>

                        <div className="absolute -bottom-26 right-12 flex flex-col opacity-40 rotate-40">
                            <img src={Arrow} className="w-28 h-auto" alt="Shifter Arrow" />
                            <img src={Arrow} className="w-28 h-auto rotate-180" alt="Shifter Arrow" />
                        </div>
                    </div>

                    {/*Form*/}
                    <div className="flex flex-col gap-4 col-span-2">
                        <div className="grid grid-cols-2 gap-4 items-center">
                            <p className="text-black/40 text-xs col-span-2">{t("autoFilledNote")}</p>
                            <p className="font-light text-black-text/60 text-md whitespace-nowrap">
                                {t("name")}: <span className="text-black-text font-normal">{user?.name}</span>
                            </p>
                            <p className="font-light text-black-text/60 text-md whitespace-nowrap">
                                {t("email")}: <span className="text-black-text font-normal">{user?.email}</span>
                            </p>
                        </div>

                        <hr className="border-t-1 border-black/20 rounded-full" />

                        <form onSubmit={handleSubmit} className="flex flex-col gap-4 items-start">
                            <div className="flex flex-col gap-4 items-center w-full">
                                <TextInput
                                    label={t("subjectLabel")}
                                    name="subject"
                                    placeholder={t("subjectPlaceholder")}
                                    rows={1}
                                    onChange={(e) => setSubject(e.target.value)}
                                />
                                <TextInput
                                    label={t("messageLabel")}
                                    name="message"
                                    placeholder={t("messagePlaceholder")}
                                    rows={8}
                                    onChange={(e) => setMessage(e.target.value)}
                                />
                                {error && <p className="text-red-500 text-md font-medium text-center">{error}</p>}
                            </div>

                            {isMessageSent ? (
                                <div className="text-center w-full">
                                    <h2 className="text-xl font-bold text-shifter mb-2">{t("messageSentTitle")}</h2>
                                    <p className="text-md font-medium text-black/80 max-w-xl mx-auto" dangerouslySetInnerHTML={{ __html: t("messageSentDescription") }} />
                                </div>
                            ) : (
                                <div className="flex items-center gap-6">
                                    <button
                                        className="hover:shadow-shifter/60 transition-all duration-200 ease-in-out disabled:cursor-not-allowed disabled:opacity-60 shadow-md shadow-shifter/40 bg-shifter text-white py-2 px-6 rounded-md cursor-pointer"
                                        disabled={loading}
                                        type="submit"
                                    >
                                        {loading ? t("sending") : t("sendMessage")}
                                    </button>
                                    {loading && <div className="loader" />}
                                </div>
                            )}
                        </form>
                    </div>
                </div>
            </section>
        </main>
    );
}

function TextInput({ label, name, placeholder, rows, onChange }: {
    label: string;
    name: string;
    placeholder: string;
    rows: number;
    onChange: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
}) {
    return (
        <label className="w-full flex flex-col items-start gap-2">
            <span className="text-black/40 font-semibold text-md peer-focused:text-shifter">{label}</span>
            <textarea
                required
                onChange={onChange}
                rows={rows}
                name={name}
                className="peer w-full bg-dark-blue/5 border-1 border-black/10 py-1 px-2 rounded-sm resize-none min-h-fit custom-scrollbar focus:outline-none focus:border-shifter/40 focus:border-2"
                placeholder={placeholder}
            />
        </label>
    );
}

export default Consulting;
