import { useTranslation } from "react-i18next";
import {useUserContext} from "../context/UserContext.tsx";
import {LocalizedLink} from "./links/LocalizedLink.tsx";

export default function CollaborationSteps() {
    const { user } = useUserContext();
    const { t } = useTranslation("home");

    const steps = [
        {
            number: '01',
            title: user?.usedFreeConsultation
                ? t("collaborationSteps.step1.afterConsultation.title")
                : t("collaborationSteps.step1.beforeConsultation.title"),
            description: user?.usedFreeConsultation
                ? t("collaborationSteps.step1.afterConsultation.description")
                : t("collaborationSteps.step1.beforeConsultation.description"),
        },
        {
            number: '02',
            title: t("collaborationSteps.step2.title"),
            description: t("collaborationSteps.step2.description"),
        },
        {
            number: '03',
            title: t("collaborationSteps.step3.title"),
            description: t("collaborationSteps.step3.description"),
        },
        {
            number: '04',
            title: t("collaborationSteps.step4.title"),
            description: t("collaborationSteps.step4.description"),
        },
    ];

    return (
        <section className="relative bg-shifter text-white py-vertical px-horizontal">
            <div className="max-w-screen-2xl mx-auto flex flex-col gap-between items-center">

                {/* Gradients */}
                <div className="absolute top-0 w-full h-[30%] sm:h-[25%]" style={{
                    background: "linear-gradient(to top, rgba(250,251,252,0) 0%, rgba(250,251,252,0.03) 15%, rgba(250,251,252,0.08) 30%, rgba(250,251,252,0.2) 50%, rgba(250,251,252,0.5) 70%, rgba(250,251,252,0.8) 85%, rgba(250,251,252,1) 100%)",
                    pointerEvents: "none",
                }} />
                <div className="absolute bottom-0 w-full h-[30%] sm:h-[25%]" style={{
                    background: "linear-gradient(to bottom, rgba(250,251,252,0) 0%, rgba(250,251,252,0.03) 15%, rgba(250,251,252,0.08) 30%, rgba(250,251,252,0.2) 50%, rgba(250,251,252,0.5) 70%, rgba(250,251,252,0.8) 85%, rgba(250,251,252,1) 100%)",
                    pointerEvents: "none",
                }} />

                {/* Heading */}
                <h2 className="text-2xl font-light z-1 text-center
                    sm:text-3xl
                    md:text-4xl
                    lg:text-5xl
                    xl:text-5xl
                    2xl:text-6xl"
                    dangerouslySetInnerHTML={{ __html: t("collaborationSteps.heading") }} />

                {/* Steps - Mobile: Vertical, Desktop: Horizontal with line */}
                <div className="relative w-full z-1">
                    {/* MOBILE & TABLET: Vertical Steps */}
                    <div className="flex flex-col gap-6 lg:hidden
                        sm:gap-8
                        md:gap-10">
                        {steps.map((step, i) => (
                            <div
                                key={i}
                                className="flex flex-col gap-2 border-l-4 border-white/60 pl-6
                                    sm:pl-8
                                    md:pl-10"
                            >
                                <strong className="text-2xl font-bold text-white/60
                                    sm:text-3xl
                                    md:text-4xl">
                                    {step.number}
                                </strong>
                                <div className="flex flex-col gap-1">
                                    <h3 className="text-xl font-semibold
                                        sm:text-2xl
                                        md:text-3xl">
                                        {step.title}
                                    </h3>
                                    <p className="text-base text-white/70
                                        sm:text-lg
                                        md:text-xl">
                                        {step.description}
                                    </p>
                                </div>
                            </div>
                        ))}
                    </div>

                    {/* DESKTOP: Horizontal Steps with Line */}
                    <div className="hidden lg:flex relative w-full">
                        {/* LINE AND DOTS */}
                        <div className="absolute w-[104%] top-1/2 -translate-y-1/2 left-1/2 -translate-x-1/2 -rotate-16">
                            <hr className="border-t-4 border-white rounded-full absolute top-2 horizonal-line-show" />
                            {[-0.2, 25, 50, 75, 99.8].map((percent, idx) => (
                                <div
                                    key={idx}
                                    className="absolute w-5 h-5 bg-white rounded-full z-20 border-2 border-black/20 top-0
                                        xl:w-6 xl:h-6"
                                    style={{ left: `${percent}%`, transform: 'translateX(-50%)' }}
                                />
                            ))}
                        </div>

                        {steps.map((step, i) => (
                            <div
                                key={i}
                                className={`flex flex-col items-center gap-2 justify-between w-1/4 px-2
                                    border-l-2 ${i === 3 ? 'border-r-2' : ''} ${i < 2 ? 'pb-40' : 'pt-40'} border-white/60
                                    xl:px-4 xl:gap-3 ${i < 2 ? 'xl:pb-60' : 'xl:pt-60'}
                                    2xl:px-6`}
                            >
                                <strong className="text-3xl font-bold text-white/60
                                    xl:text-4xl
                                    2xl:text-5xl">
                                    {step.number}
                                </strong>
                                <div className="flex flex-col justify-start h-full gap-2">
                                    <div className="flex justify-center items-center min-h-[3rem]
                                        xl:min-h-[3.5rem]">
                                        <h3 className="text-lg font-semibold text-center
                                            xl:text-2xl
                                            2xl:text-3xl">
                                            {step.title}
                                        </h3>
                                    </div>
                                    <p className="text-sm text-white/70 text-center
                                        xl:text-base
                                        2xl:text-lg">
                                        {step.description}
                                    </p>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>

                {/* CTA Button */}
                <LocalizedLink
                    to={user?.usedFreeConsultation ? "/contact" : "/free-consultation"}
                    className="z-1 hover:shadow-white/60 transition-all duration-200 ease-in-out cursor-pointer
                        w-full whitespace-nowrap py-2 bg-white text-base text-black-text rounded-md font-semibold
                        shadow-md shadow-white/40 border-2 border-shifter/10
                        sm:w-4/5 sm:text-lg
                        md:w-3/5 md:py-3 md:text-xl
                        lg:w-2/5
                        xl:w-3/10 xl:text-xl
                        2xl:text-2xl">
                    {t("collaborationSteps.button")}
                </LocalizedLink>
            </div>
        </section>
    );
}