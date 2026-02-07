import { ArrowBigUpDash, BookOpen, GraduationCap, Handshake } from "lucide-react";
import { useTranslation } from "react-i18next";
import {LocalizedLink} from "./links/LocalizedLink.tsx";

function OurServices() {
    const { t } = useTranslation("home"); // assuming this JSON is in home.json

    const cardContent = [
        {
            icon: Handshake,
            path: "ourServices.cards.0"
        },
        {
            icon: BookOpen,
            path: "ourServices.cards.1"
        },
        {
            icon: ArrowBigUpDash,
            path: "ourServices.cards.2"
        },
        {
            icon: GraduationCap,
            path: "ourServices.cards.3"
        }
    ];

    return (
        <section className="grid grid-cols-3 gap-20 justify-between py-vertical pt-nav-top px-horizontal">

            {/* TEXT */}
            <div className="col-span-1 col-start-1 flex flex-col gap-6 text-black-text text-left w-full">
                <h2 className="text-5xl font-bold">{t("ourServices.heading")}</h2>
                <p className="text-xl font-light opacity-80">{t("ourServices.description")}</p>
            </div>

            {/* CARDS */}
            <div className="col-span-2 col-start-2 grid grid-cols-2 grid-rows-2 gap-8">
                {cardContent.map((card, index) => {
                    const CardIcon = card.icon;
                    return (
                        <div
                            key={index}
                            className={`shadow-md shadow-black/20
                            ${index === 0 ? "bg-shifter/100 rotate-on-scroll" : "bg-black/10"}
                            ${index === 0 ? "text-white" : "text-black-text"}
                            flex flex-col gap-4 items-start text-left border-2 border-white/40 rounded-2xl p-8`}
                        >
                            <CardIcon
                                size={44}
                                strokeWidth={1.5}
                                color={index === 0 ? "var(--color-white)" : "var(--color-black-text)"}
                            />
                            <div className="flex flex-col justify-between h-full gap-4">
                                <div className="flex flex-col gap-2">
                                    <h2 className="text-2xl font-bold">{t(`${card.path}.title`)}</h2>
                                    <p className="text-lg font-light">{t(`${card.path}.description`)}</p>
                                </div>
                                <LocalizedLink to={t(`${card.path}.link`)}
                                className={`font-medium underline hover:opacity-80 transition-opacity duration-200`}>
                                    {t("ourServices.discoverMore")}
                                </LocalizedLink>
                            </div>
                        </div>
                    );
                })}
            </div>
        </section>
    );
}

export default OurServices;
