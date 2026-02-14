import {useTranslation} from "react-i18next";
import CEOImage from "../../public/Aco Gjorgjievski.jpg";

export default function AcoSection() {
    const {t} = useTranslation("about");

    return (
        <section className="px-horizontal py-vertical">
            <div className="max-w-screen-2xl mx-auto grid grid-cols-1 gap-between
                lg:grid-cols-2">

                {/* Text Content */}
                <div className="flex flex-col text-left gap-8
                    sm:gap-10
                    md:gap-12
                    lg:col-span-1">
                    <div className="flex flex-col gap-8
                        sm:gap-10
                        md:gap-12
                        lg:gap-16
                        xl:gap-20">
                        <div className="flex flex-col gap-4
                            sm:gap-5
                            md:gap-6">
                            <div className="flex flex-col gap-1">
                                <h2 className="text-2xl font-bold
                                    sm:text-3xl
                                    md:text-4xl
                                    lg:text-5xl
                                    xl:text-5xl
                                    2xl:text-6xl"
                                    dangerouslySetInnerHTML={{__html: t("about.aco")}}/>
                                <p className="text-lg font-medium
                                    sm:text-xl
                                    md:text-2xl
                                    lg:text-xl
                                    xl:text-2xl
                                    2xl:text-3xl"
                                   dangerouslySetInnerHTML={{__html: t("about.founderAndCeo")}}/>
                            </div>
                            <p className="text-base leading-relaxed
                                sm:text-base
                                md:text-lg
                                lg:text-lg
                                xl:text-xl
                                2xl:text-xl"
                               dangerouslySetInnerHTML={{__html: t("about.description")}}/>
                        </div>

                        {/*<LocalizedLink to={"/free-consultation"}*/}
                        {/*               className="hover:shadow-shifter/60 shadow-shifter/40 transition-all duration-250 ease-in-out*/}
                        {/*               shadow-md border-2 border-white/40 flex gap-3 items-center justify-center text-white bg-shifter px-6 py-2*/}
                        {/*                w-full rounded-md group font-medium text-sm*/}
                        {/*                sm:gap-4 sm:px-8 sm:py-3 sm:text-base sm:justify-start sm:w-fit*/}
                        {/*                md:text-lg*/}
                        {/*                lg:text-base*/}
                        {/*                xl:text-lg*/}
                        {/*                2xl:text-xl">*/}
                        {/*    {t("about.cta")}*/}
                        {/*    <ArrowRight className="w-4 h-4*/}
                        {/*        sm:w-5 sm:h-5*/}
                        {/*        md:w-6 md:h-6*/}
                        {/*        lg:w-5 lg:h-5*/}
                        {/*        xl:w-6 xl:h-6*/}
                        {/*        group-hover:translate-x-1 transition-all duration-250 ease-in-out"*/}
                        {/*                strokeWidth={1.5}/>*/}
                        {/*</LocalizedLink>*/}
                    </div>
                </div>

                {/* Image/Placeholder */}
                <div className="h-80 bg-black/20 rounded-2xl overflow-hidden
                    sm:h-96
                    md:h-120
                    lg:col-span-1 lg:h-full
                    2xl:min-h-[600px] 2xl:max-h-[500px]">
                    <img src={CEOImage} alt="CEO image"
                         className="w-full h-full object-cover object-[center_20%]
                         sm:object-[center_10%]
                         lg:object-center"/>
                </div>
            </div>
        </section>
    )
}