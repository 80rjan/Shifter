
import ShifterLogo from "../assets/images/Shifter-S2W-Transparent.png";
import NavbarLink from "../components/links/NavbarLink.tsx";
import {Linkedin} from "lucide-react";
import {useTranslation} from "react-i18next";
import {LocalizedLink} from "../components/links/LocalizedLink.tsx";

export default function FooterSmall() {
    const {t} = useTranslation("footer");

    return (
        <footer className="flex flex-col sm:flex-row justify-between items-center gap-6 sm:gap-4 px-horizontal py-4 bg-main mt-auto">
            <LocalizedLink to="/">
                <img src={ShifterLogo} alt="Shifter - Business Consulting, Mentoring & Online Courses Logo"
                     className="h-8 md:h-10"
                />
            </LocalizedLink>

            <div className="flex flex-wrap justify-center gap-4 lg:gap-6 xl:gap-12 items-center">
                <NavbarLink to={"/courses"} label={t("sections.services.items.courses")} className="text-xs md:text-sm"/>
                <NavbarLink to={"/mentoring"} label={t("sections.services.items.mentoring")} className="text-xs md:text-sm"/>
                <NavbarLink to={"/consulting"} label={t("sections.services.items.consulting")} className="text-xs md:text-sm"/>
                <NavbarLink to={"/academies"} label={t("sections.services.items.academies")} className="text-xs md:text-sm"/>
            </div>

            <div className="flex gap-3 sm:gap-4 items-center">
                <a
                    href="https://www.linkedin.com/company/shift-er"
                    target="_blank"
                    rel="noopener noreferrer"
                    className="hover:bg-shifter/15 duration-300 ease-in-out
                    bg-shifter/10 h-fit p-2 md:p-3 rounded-full"
                >
                    <Linkedin className="w-4 h-4 md:w-5 md:h-5" color="var(--color-shifter)"/>
                </a>

                {/*<a*/}
                {/*    href="http://www.google.com"*/}
                {/*    target="_blank"*/}
                {/*    rel="noopener noreferrer"*/}
                {/*    className="hover:bg-shifter/15 duration-300 ease-in-out*/}
                {/*    bg-shifter/10 h-fit p-2 md:p-3 rounded-full"*/}
                {/*>*/}
                {/*    <Instagram className="w-4 h-4 md:w-5 md:h-5" color="var(--color-shifter)"/>*/}
                {/*</a>*/}
            </div>
        </footer>
    )
}