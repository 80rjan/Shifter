import {useTranslation} from "react-i18next";
import {LocalizedLink} from "./links/LocalizedLink.tsx";
import {ArrowRight} from "lucide-react";

export default function AcoSection() {
    const {t} = useTranslation("about");

    return (
        <section className="grid grid-cols-2 gap-x-40 px-horizontal py-vertical ">
            <div className="col-start-1 col-span-1 flex flex-col text-left gap-12">
                <div className="flex flex-col gap-20">
                    <div className="flex flex-col gap-6">
                        <div className="flex flex-col gap-2">
                            <h2 className="text-5xl font-semibold"
                                dangerouslySetInnerHTML={{__html: t("about.aco")}}/>
                            <p className="text-2xl " dangerouslySetInnerHTML={{__html: t("about.founderAndCeo")}}/>
                        </div>
                        <p dangerouslySetInnerHTML={{__html: t("about.description")}}/>
                    </div>

                    <LocalizedLink to={"/free-consultation"}
                                   className="hover:shadow-shifter/60 shadow-shifter/40 transition-all duration-250 ease-in-out
                                   shadow-md border-2 border-white/40 flex gap-4 items-center text-white bg-shifter px-8 py-2
                                    w-fit rounded-md group font-medium">
                        {t("about.cta")} <ArrowRight size={20} strokeWidth={1.5}
                                                     className="group-hover:translate-x-1 transition-all duration-250 ease-in-out"/>
                    </LocalizedLink>
                </div>
            </div>

            <div className="col-start-2 col-span-1 h-full bg-black/20 rounded-2xl"/>
        </section>
    )
}