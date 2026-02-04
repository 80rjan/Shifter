// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import ShifterLogo from "../../public/Shifter-S2W-Transparent.png";
import NavbarLink from "../components/links/NavbarLink.tsx";
import {Instagram, Linkedin} from "lucide-react";
import {useTranslation} from "react-i18next";
import {LocalizedLink} from "../components/links/LocalizedLink.tsx";

export default function FooterSmall() {
    const {t} = useTranslation("footer");

    return (
        <footer className="flex justify-between px-horizontal-lg py-vertical-sm bg-main">
            <LocalizedLink to="/">
                <img src={ShifterLogo} alt="Shifter - Business Consulting, Mentoring & Online Courses Logo"
                     className="h-12"
                />
            </LocalizedLink>

            <div className="flex gap-4 items-center">
                <NavbarLink to={"/courses"} label={t("sections.services.items.courses")} className="text-sm"/>
                <NavbarLink to={"/mentoring"} label={t("sections.services.items.mentoring")} className="text-sm"/>
                <NavbarLink to={"/consulting"} label={t("sections.services.items.consulting")} className="text-sm"/>
                <NavbarLink to={"/academies"} label={t("sections.services.items.academies")} className="text-sm"/>
            </div>

            <div className="flex gap-4 items-center">
                {/* TODO: change these footer links to original links*/}
                <a
                    href="http://www.google.com"
                    target="_blank"
                    rel="noopener noreferrer"
                    className="hover:bg-shifter/15 duration-300 ease-in-out
                    bg-shifter/10 h-fit p-3 rounded-full scale-80"
                >
                    <Linkedin size={22} color="var(--color-shifter)"/>
                </a>

                <a
                    href="http://www.google.com"
                    target="_blank"
                    rel="noopener noreferrer"
                    className="hover:bg-shifter/15 duration-300 ease-in-out
                    bg-shifter/10 h-fit p-3 rounded-full scale-80"
                >
                    <Instagram size={22} color="var(--color-shifter)"/>
                </a>
            </div>
        </footer>
    )
}