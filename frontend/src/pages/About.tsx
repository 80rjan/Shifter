import HeroAbout from "../components/HeroAbout.tsx";
import {
    IconArrowBigUpFilled,
    IconChessKnightFilled,
    IconTrendingUp,
    IconRotate360,
    IconRoute, IconTarget, IconRocket, IconTelescope, IconChessKnight, IconArrowBigUp
} from '@tabler/icons-react';
import {LightBeams} from "../assets/animations/SpikeAnimation.tsx"
import CountUp from "react-countup";
import {Link} from "react-router-dom";
import {ArrowRight} from "lucide-react";
import MagicBento from "../assets/animations/MagicBento.tsx";
import ShifterArrow from "../../public/Shifter-Arrow-White.png"

const reasons = [
    {
        icon: <IconChessKnight size={72} color="var(--color-shifter)" aria-hidden={true} focusable={false}/>,
        title: "Tailored Strategies",
        description: "Strategies and programs designed specifically for your business goals and challenges.",
    },
    {
        icon: <IconArrowBigUp size={72} color="var(--color-shifter)" aria-hidden={true} focusable={false}/>,
        title: "Empowering Clients",
        description: "Teaching clients how to solve challenges independently, not just provide ready-made solutions.",
    },
    {
        icon: <IconRotate360 size={72} color="var(--color-shifter)" aria-hidden={true} focusable={false}/>,
        title: "Holistic Approach",
        description: "A comprehensive method that addresses root causes across your business, not just the symptoms.",
    },
    {
        icon: <IconTrendingUp size={72} color="var(--color-shifter)" aria-hidden={true} focusable={false}/>,
        title: "Sustainable Growth",
        description: "Strategic solutions that strengthen processes, teams, and systems for long-term success.",
    },
    {
        icon: <IconRoute size={72} color="var(--color-shifter)" aria-hidden={true} focusable={false}/>,
        title: "Expert Guidance",
        description: "Solutions from professionals who make business challenges manageable.",
    }
];

function About() {
    return (
        <main className="relative bg-beige text-black-text">
            {/*Hero Section*/}
            <section className="relative py-vertical-lg pt-top-nav-lg px-horizontal-md
                        flex flex-col items-center justify-center">
                <LightBeams/>

                <div className="flex flex-col items-center gap-20 z-1">
                    <div className="flex flex-col  gap-4 max-w-3/4">
                        <h1 className="text-7xl font-semibold ">Business Development & Transformation
                            Center</h1>
                        <p className="text-xl font-light ">
                            <span className="font-semibold">Shifter</span> is a center for business development and
                            transformation,
                            offering mentorship, consulting, academies, and e-learning for family businesses.
                        </p>
                    </div>
                    <div className="flex justify-evenly items-stretch w-full">
                        <div>
                            <h2 className="text-3xl font-bold text"><CountUp start={0} end={250} duration={4}
                                                                             separator={"."}/>+</h2>
                            <p className="font-light whitespace-nowrap">Clients Empowered</p>
                        </div>

                        <div className="w-px  bg-beige/80"/>

                        <div>
                            <h2 className="text-3xl font-bold text"><CountUp start={0} end={2000} duration={4}
                                                                             separator={"."}/>+</h2>
                            <p className="font-light whitespace-nowrap">Mentoring Hours</p>
                        </div>

                        <div className="w-px bg-beige/80"/>

                        <div>
                            <h2 className="text-3xl font-bold text"><CountUp start={0} end={4} duration={4}
                                                                             separator={"."}/>+</h2>
                            <p className="font-light whitespace-nowrap">Years of Shifter</p>
                        </div>
                    </div>
                </div>

                <div
                    className="absolute bottom-0 w-full h-3/10"
                    style={{
                        background: "linear-gradient(to bottom, rgba(248,248,248,0) 0%, rgba(248,248,248,1) 100%)",
                        pointerEvents: "none", // allows clicks to pass through
                    }}
                />
            </section>

            {/*Why Choose Us*/}
            <section className="grid grid-cols-3 grid-rows-2 px-horizontal-md py-vertical-lg gap-20">
                <div className="col-span-1 flex flex-col">
                    <h2 className="text-5xl font-medium text-left ">
                        Why choose <br/> <span className="font-bold text-shifter">Shifter?</span>
                    </h2>
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

            {/*About Section*/}
            <section className="grid grid-cols-2 gap-x-40 px-horizontal-md py-vertical-lg bg-dark-blue/5">

                <div className="col-start-1 col-span-1 flex flex-col text-left gap-12">
                    <div className="flex flex-col gap-20">
                        <div className="flex flex-col gap-4">
                            <h2 className="text-5xl font-medium">About <span
                                className="text-shifter font-black">Shifter</span></h2>
                            <p>
                                Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum
                                has been the
                                industry's standard dummy text ever since the 1500s, when an unknown printer took a
                                galley of type
                                and scrambled it to make a type specimen book. It has survived not only five centuries,
                                but also the
                                leap into electronic typesetting, remaining essentially unchanged. It was popularised in
                                the 1960s
                                with the release of Letraset sheets containing Lorem Ipsum passages, and more recently
                                with desktop
                                publishing software like Aldus PageMaker including versions of Lorem Ipsum.
                            </p>
                        </div>

                        <Link to={"/free-consultation"}
                              className="hover:shadow-shifter/60 shadow-shifter/40 transition-all duration-250 ease-in-out
                              shadow-md border-2 border-white/40 flex gap-4 items-center
                              text-white bg-shifter px-8 py-2 w-fit rounded-md group font-medium">
                            Book a Free Consultation
                            <ArrowRight size={20} strokeWidth={1.5} className="group-hover:translate-x-1 transition-all duration-250 ease-in-out" />
                        </Link>
                    </div>
                </div>

                <div className="col-start-2 col-span-1 h-full bg-black/20 rounded-2xl"/>
            </section>

            {/*Mission, Vision & Purpose*/}
            <section className="flex flex-col gap-12 px-horizontal-md py-vertical-lg bg-dark-blue/5">
                <h2 className="text-5xl font-medium">Our Foundations</h2>
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

            <section className="py-vertical-sm bg-dark-blue/5">
                <div className="relative border-2 border-white/40 shadow-md shadow-shifter/40
                flex justify-between items-end overflow-clip
                px-horizontal-sm py-vertical-md pt-top-nav-lg mx-horizontal-md bg-shifter rounded-xl text-left">
                    <h2 className="text-5xl text-white">Simplify Your Growth <br/> Journey</h2>
                    <Link to={"/free-consultation"}
                          className="z-1 hover:shadow-white/60 shadow-white/40 transition-all duration-250 ease-in-out
                              shadow-md border-2 border-black/10 flex gap-4 items-center
                              text-shifter bg-beige px-4 py-2 w-fit rounded-md font-bold">
                        Book a Free Consultation
                    </Link>

                    <img src={ShifterArrow} alt={"Shifter Arrow"}
                        aria-hidden={true}
                         className="absolute right-0 -bottom-20 rotate-45 w-60 opacity-20"
                    />
                </div>
            </section>
        </main>
    );
}

export default About;
