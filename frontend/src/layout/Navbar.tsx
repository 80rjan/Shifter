import { useState } from 'react';
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import logo from "../../public/Shifter-S2W-White-Transparent.png"
import NavbarLink from "../components/links/NavbarLink.tsx";
import {useTranslation} from "react-i18next";
import {useUserContext} from "../context/UserContext.tsx";
import {LocalizedLink} from "../components/links/LocalizedLink.tsx";

function Navbar() {
    const [isOpen, setIsOpen] = useState(false);
    const {user} = useUserContext();
    const { t } = useTranslation("navbar");

    return (
        <>
            {/* DESKTOP NAVBAR - Hidden on mobile/tablet, shown on lg+ */}
            <nav className="hidden lg:grid grid-cols-[1fr_auto_1fr] fixed top-2 z-50 w-11/12 left-1/2 -translate-x-1/2 items-center py-2 rounded-2xl
    border-1 border-white/30 bg-black/50 backdrop-blur-md text-white font-light overflow-clip"
            >
                {/* Left nav links */}
                <div className="flex justify-start items-center gap-4 xl:justify-between pl-10 2xl:text-xl">
                    <NavbarLink to="/courses" label={t("courses")}/>
                    <NavbarLink to="/mentoring" label={t("mentoring")}/>
                    <NavbarLink to="/consulting" label={t("consulting")}/>
                    <NavbarLink to="/academies" label={t("academies")}/>
                </div>

                {/* Centered Logo */}
                <div className="flex justify-center items-center xl:px-20 2xl:px-32">
                    <LocalizedLink to="/">
                        <img src={logo} alt="Shifter Logo" className="h-12 2xl:h-16"/>
                    </LocalizedLink>
                </div>

                {/* Right nav links + profile */}
                <div className="flex justify-end items-center gap-4 xl:justify-between 2xl:text-xl">
                    <NavbarLink to="/about" label={t("about")}/>
                    {!user && (
                        <NavbarLink to="/login" label={t("login")}/>
                    )}

                    {user && (
                        <LocalizedLink
                            to="/profile"
                            className="hover:bg-shifter transition-all duration-200 ease-in-out cursor-pointer
                    aspect-square rounded-full border-2 border-white/20 p-3 bg-shifter/40 text-white font-bold flex items-center justify-center"
                        >
                            {user.name.split(" ")[0].charAt(0).toUpperCase()}
                        </LocalizedLink>
                    )}

                    {/* CTA button that sticks out */}
                    <LocalizedLink
                        to={`${user?.usedFreeConsultation ? "/contact" : "/free-consultation"}`}
                        className="hover:-translate-x-2 transition-all duration-200 ease-in-out cursor-pointer
                relative -mr-4 py-2 bg-cta rounded-l-lg font-bold whitespace-nowrap
                shadow-md shadow-shifter/30 px-8 pr-10"
                    >
                        {user?.usedFreeConsultation ? t("contact") : t("freeConsultation")}
                    </LocalizedLink>
                </div>
            </nav>

            {/* MOBILE/TABLET NAVBAR - Shown on mobile/tablet, hidden on lg+ */}
            <nav className="lg:hidden fixed top-2 z-50 w-[calc(100%-1rem)] left-1/2 -translate-x-1/2 rounded-2xl
                border-1 border-white/30 bg-black/50 backdrop-blur-md text-white"
            >
                {/* Top bar */}
                <div className="flex items-center justify-between py-1 px-4 md:py-2 md:px-6">
                    {/* Logo */}
                    <LocalizedLink to="/">
                        <img src={logo} alt="Shifter Logo" className="h-6 sm:h-8 md:h-10"/>
                    </LocalizedLink>

                    {/* Hamburger button */}
                    <button
                        onClick={() => setIsOpen(!isOpen)}
                        className="p-2 text-white"
                        aria-label="Toggle menu"
                    >
                        {isOpen ? (
                            <svg className="h-4 sm:h-6 md:h-8 aspect-square" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                            </svg>
                        ) : (
                            <svg className="h-4 sm:h-6 aspect-square" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 6h16M4 12h16M4 18h16" />
                            </svg>
                        )}
                    </button>
                </div>

                {/* Mobile menu dropdown */}
                <div className={`${isOpen ? 'block' : 'hidden'} border-t border-white/20`}>
                    <div className="flex flex-col items-start py-4 px-6 gap-3">
                        <LocalizedLink className="text-sm md:text-lg" to="/courses" onClick={() => setIsOpen(false)}>{t("courses")}</LocalizedLink>
                        <LocalizedLink className="text-sm md:text-lg" to="/mentoring" onClick={() => setIsOpen(false)}>{t("mentoring")}</LocalizedLink>
                        <LocalizedLink className="text-sm md:text-lg" to="/consulting" onClick={() => setIsOpen(false)}>{t("consulting")}</LocalizedLink>
                        <LocalizedLink className="text-sm md:text-lg" to="/academies" onClick={() => setIsOpen(false)}>{t("academies")}</LocalizedLink>
                        <LocalizedLink className="text-sm md:text-lg" to="/about" onClick={() => setIsOpen(false)}>{t("about")}</LocalizedLink>

                        {!user && (
                            <LocalizedLink className="text-sm md:text-lg" to="/login" onClick={() => setIsOpen(false)}>{t("login")}</LocalizedLink>
                        )}

                        {/* Profile button for mobile */}
                        {user && (
                            <LocalizedLink className="text-sm md:text-lg"
                                to="/profile"
                                onClick={() => setIsOpen(false)}
                            >
                                Profile
                            </LocalizedLink>
                        )}

                        {/* CTA button */}
                        <LocalizedLink
                            to={`${user?.usedFreeConsultation ? "/contact" : "/free-consultation"}`}
                            onClick={() => setIsOpen(false)}
                            className="text-sm md:text-lg block w-full text-center py-2 bg-cta rounded-lg font-bold shadow-md shadow-shifter/30 mt-4"
                        >
                            {user?.usedFreeConsultation ? t("contact") : t("freeConsultation")}
                        </LocalizedLink>
                    </div>
                </div>
            </nav>
        </>
    );
}

export default Navbar;