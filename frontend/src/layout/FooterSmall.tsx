// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import ShifterLogo from "../../public/Shifter-S2W-Transparent.png";
import NavbarLink from "../components/NavbarLink.tsx";
import {Instagram, Linkedin} from "lucide-react";
import {useTranslation} from "react-i18next";

export default function FooterSmall () {
    const { t } = useTranslation("footer");
    
    return (
        <footer className="flex justify-between px-horizontal-lg py-vertical-sm bg-beige">
            <img src={ShifterLogo} alt="Shifter - Business Consulting, Mentoring & Online Courses Logo"
                 className="h-12"
            />

            <div className="flex gap-4 items-center">
                <NavbarLink to={"/mentoring"} label={t("sections.services.items.mentoring")} className="text-sm"/>
                <NavbarLink to={"/consulting"} label={t("sections.services.items.consulting")} className="text-sm"/>
                <NavbarLink to={"/courses"} label={t("sections.services.items.courses")} className="text-sm"/>
                <NavbarLink to={"/academies"} label={t("sections.services.items.academies")} className="text-sm"/>
            </div>

            <div className="flex gap-4 ">
                <a
                    href="http://www.google.com"
                    target="_blank"
                    rel="noopener noreferrer"
                    className="bg-shifter/10 rounded-full flex items-center justify-center aspect-square scale-80"
                >
                    <Linkedin size={20} color="var(--color-shifter)"/>
                </a>

                <a
                    href="http://www.google.com"
                    target="_blank"
                    rel="noopener noreferrer"
                    className="bg-shifter/10 rounded-full flex items-center justify-center aspect-square scale-80"
                >
                    <Instagram size={20} color="var(--color-shifter)"/>
                </a>
            </div>
        </footer>
    )
}