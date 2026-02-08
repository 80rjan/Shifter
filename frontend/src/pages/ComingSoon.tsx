import {LocalizedLink} from "../components/links/LocalizedLink.tsx";
import {useTranslation} from "react-i18next";

export default function ComingSoon() {
    const { t } = useTranslation("comingSoon");

    return (
        <main className="flex-1 flex flex-col items-center justify-center gap-8 sm:gap-10 md:gap-12 bg-main px-4 sm:px-6 md:px-8 py-8">
            <section className="flex flex-col items-center gap-4 sm:gap-5 md:gap-6 w-fit">
                <h1 className="text-3xl sm:text-4xl md:text-5xl lg:text-6xl text-shifter font-bold relative text-center">
                    {t("courses.title")}
                    <div className="absolute top-0 right-4 sm:-right-2 scale-75 sm:scale-90 md:scale-100">
                        <hr className="absolute -top-1 right-2 bg-shifter border-2 w-6 rounded-full
                                   -rotate-70 origin-bottom-left translate-x-full"/>
                        <hr className="absolute top-0 right-0 bg-shifter border-2 w-6 rounded-full
                                   -rotate-45 origin-bottom-left translate-x-full"/>
                        <hr className="absolute top-2 -right-1 bg-shifter border-2 w-6 rounded-full
                                   -rotate-20 origin-bottom-left translate-x-full"/>
                    </div>
                </h1>
                <p className="text-black/60 font-medium text-sm sm:text-base md:text-lg text-center max-w-xs sm:max-w-sm md:max-w-md">
                    {t("courses.description")}
                </p>
            </section>

            <div className="flex flex-col sm:flex-row gap-3 sm:gap-4 w-full sm:w-auto">
                <LocalizedLink
                    to='/'
                    className="hover:shadow-black/20 transition-all duration-300 ease-in-out cursor-pointer
                        rounded-lg text-shifter px-8 sm:px-10 md:px-12 py-2 font-bold border-2 border-shifter/20
                        w-full sm:w-fit shadow-md shadow-black/10 text-center text-sm sm:text-base"
                >
                    {t("courses.homePage")}
                </LocalizedLink>
                <LocalizedLink
                    to='/contact'
                    className="hover:shadow-shifter/40 transition-all duration-300 ease-in-out cursor-pointer
                        rounded-lg text-white px-8 sm:px-10 md:px-12 py-2 bg-shifter font-bold border-2 border-white/40
                        w-full sm:w-fit shadow-md shadow-shifter/20 text-center text-sm sm:text-base"
                >
                    {t("courses.contact")}
                </LocalizedLink>
            </div>
        </main>
    )
}