// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import ShifterLogo from "../../public/Shifter-S2W-Transparent.png";
import {useAuthContext} from "../context/AuthContext.tsx";
import LinkedIn from "../assets/icons/LinkedIn.tsx";
import Instagram from "../assets/icons/Instagram.tsx";
import NavbarLink from "../components/links/NavbarLink.tsx";
import {useTranslation} from "react-i18next";
import {useUserContext} from "../context/UserContext.tsx";
import {LocalizedLink} from "../components/links/LocalizedLink.tsx";
import {ArrowRight} from "lucide-react";

function FooterNew() {
    const {logout} = useAuthContext();
    const {user} = useUserContext();
    const {t} = useTranslation("footer");

    return (
        <section className="relative flex flex-col gap-12 pt-12 overflow-clip bg-main
            sm:gap-16 sm:pt-16
            md:gap-20 md:pt-20
            lg:gap-24 lg:pt-nav-top">

            {/* background circles */}
            <div className="absolute -bottom-120 -left-90 w-240 aspect-square bg-[radial-gradient(circle_at_center,rgba(0,140,194,0.3),transparent_70%)]
                sm:-bottom-60
                lg:-bottom-160 lg:-left-80 lg:w-320 lg:bg-[radial-gradient(radial-gradient,rgba(0,140,194,0.4),transparent_70%)]"/>
            <div className="absolute -bottom-120 -right-90 w-240 aspect-square bg-[radial-gradient(circle_at_center,rgba(0,140,194,0.3),transparent_70%)]
                sm:-bottom-60
                lg:-bottom-160 lg:-right-80 lg:w-320 lg:bg-[radial-gradient(radial-gradient,rgba(0,140,194,0.4),transparent_70%)]"/>

            {/* CTA */}
            <section className="flex flex-col gap-4 z-10 text-black-text px-4 max-w-screen-2xl mx-auto w-full text-center
                sm:gap-5 sm:px-8
                md:gap-6 md:px-12
                lg:px-20 lg:text-left
                xl:px-40
                2xl:px-60">
                <h2 className="text-2xl font-semibold leading-tight
                    sm:text-3xl
                    md:text-4xl
                    lg:text-5xl
                    xl:text-5xl
                    2xl:text-6xl">
                    {t("cta.title")}
                </h2>
                <p className="text-sm opacity-80
                    sm:text-base
                    md:text-lg
                    lg:text-xl
                    xl:text-xl
                    2xl:text-2xl">
                    {t("cta.subtitle")}
                </p>
                <div className="mt-4 w-full flex justify-center
                    sm:mt-6
                    md:mt-8
                    lg:mt-12 lg:justify-start">
                    <LocalizedLink
                        to={user?.usedFreeConsultation ? "/contact" : "/free-consultation"}
                        className="hover:shadow-shifter/60 transition-all duration-200 ease-in-out cursor-pointer
                        flex items-center justify-center gap-2 shadow-md shadow-shifter/40 border-2 border-white/20
                        bg-shifter py-2 px-6 rounded-lg text-white group text-xs font-semibold w-full
                        sm:w-fit sm:px-8 sm:text-sm
                        md:text-base
                        lg:text-lg
                        xl:text-lg
                        2xl:text-xl"
                    >
                        {user?.usedFreeConsultation
                            ? t("cta.button.contact")
                            : t("cta.button.consultation")}
                        <ArrowRight className="w-4 h-4
                            sm:w-5 sm:h-5
                            md:w-6 md:h-6
                            lg:w-5 lg:h-5
                            xl:w-6 xl:h-6
                            group-hover:translate-x-1 transition-all duration-250 ease-in-out"
                                    strokeWidth={1.5}/>
                    </LocalizedLink>
                </div>
            </section>

            {/* FOOTER */}
            <div className="p-2
                sm:p-3
                md:p-4">
                <footer className="flex flex-col gap-4 rounded-xl shadow-md max-w-screen-2xl mx-auto
                    bg-main/80 backdrop-blur-md text-black-text py-8 px-4
                    sm:py-10 sm:px-6
                    md:py-12 md:px-8
                    lg:pt-12 lg:pb-4 lg:px-horizontal">

                    <div className="flex flex-col gap-8
                        lg:flex-row lg:justify-between lg:gap-0">

                        {/* Logo & Contact Section */}
                        <section className="flex justify-between items-center gap-2
                            sm:gap-4
                            md:gap-4 md:flex-col md:justify-center md:items-start
                            lg:gap-8 lg:max-w-[25%]">
                            <LocalizedLink to="/">
                                <img
                                    src={ShifterLogo}
                                    alt="Shifter - Business Consulting, Mentoring & Online Courses Logo"
                                    className="w-32
                                        sm:w-36
                                        md:w-40"
                                />
                            </LocalizedLink>

                            <div className="flex flex-col items-start text-xs
                                sm:text-sm
                                md:text-base
                                lg:text-xs">
                                <p>contact@mail.shift-er.com</p>
                                <div className="flex gap-2 items-center">
                                    {t("social.visit")}:
                                    <a
                                        href="http://www.google.com"
                                        target="_blank"
                                        rel="noopener noreferrer"
                                        className="w-5 h-5 flex items-center justify-center
                                    sm:w-6 sm:h-6"
                                    >
                                        <LinkedIn className="w-full h-full"/>
                                    </a>

                                    <a
                                        href="http://www.google.com"
                                        target="_blank"
                                        rel="noopener noreferrer"
                                        className="w-5 h-5 flex items-center justify-center
                                sm:w-6 sm:h-6"
                                    >
                                        <Instagram className="w-full h-full"/>
                                    </a>
                                </div>
                            </div>
                        </section>

                        {/* Navigation Links */}
                        <div className="grid grid-cols-2 gap-6
                            sm:grid-cols-2 sm:gap-8
                            md:grid-cols-4 md:gap-10
                            lg:flex lg:gap-12
                            xl:gap-16
                            2xl:gap-20">

                            {/* SERVICES */}
                            <section className="flex flex-col gap-1 text-left font-light">
                                <h3 className="font-bold text-base mb-3
                                    sm:text-lg sm:mb-4
                                    md:text-lg
                                    lg:text-lg
                                    xl:text-xl">
                                    {t("sections.services.title")}
                                </h3>
                                <div className="flex flex-col gap-1 text-xs
                                    sm:text-sm
                                    md:text-sm
                                    lg:text-sm
                                    xl:text-base">
                                    <NavbarLink to="/mentoring" label={t("sections.services.items.mentoring")}
                                                classNameLine="border-black-text"/>
                                    <NavbarLink to="/consulting" label={t("sections.services.items.consulting")}
                                                classNameLine="border-black-text"/>
                                    <NavbarLink to="/courses" label={t("sections.services.items.courses")}
                                                classNameLine="border-black-text"/>
                                    <NavbarLink to="/academies" label={t("sections.services.items.academies")}
                                                classNameLine="border-black-text"/>
                                </div>
                            </section>

                            {/* ABOUT */}
                            <section className="flex flex-col gap-1 text-left font-light">
                                <h3 className="font-bold text-base mb-3
                                    sm:text-lg sm:mb-4
                                    md:text-lg
                                    lg:text-lg
                                    xl:text-xl">
                                    {t("sections.about.title")}
                                </h3>
                                <div className="flex flex-col gap-1 text-xs
                                    sm:text-sm
                                    md:text-sm
                                    lg:text-sm
                                    xl:text-base">
                                    <NavbarLink to="/about" label={t("sections.about.items.about")}
                                                classNameLine="border-black-text"/>
                                </div>
                            </section>

                            {/* PROFILE */}
                            <section className="flex flex-col gap-1 text-left font-light">
                                <h3 className="font-bold text-base mb-3
                                    sm:text-lg sm:mb-4
                                    md:text-lg
                                    lg:text-lg
                                    xl:text-xl">
                                    {t("sections.profile.title")}
                                </h3>
                                <div className="flex flex-col gap-1 text-xs
                                    sm:text-sm
                                    md:text-sm
                                    lg:text-sm
                                    xl:text-base">
                                    {user ? (
                                        <>
                                            <NavbarLink to="/profile" label={t("sections.profile.items.profile")}
                                                        classNameLine="border-black-text"/>
                                            <div className="flex flex-col gap-0 overflow-clip group w-fit">
                                                <button
                                                    onClick={logout}
                                                    className="transition-all duration-300 ease-in-out z-10 cursor-pointer text-left"
                                                >
                                                    {t("sections.profile.items.logout")}
                                                </button>
                                                <hr className="relative -left-[7.5rem] group-hover:-left-4 border-t-2 border-black-text
                                                rounded-full transition-all duration-300 ease-in-out"/>
                                            </div>
                                        </>
                                    ) : (
                                        <NavbarLink to="/login" label={t("sections.profile.items.login")}
                                                    classNameLine="border-black-text"/>
                                    )}
                                </div>
                            </section>

                            {/* CONTACT */}
                            <section className="flex flex-col gap-1 text-left font-light">
                                <h3 className="font-bold text-base mb-3
                                    sm:text-lg sm:mb-4
                                    md:text-lg
                                    lg:text-lg
                                    xl:text-xl">
                                    {t("sections.contact.title")}
                                </h3>
                                <div className="flex flex-col gap-1 text-xs
                                    sm:text-sm
                                    md:text-sm
                                    lg:text-sm
                                    xl:text-base">
                                    <NavbarLink to="/contact" label={t("sections.contact.items.contact")}
                                                classNameLine="border-black-text"/>
                                </div>
                            </section>
                        </div>
                    </div>

                    <hr className="mt-8 border-t-1 border-black/10 w-full
                        sm:mt-10
                        md:mt-12"/>
                    <div className="text-xs font-light text-black/60
                        sm:text-sm
                        md:text-base
                        lg:text-xs">
                        &copy; {new Date().getFullYear()} Shifter. {t("rights")}
                    </div>
                </footer>
            </div>
        </section>
    );
}

export default FooterNew;