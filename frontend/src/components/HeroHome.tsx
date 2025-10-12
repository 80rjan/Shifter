
import {Link} from 'react-router-dom';
import Silk from "../assets/animations/Silk.tsx";
import CountUp from "react-countup";
import {useAuthContext} from "../context/AuthContext.tsx";
import {useTranslation} from "react-i18next";

function HeroHome() {
    const {user} = useAuthContext()
    const { t } = useTranslation("home");

    return (
        <section className="relative ">

            <div className="relative flex flex-col items-center gap-0 rounded-4xl shadow-lg shadow-black/20
             overflow-clip py-top-nav-lg">
                <Silk
                    className="absolute inset-0 opacity-90"
                    speed={8}
                    scale={1}
                    color="#008CC2"
                    noiseIntensity={1}
                    rotation={0}
                />

                <div className="flex flex-col gap-12 justify-center items-center z-1">
                    <div className="flex flex-col gap-6 items-center justify-center">
                        <div className="border-1 border-white/40
                        bg-black/10 rounded-full py-1 px-8 font-medium text-sm text-white shadow-sm">
                            {t("hero.label")}
                        </div>
                        <h1 className="text-7xl font-semibold text-white max-w-3/4">
                            {t("hero.title")}
                        </h1>
                        <p className="text-xl font-light text-white max-w-3/4">
                            {t("hero.description")}
                        </p>
                    </div>
                    <Link
                        to={user?.hasUsedFreeConsultation ? "/contact" : "/free-consultation"}
                        className="hover:shadow-white/60 transition-all duration-200 ease-in-out cursor-pointer
                        rounded-full text-black/90 px-12 py-3 bg-white font-bold border-2 border-black/20
                        w-fit shadow-md shadow-white/40">
                        {user?.hasUsedFreeConsultation ?
                            t("hero.cta.contact") :
                            t("hero.cta.freeConsultation")
                        }
                    </Link>
                </div>
            </div>

            <div className="absolute bottom-0 translate-y-1/2 left-1/2 translate-x-[-50%] border-2 border-black/10
                 flex justify-between bg-black/40 backdrop-blur-md w-9/10 py-6 px-24 rounded-lg">
                <div className="text-white">
                    <h3 className="text-4xl font-bold"><CountUp start={0} end={Number(t("hero.stats.businessesEmpowered.value"))} duration={4} separator={"."}  />+</h3>
                    <p className="font-light whitespace-nowrap">{t("hero.stats.businessesEmpowered.label")}</p>
                </div>
                <div className="text-white">
                    <h3 className="text-4xl font-bold"><CountUp start={0} end={Number(t("hero.stats.mentoringHours.value"))} duration={4} separator={"."}  />+</h3>
                    <p className="font-light whitespace-nowrap">{t("hero.stats.mentoringHours.label")}</p>
                </div>
                <div className="text-white">
                    <h3 className="text-4xl font-bold"><CountUp start={0} end={Number(t("hero.stats.yearsOfShifter.value"))} duration={4} separator={"."}  />+</h3>
                    <p className="font-light whitespace-nowrap">{t("hero.stats.yearsOfShifter.label")}</p>
                </div>
                {/*<div className="text-white">*/}
                {/*    <h3 className="text-4xl font-bold"><CountUp start={0} end={10} duration={4} separator={"."}  />+</h3>*/}
                {/*    /!*<p className="font-light whitespace-nowrap">Courses & Academies Available</p>*!/*/}
                {/*    <p className="font-light whitespace-nowrap">Growth Programs Delivered</p>*/}
                {/*</div>*/}
            </div>
        </section>
    )
}

export default HeroHome