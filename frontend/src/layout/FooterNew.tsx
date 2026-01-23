// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import ShifterLogo from "../../public/Shifter-S2W-Transparent.png";
import { useAuthContext } from "../context/AuthContext.tsx";
import LinkedIn from "../assets/icons/LinkedIn.tsx";
import Instagram from "../assets/icons/Instagram.tsx";
import NavbarLink from "../components/links/NavbarLink.tsx";
import { useTranslation } from "react-i18next";
import {useUserContext} from "../context/UserContext.tsx";
import {LocalizedLink} from "../components/links/LocalizedLink.tsx";
import {ArrowRight} from "lucide-react";

function FooterNew() {
    const { logout } = useAuthContext();
    const { user } = useUserContext();
    const { t } = useTranslation("footer");

    return (
        <section className="relative flex flex-col gap-24 pt-top-nav-lg overflow-clip bg-main">

            {/* background circles */}
            <div className="absolute -bottom-150 -left-80 w-320 aspect-square bg-[radial-gradient(circle_at_center,rgba(0,140,194,0.4),transparent_70%)]"></div>
            <div className="absolute -bottom-150 -right-80 w-320 aspect-square bg-[radial-gradient(circle_at_center,rgba(0,140,194,0.4),transparent_70%)]"></div>

            {/* CTA */}
            <section className="flex flex-col gap-6 z-1 text-black-text px-60">
                <h2 className="text-5xl font-semibold leading-tight">
                    {t("cta.title")}
                </h2>
                <p className="text-xl opacity-80">
                    {t("cta.subtitle")}
                </p>
                <div className="mt-12 w-full flex justify-center">
                    <LocalizedLink
                        to={user?.hasUsedFreeConsultation ? "/contact" : "/free-consultation"}
                        className="hover:shadow-shifter/60 transition-all duration-200 ease-in-out cursor-pointer
                        flex items-center gap-2 w-fit shadow-md shadow-shifter/40 font-bold border-2 border-white/20
                        bg-shifter py-3 px-8 rounded-lg text-white group"
                    >
                        {user?.hasUsedFreeConsultation
                            ? t("cta.button.contact")
                            : t("cta.button.consultation")}
                        <ArrowRight size={20} strokeWidth={1.5}
                                    className="group-hover:translate-x-1 transition-all duration-250 ease-in-out"/>
                    </LocalizedLink>
                </div>
            </section>

            {/* FOOTER */}
            <div className="p-4">
                <footer className="flex flex-col gap-4 rounded-xl shadow-md
                bg-beige/80 backdrop-blur-md text-black-text pt-12 pb-4 px-horizontal-sm">

                    <div className="flex justify-between">
                        <section className="flex flex-col justify-center gap-12 max-w-1/4">
                            <LocalizedLink to="/">
                                <img
                                    src={ShifterLogo}
                                    alt="Shifter - Business Consulting, Mentoring & Online Courses Logo"
                                    className="w-40"
                                />
                            </LocalizedLink>

                            <div className="flex flex-col items-start text-xs">
                                <p>contact@mail.shift-er.com</p>
                                <div className="flex gap-2 items-center">
                                    {t("social.visit")}:
                                    {/* TODO: change these footer links to original links*/}
                                    <a
                                        href="http://www.google.com"
                                        target="_blank"
                                        rel="noopener noreferrer"
                                        className="w-6 h-6 flex items-center justify-center"
                                    >
                                        <LinkedIn className="w-full h-full" />
                                    </a>
                                    <a
                                        href="http://www.google.com"
                                        target="_blank"
                                        rel="noopener noreferrer"
                                        className="w-6 h-6 flex items-center justify-center"
                                    >
                                        <Instagram className="w-full h-full" />
                                    </a>
                                </div>
                            </div>
                        </section>

                        <div>
                            <div className="flex gap-20 text-sm">
                                {/* SERVICES */}
                                <section className="flex flex-col gap-1 text-left font-light">
                                    <h3 className="font-bold text-lg mb-4">{t("sections.services.title")}</h3>
                                    <NavbarLink to="/mentoring" label={t("sections.services.items.mentoring")} classNameLine="border-black-text" />
                                    <NavbarLink to="/consulting" label={t("sections.services.items.consulting")} classNameLine="border-black-text" />
                                    <NavbarLink to="/courses" label={t("sections.services.items.courses")} classNameLine="border-black-text" />
                                    <NavbarLink to="/academies" label={t("sections.services.items.academies")} classNameLine="border-black-text" />
                                </section>

                                {/* ABOUT */}
                                <section className="flex flex-col gap-1 text-left font-light">
                                    <h3 className="font-bold text-lg mb-4">{t("sections.about.title")}</h3>
                                    <NavbarLink to="/about" label={t("sections.about.items.about")} classNameLine="border-black-text" />
                                </section>

                                {/* PROFILE */}
                                <section className="flex flex-col gap-1 text-left font-light">
                                    <h3 className="font-bold text-lg mb-4">{t("sections.profile.title")}</h3>
                                    {user ? (
                                        <>
                                            <NavbarLink to="/profile" label={t("sections.profile.items.profile")} classNameLine="border-black-text" />
                                            <div className="flex flex-col gap-0 overflow-clip group w-fit">
                                                <button
                                                    onClick={logout}
                                                    className="transition-all duration-300 ease-in-out z-10 cursor-pointer"
                                                >
                                                    {t("sections.profile.items.logout")}
                                                </button>
                                                <hr className="relative -left-30 group-hover:-left-4 border-t-2
                                                rounded-full transition-all duration-300 ease-in-out"/>
                                            </div>
                                        </>
                                    ) : (
                                        <NavbarLink to="/login" label={t("sections.profile.items.login")} classNameLine="border-black-text" />
                                    )}
                                </section>

                                {/* DASHBOARD */}
                                {/*{user && (*/}
                                {/*    <section className="flex flex-col gap-1 text-left font-light">*/}
                                {/*        <h3 className="font-bold text-lg mb-4">{t("sections.dashboard.title")}</h3>*/}
                                {/*        <NavbarLink to="/profile" label={t("sections.dashboard.items.profile")} classNameLine="border-black-text" />*/}
                                {/*        <NavbarLink to="/learn" label={t("sections.dashboard.items.learning")} classNameLine="border-black-text" />*/}
                                {/*    </section>*/}
                                {/*)}*/}

                                {/* CONTACT */}
                                <section className="flex flex-col gap-1 text-left font-light">
                                    <h3 className="font-bold text-lg mb-4">{t("sections.contact.title")}</h3>
                                    <NavbarLink to="/contact" label={t("sections.contact.items.contact")} classNameLine="border-black-text" />
                                </section>
                            </div>
                        </div>
                    </div>

                    <hr className="mt-12 border-t-1 border-black/10 w-full" />
                    <div className="text-xs font-light text-black/60">
                        &copy; {new Date().getFullYear()} Shifter. {t("rights")}
                    </div>
                </footer>
            </div>
        </section>
    );
}

export default FooterNew;
