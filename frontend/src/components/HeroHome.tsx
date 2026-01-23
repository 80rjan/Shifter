import Silk from "../assets/animations/Silk.tsx";
import CountUp from "react-countup";
import {useTranslation} from "react-i18next";
import {useUserContext} from "../context/UserContext.tsx";
import {LocalizedLink} from "./links/LocalizedLink.tsx";

function HeroHome() {
    const {user} = useUserContext()
    const { t } = useTranslation("home");

    return (
        <section className="relative ">

            <div className="relative flex flex-col items-center rounded-4xl shadow-lg shadow-black/20
             overflow-clip py-60">
                <Silk
                    className="absolute inset-0 opacity-90"
                    speed={8}
                    scale={1}
                    noiseIntensity={4}
                    rotation={0}
                />

                <div className="flex flex-col gap-12 justify-center items-center z-1">
                    <div className="flex flex-col gap-12 items-center justify-center text-white-text px-horizontal-md">
                        {/* <div className="border-1 border-white/40
                        bg-black/10 rounded-full py-1 px-8 font-medium text-md  shadow-sm">
                            {t("hero.label")}
                        </div> */}
                        <h1 className="text-6xl font-bold">
                            {t("hero.title")}
                        </h1>
                        <p className="text-2xl font-medium max-w-3/4 leading-relaxed" dangerouslySetInnerHTML={{ __html: t("hero.description") }} />
                    </div>
                    {/*<LocalizedLink*/}
                    {/*    to={user?.hasUsedFreeConsultation ? "/contact" : "/free-consultation"}*/}
                    {/*    className="hover:shadow-white/60 transition-all duration-300 ease-in-out cursor-pointer*/}
                    {/*    rounded-full text-black/90 px-12 py-3 bg-white font-bold border-2 border-black/20*/}
                    {/*    w-fit shadow-md shadow-white/40">*/}
                    {/*    {user?.hasUsedFreeConsultation ?*/}
                    {/*        t("hero.cta.contact") :*/}
                    {/*        t("hero.cta.freeConsultation")*/}
                    {/*    }*/}
                    {/*</LocalizedLink>*/}
                </div>
            </div>

            <div className="absolute bottom-0 translate-y-1/2 left-1/2 translate-x-[-50%] border-2 border-black/10
                 flex justify-between bg-black/40 backdrop-blur-md w-9/10 py-4 px-24 rounded-lg">
                <div className="text-white">
                    <h3 className="text-4xl font-black"><CountUp start={0} end={Number(t("hero.stats.businessesEmpowered.value"))} duration={4} separator={"."}  />+</h3>
                    <p className=" whitespace-nowrap">{t("hero.stats.businessesEmpowered.label")}</p>
                </div>
                <div className="text-white">
                    <h3 className="text-4xl font-black"><CountUp start={0} end={Number(t("hero.stats.mentoringHours.value"))} duration={4} separator={"."}  />+</h3>
                    <p className=" whitespace-nowrap">{t("hero.stats.mentoringHours.label")}</p>
                </div>
                <div className="text-white">
                    <h3 className="text-4xl font-black"><CountUp start={0} end={Number(t("hero.stats.yearsOfShifter.value"))} duration={4} separator={"."}  />+</h3>
                    <p className=" whitespace-nowrap">{t("hero.stats.yearsOfShifter.label")}</p>
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