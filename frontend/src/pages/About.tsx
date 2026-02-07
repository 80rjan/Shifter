import {
    IconTrendingUp,
    IconRotate360,
    IconRoute, IconChessKnight, IconArrowBigUp
} from '@tabler/icons-react';
import {LightBeams} from "../assets/animations/SpikeAnimation.tsx"
import CountUp from "react-countup";
import MagicBento from "../assets/animations/MagicBento.tsx";
import {useTranslation} from "react-i18next";
import OurValues from "../components/OurValues.tsx";
import AcoSection from "../components/AcoSection.tsx";

const About = () => {
    const {t} = useTranslation("about");

    const reasons = [
        {
            icon: <IconChessKnight size={72} color="var(--color-shifter)" aria-hidden={true} focusable={false}/>,
            title: t("reasons.0.title"),
            description: t("reasons.0.description"),
        },
        {
            icon: <IconArrowBigUp size={72} color="var(--color-shifter)" aria-hidden={true} focusable={false}/>,
            title: t("reasons.1.title"),
            description: t("reasons.1.description"),
        },
        {
            icon: <IconRotate360 size={72} color="var(--color-shifter)" aria-hidden={true} focusable={false}/>,
            title: t("reasons.2.title"),
            description: t("reasons.2.description"),
        },
        {
            icon: <IconTrendingUp size={72} color="var(--color-shifter)" aria-hidden={true} focusable={false}/>,
            title: t("reasons.3.title"),
            description: t("reasons.3.description"),
        },
        {
            icon: <IconRoute size={72} color="var(--color-shifter)" aria-hidden={true} focusable={false}/>,
            title: t("reasons.4.title"),
            description: t("reasons.4.description"),
        }
    ];



    return (
        <main className="relative bg-main text-black-text">
            {/*Hero Section*/}
            <section
                className="relative py-vertical pt-nav-top px-horizontal flex flex-col items-center justify-center">
                <LightBeams tilt={-50} beamCount={120} initialHeight={120 + Math.random() * 80} spikeMoveDown={40}/>

                <div className="flex flex-col items-center gap-20 z-1">
                    <div className="flex flex-col  gap-4 max-w-3/4">
                        <h1 className="text-7xl font-semibold" dangerouslySetInnerHTML={{__html: t("hero.title")}}/>
                        <p className="text-xl font-light" dangerouslySetInnerHTML={{__html: t("hero.description")}}/>
                    </div>

                    <div className="flex justify-evenly items-stretch w-full">
                        <div>
                            <h2 className="text-3xl font-bold"><CountUp start={0} end={250} duration={4}
                                                                        separator={"."}/>+</h2>
                            <p className="font-light whitespace-nowrap">{t("stats.clients")}</p>
                        </div>

                        <div className="w-px bg-main/80"/>

                        <div>
                            <h2 className="text-3xl font-bold"><CountUp start={0} end={2000} duration={4}
                                                                        separator={"."}/>+</h2>
                            <p className="font-light whitespace-nowrap">{t("stats.hours")}</p>
                        </div>

                        <div className="w-px bg-main/80"/>

                        <div>
                            <h2 className="text-3xl font-bold"><CountUp start={0} end={5} duration={4} separator={"."}/>+
                            </h2>
                            <p className="font-light whitespace-nowrap">{t("stats.years")}</p>
                        </div>
                    </div>
                </div>

                <div
                    className="absolute bottom-0 w-full h-3/10"
                    style={{
                        background: "linear-gradient(to bottom, rgba(248,248,248,0) 0%, rgba(248,248,248,1) 100%)",
                        pointerEvents: "none"
                    }}
                />
            </section>

            {/*Why Choose Us*/}
            <section className="grid grid-cols-3 grid-rows-2 px-horizontal py-vertical gap-20">
                <div className="col-span-1 flex flex-col">
                    <h2 className="text-5xl font-medium text-left "
                        dangerouslySetInnerHTML={{__html: t("whyChoose.title")}}/>
                </div>

                {reasons.map((reason, index) => (
                    <article key={index} className="col-span-1 flex flex-col gap-6 items-center">
                        {reason.icon}
                        <div className="flex flex-col gap-2 items-center">
                            <h3 className="text-3xl font-bold">{reason.title}</h3>
                            <p className="text-black-text/60">{reason.description}</p>
                        </div>
                    </article>
                ))}
            </section>

            {/*About Aco Section*/}
            <AcoSection />

            {/*Mission, Vision & Purpose*/}
            <section className="flex flex-col gap-12 px-horizontal py-vertical ">
                {/*<h2 className="text-5xl font-medium">{t("foundations.title")}</h2>*/}
                <MagicBento
                    textAutoHide={true}
                    enableStars={false}
                    enableSpotlight={true}
                    enableBorderGlow={true}
                    enableTilt={true}
                    enableMagnetism={true}
                    clickEffect={false}
                    spotlightRadius={300}
                />
            </section>

            {/*Values*/}
            <OurValues />
        </main>
    );
}

export default About;
