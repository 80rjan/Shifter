import {
    IconTrendingUp,
    IconRotate360,
    IconRoute, IconChessKnight, IconArrowBigUp
} from '@tabler/icons-react';
import CountUp from "react-countup";
import {useTranslation} from "react-i18next";
import OurValues from "../components/OurValues.tsx";
import AcoSection from "../components/AcoSection.tsx";
import {Suspense} from "react";
import {LazyLightBeams, LazyMagicBento} from "../assets/animations/LazyComponents.tsx";

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
            <section className="relative py-vertical pt-nav-top px-horizontal">
                <div className="max-w-screen-2xl mx-auto flex flex-col items-center justify-center">
                    <Suspense fallback={null}>
                        <LazyLightBeams tilt={-50} beamCount={120} initialHeight={120 + Math.random() * 60} spikeMoveDown={8} />
                    </Suspense>

                    <div className="flex flex-col items-center gap-10 z-10
                        sm:gap-12
                        md:gap-16
                        lg:gap-20">
                        <div className="flex flex-col gap-3 w-full text-center
                            sm:gap-4
                            md:max-w-[90%]
                            lg:max-w-[75%]">
                            <h1 className="text-3xl font-semibold
                                sm:text-4xl
                                md:text-5xl
                                lg:text-6xl
                                xl:text-7xl
                                2xl:text-8xl"
                                dangerouslySetInnerHTML={{__html: t("hero.title")}}/>
                            <p className="text-sm font-light
                                sm:text-base
                                md:text-lg
                                lg:text-xl
                                xl:text-xl
                                2xl:text-2xl"
                               dangerouslySetInnerHTML={{__html: t("hero.description")}}/>
                        </div>

                        <div className="flex flex-col gap-6 items-center w-full
                            sm:flex-row sm:justify-evenly sm:items-stretch">
                            <div className="text-center">
                                <h2 className="text-2xl font-bold
                                    sm:text-3xl
                                    md:text-4xl
                                    lg:text-3xl
                                    xl:text-4xl">
                                    <CountUp start={0} end={250} duration={4} separator={"."}/>+
                                </h2>
                                <p className="font-light whitespace-nowrap text-sm
                                    sm:text-base
                                    md:text-lg
                                    lg:text-base">
                                    {t("stats.clients")}
                                </p>
                            </div>

                            <div className="hidden sm:block w-px bg-main/80"/>

                            <div className="text-center">
                                <h2 className="text-2xl font-bold
                                    sm:text-3xl
                                    md:text-4xl
                                    lg:text-3xl
                                    xl:text-4xl">
                                    <CountUp start={0} end={2000} duration={4} separator={"."}/>+
                                </h2>
                                <p className="font-light whitespace-nowrap text-sm
                                    sm:text-base
                                    md:text-lg
                                    lg:text-base">
                                    {t("stats.hours")}
                                </p>
                            </div>

                            <div className="hidden sm:block w-px bg-main/80"/>

                            <div className="text-center">
                                <h2 className="text-2xl font-bold
                                    sm:text-3xl
                                    md:text-4xl
                                    lg:text-3xl
                                    xl:text-4xl">
                                    <CountUp start={0} end={5} duration={4} separator={"."}/>+
                                </h2>
                                <p className="font-light whitespace-nowrap text-sm
                                    sm:text-base
                                    md:text-lg
                                    lg:text-base">
                                    {t("stats.years")}
                                </p>
                            </div>
                        </div>
                    </div>

                    <div
                        className="absolute bottom-0 w-full h-[30%]"
                        style={{
                            background: "linear-gradient(to bottom, rgba(250,251,252,0) 0%, rgba(250,251,252,1) 100%)",
                            pointerEvents: "none"
                        }}
                    />
                </div>
            </section>

            {/*Why Choose Us*/}
            <section className="px-horizontal py-vertical">
                <div className="max-w-screen-2xl mx-auto grid grid-cols-1 gap-8
                    sm:grid-cols-2 sm:gap-10
                    md:gap-12
                    lg:grid-cols-3 lg:grid-rows-2 lg:gap-16
                    xl:gap-20">

                    <div className="flex flex-col text-center
                        sm:col-span-2 sm:text-left
                        lg:col-span-1">
                        <h2 className="text-3xl font-medium
                            sm:text-4xl
                            md:text-5xl
                            lg:text-4xl
                            xl:text-5xl
                            2xl:text-6xl"
                            dangerouslySetInnerHTML={{__html: t("whyChoose.title")}}/>
                    </div>

                    {reasons.map((reason, index) => (
                        <article key={index} className="flex flex-col gap-4 items-center text-center
                            sm:col-span-1 sm:gap-5
                            md:gap-6
                            lg:gap-6
                            xl:gap-6">
                            <div className="[&>svg]:w-12 [&>svg]:h-12
                                sm:[&>svg]:w-14 sm:[&>svg]:h-14
                                md:[&>svg]:w-16 md:[&>svg]:h-16
                                lg:[&>svg]:w-16 lg:[&>svg]:h-16
                                xl:[&>svg]:w-18 xl:[&>svg]:h-18">
                                {reason.icon}
                            </div>
                            <div className="flex flex-col gap-2">
                                <h3 className="text-xl font-bold
                                    sm:text-2xl
                                    md:text-3xl
                                    lg:text-2xl
                                    xl:text-3xl
                                    2xl:text-4xl">
                                    {reason.title}
                                </h3>
                                <p className="text-sm text-black-text/60
                                    sm:text-base
                                    md:text-lg
                                    lg:text-base
                                    xl:text-lg
                                    2xl:text-xl">
                                    {reason.description}
                                </p>
                            </div>
                        </article>
                    ))}
                </div>
            </section>

            {/*About Aco Section*/}
            <AcoSection />

            {/*Mission, Vision & Purpose*/}
            <section className="px-horizontal py-vertical">
                <div className="max-w-screen-2xl mx-auto flex flex-col gap-8
                    sm:gap-10
                    md:gap-12">
                    <Suspense fallback={
                        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-10">
                            {[1, 2, 3].map((i) => (
                                <div
                                    key={i}
                                    className="h-64 bg-gray-200/50 rounded-lg animate-pulse flex items-center justify-center"
                                >
                                    <div className="h-12 loader" />
                                </div>
                            ))}
                        </div>
                    }>
                        <LazyMagicBento
                            textAutoHide={true}
                            enableStars={false}
                            enableSpotlight={true}
                            enableBorderGlow={true}
                            enableTilt={true}
                            enableMagnetism={true}
                            clickEffect={false}
                            spotlightRadius={300}
                        />
                    </Suspense>
                </div>
            </section>

            {/*Values*/}
            <OurValues />
        </main>
    );
}

export default About;