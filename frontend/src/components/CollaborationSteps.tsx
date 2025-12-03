import { useTranslation } from "react-i18next";
import {useUserContext} from "../context/UserContext.tsx";
import {LocalizedLink} from "./links/LocalizedLink.tsx";

export default function CollaborationSteps() {
    const { user } = useUserContext();
    const { t } = useTranslation("home");

    const steps = [
        {
            number: '01',
            title: user?.hasUsedFreeConsultation
                ? t("collaborationSteps.step1.afterConsultation.title")
                : t("collaborationSteps.step1.beforeConsultation.title"),
            description: user?.hasUsedFreeConsultation
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
        <section className="relative bg-shifter text-white py-vertical-xl px-horizontal-md flex flex-col gap-between-md items-center">
            {/* Gradients */}
            <div className="absolute bottom-0 w-full h-[32%]" style={{
                background: "linear-gradient(to bottom, rgba(248,246,245,0) 0%, rgba(248,246,245,0.1) 20%, rgba(248,246,245,0.9) 80%, rgba(248,246,245,1) 100%)",
                pointerEvents: "none",
            }} />
            <div className="absolute top-0 w-full h-[32%]" style={{
                background: "linear-gradient(to top, rgba(248,246,245,0) 0%, rgba(248,246,245,0.1) 20%, rgba(248,246,245,0.9) 80%, rgba(248,246,245,1) 100%)",
                pointerEvents: "none",
            }} />

            <h2 className="text-5xl font-light z-1" dangerouslySetInnerHTML={{ __html: t("collaborationSteps.heading") }} />

            <div className="relative flex w-full mt-4">
                {/* LINE AND DOTS */}
                <div className="absolute w-[104%] top-1/2 -translate-y-1/2 left-1/2 -translate-x-1/2 -rotate-16">
                    <hr className="border-t-4 border-white rounded-full w-full absolute top-2" />
                    {[-0.2, 25, 50, 75, 99.8].map((percent, idx) => (
                        <div
                            key={idx}
                            className="absolute w-5 h-5 bg-white rounded-full z-20 border-2 border-black/20 top-0"
                            style={{ left: `${percent}%`, transform: 'translateX(-50%)' }}
                        />
                    ))}
                </div>

                {steps.map((step, i) => (
                    <section
                        key={i}
                        className={`flex flex-col items-center gap-2 justify-between w-1/4 px-4 
                                border-l-2 ${i === 3 ? 'border-r-2' : ''} ${i < 2 ? 'pb-60' : 'pt-60'} border-white/60`}
                    >
                        <strong className="text-4xl font-bold text-white/60">{step.number}</strong>
                        <div className="flex flex-col justify-start h-full gap-2">
                            <div className="flex justify-center items-center min-h-[3.5rem]">
                                <h3 className="text-2xl font-semibold text-center">{step.title}</h3>
                            </div>
                            <p className="font-light text-white/70">{step.description}</p>
                        </div>
                    </section>
                ))}
            </div>

            <LocalizedLink
                to={user?.hasUsedFreeConsultation ? "/contact" : "/free-consultation"}
                className="z-1 hover:shadow-white/60 transition-all duration-200 ease-in-out cursor-pointer
                w-3/10 whitespace-nowrap py-2 bg-white text-xl text-black-text rounded-md font-semibold
                shadow-md shadow-white/40 border-2 border-shifter/10">
                {t("collaborationSteps.button")}
            </LocalizedLink>
        </section>
    );
}
