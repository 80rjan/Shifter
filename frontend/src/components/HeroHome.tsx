import Silk from "../assets/animations/Silk.tsx";
import CountUp from "react-countup";
import {useTranslation} from "react-i18next";
import {useUserContext} from "../context/UserContext.tsx";
import {LocalizedLink} from "./links/LocalizedLink.tsx";

function HeroHome() {
    const {user} = useUserContext()
    const {t} = useTranslation("home");

    return (
        <section className="px-2 py-vertical pt-6 lg:pt-8">
            <div className="relative">

                <div className="relative flex flex-col items-center rounded-xl shadow-lg shadow-black/20
                    overflow-clip py-nav-top
                    xl:rounded-4xl ">
                    <Silk
                        className="absolute inset-0 opacity-90"
                        speed={8}
                        scale={1}
                        noiseIntensity={4}
                        rotation={0}
                    />

                    <div className="flex flex-col gap-10 justify-center items-center z-1 max-w-screen-2xl mx-auto">
                        <header className="flex flex-col gap-4 items-center justify-center text-white-text px-horizontal
                                md:gap-10
                                xl:gap-16
                                2xl:px-0">
                            {/* <div className="border-1 border-white/40
                                    bg-black/10 rounded-full py-1 px-8 font-medium text-md  shadow-sm">
                                        {t("hero.label")}
                            </div> */}
                            <h1 className="text-3xl font-bold max-w-full
                            sm:text-4xl
                            md:text-5xl
                            lg:text-6xl
                            xl:text-7xl
                            2xl:text-8xl">
                                {t("hero.title")}
                            </h1>
                            <p className="text-xs font-medium leading-relaxed max-w-full
                            sm:text-sm sm:max-w-9/10
                            md:text-lg
                            lg:text-2xl
                            xl:text-2xl xl:max-w-8/10
                            2xl:text-3xl"
                               dangerouslySetInnerHTML={{__html: t("hero.description")}}/>
                        </header>
                        <LocalizedLink
                            to={user?.usedFreeConsultation ? "/contact" : "/free-consultation"}
                            className="text-xs lg:hidden md:text-sm sm:px-8 md:px-12 sm:py-3/2 md:py-2
                            hover:shadow-white/60 transition-all duration-300 ease-in-out cursor-pointer
                            rounded-lg text-black/90 px-4 py-1 bg-white font-semibold sm:font-bold border-2 border-black/20
                            w-fit shadow-md shadow-white/40">
                            {user?.usedFreeConsultation ?
                                t("hero.cta.contact") :
                                t("hero.cta.freeConsultation")
                            }
                        </LocalizedLink>
                    </div>
                </div>

                <aside aria-label="Statistics" className="absolute bottom-0 translate-y-1/2 left-1/2 translate-x-[-50%] border-2 border-black/10
                 flex justify-between bg-black/40 backdrop-blur-md w-[calc(100%-1rem)] rounded-lg py-2 px-4 gap-4
                 sm:px-8
                 md:px-12
                 lg:px-16
                 xl:py-4 xl:px-24 xl:w-9/10
                 2xl:py-6 2xl:px-32 2xl:w-8/10">
                    <div className="text-white text-center max-w-1/3">
                        <h3 className="text-xl font-black
                        sm:text-2xl
                        lg:text-3xl
                        xl:text-4xl
                        2xl:text-5xl"><CountUp start={0} end={Number(t("hero.stats.businessesEmpowered.value"))}
                                              duration={4} separator={"."}/>+</h3>
                        <p className="text-xs
                        md:text-base
                        lg:text-lg lg:whitespace-nowrap
                        xl:text-base
                        2xl:text-xl">{t("hero.stats.businessesEmpowered.label")}</p>
                    </div>
                    <div className="text-white text-center max-w-1/3">
                        <h3 className="text-xl font-black
                        sm:text-2xl
                        lg:text-3xl
                        xl:text-4xl
                        2xl:text-5xl"><CountUp start={0} end={Number(t("hero.stats.mentoringHours.value"))} duration={4}
                                              separator={"."}/>+</h3>
                        <p className="text-xs
                        md:text-base
                        lg:text-lg lg:whitespace-nowrap
                        xl:text-base
                        2xl:text-xl">{t("hero.stats.mentoringHours.label")}</p>
                    </div>
                    <div className="text-white text-center max-w-1/3">
                        <h3 className="text-xl font-black
                        sm:text-2xl
                        lg:text-3xl
                        xl:text-4xl
                        2xl:text-5xl"><CountUp start={0} end={Number(t("hero.stats.yearsOfShifter.value"))} duration={4}
                                              separator={"."}/>+</h3>
                        <p className="text-xs
                        md:text-base
                        lg:text-lg lg:whitespace-nowrap
                        xl:text-base
                        2xl:text-xl">{t("hero.stats.yearsOfShifter.label")}</p>
                    </div>
                </aside>
            </div>
        </section>
    )
}

export default HeroHome