import { ArrowBigUpDash, BookOpen, GraduationCap, Handshake } from "lucide-react";
import { useTranslation } from "react-i18next";
import {LocalizedLink} from "./links/LocalizedLink.tsx";

function OurServices() {
    const { t } = useTranslation("home");

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
        <section className="px-horizontal py-vertical">
            <div className="flex flex-col gap-between max-w-screen-2xl mx-auto
                lg:grid lg:grid-cols-3 ">

                {/* TEXT */}
                <div className="flex flex-col gap-2 text-black-text text-left
                    md:gap-4
                    lg:col-span-1">
                    <h2 className="text-2xl font-bold
                        sm:text-3xl
                        md:text-4xl
                        lg:text-5xl
                        xl:text-5xl
                        2xl:text-6xl">
                        {t("ourServices.heading")}
                    </h2>
                    <p className="text-base font-light
                        sm:text-lg
                        md:text-xl
                        lg:text-2xl
                        2xl:text-3xl">
                        {t("ourServices.description")}
                    </p>
                </div>

                {/* CARDS */}
                <div className="grid grid-cols-1 gap-4
                    sm:grid-cols-2 sm:gap-6
                    md:gap-6
                    lg:col-span-2 lg:grid-cols-2 lg:grid-rows-2 lg:gap-4
                    xl:gap-6
                    2xl:gap-8">
                    {cardContent.map((card, index) => {
                        const CardIcon = card.icon;
                        return (
                            <div
                                key={index}
                                className={`shadow-md shadow-black/20
                                ${index === 0 ? "bg-shifter/100 rotate-on-scroll" : "bg-black/10"}
                                ${index === 0 ? "text-white" : "text-black-text"}
                                flex flex-col gap-3 items-start text-left border-2 border-white/40 rounded-2xl p-6
                                md:gap-4 md:p-7
                                lg:p-8
                                xl:p-8
                                2xl:p-10 2xl:gap-5`}
                            >
                                <CardIcon
                                    className="w-8 h-8
                                        sm:w-9 sm:h-9
                                        md:w-10 md:h-10
                                        lg:w-11 lg:h-11
                                        xl:w-11 xl:h-11
                                        2xl:w-12 2xl:h-12"
                                    strokeWidth={1.5}
                                    color={index === 0 ? "var(--color-white)" : "var(--color-black-text)"}
                                />
                                <div className="flex flex-col justify-between h-full gap-3
                                    md:gap-4
                                    2xl:gap-5">
                                    <div className="flex flex-col gap-2">
                                        <h3 className="text-xl font-semibold
                                            sm:text-2xl
                                            md:text-3xl
                                            lg:text-3xl
                                            xl:text-3xl
                                            2xl:text-4xl">
                                            {t(`${card.path}.title`)}
                                        </h3>
                                        <p className="text-base font-light
                                            sm:text-base
                                            md:text-lg
                                            lg:text-xl
                                            xl:text-xl
                                            2xl:text-3xl">
                                            {t(`${card.path}.description`)}
                                        </p>
                                    </div>
                                    <LocalizedLink
                                        to={t(`${card.path}.link`)}
                                        className="font-medium underline hover:opacity-80 transition-opacity duration-200 text-sm
                                            sm:text-sm
                                            md:text-base
                                            lg:text-lg
                                            xl:text-lg
                                            2xl:text-lg">
                                        {t("ourServices.discoverMore")}
                                    </LocalizedLink>
                                </div>
                            </div>
                        );
                    })}
                </div>
            </div>
        </section>
    );
}

export default OurServices;