import {LocalizedLink} from "../components/links/LocalizedLink.tsx";
import {useTranslation} from "react-i18next";

export default function ComingSoon() {
    const { t } = useTranslation("comingSoon");

    return (
        <main className="flex-1 flex flex-col items-center justify-center gap-12 bg-beige">
            <section className="flex flex-col items-center gap-6 w-fit">
                <h1 className="text-6xl text-shifter font-bold relative">
                    {t("courses.title")}
                    <div className="absolute top-0 -right-2">
                        <hr className="absolute -top-1 right-2 bg-shifter border-2 w-6 rounded-full
                                   -rotate-70 origin-bottom-left translate-x-full"/>
                        <hr className="absolute top-0 right-0 bg-shifter border-2 w-6 rounded-full
                                   -rotate-45 origin-bottom-left translate-x-full"/>
                        <hr className="absolute top-2 -right-1 bg-shifter border-2 w-6 rounded-full
                                   -rotate-20 origin-bottom-left translate-x-full"/>
                    </div>
                </h1>
                <p className="text-black/60 font-medium text-lg">
                    {t("courses.description")}
                </p>
            </section>

            <div className="flex gap-4">
                <LocalizedLink
                    to='/'
                    className="hover:shadow-black/20 transition-all duration-300 ease-in-out cursor-pointer
                        rounded-full text-shifter px-12 py-2  font-bold border-2 border-shifter/20
                        w-fit shadow-md shadow-black/10"
                >
                    {t("courses.homePage")}
                </LocalizedLink>
                <LocalizedLink
                    to='/contact'
                    className="hover:shadow-shifter/40 transition-all duration-300 ease-in-out cursor-pointer
                        rounded-full text-white px-12 py-2 bg-shifter font-bold border-2 border-white/40
                        w-fit shadow-md shadow-shifter/20"
                >
                    {t("courses.contact")}
                </LocalizedLink>
            </div>
        </main>
    )
}