import ReactCountryFlag from "react-country-flag";
import { useState } from "react";
import i18n from "../i18n.ts";

export default function LanguageToggle() {
    const [open, setOpen] = useState(false);
    const [lang, setLang] = useState("en");

    const handleLangChange = (lang: string) => {
        setLang(lang);
        i18n.changeLanguage(lang);
        setOpen(false);
    }

    return (
        <div className="fixed bottom-4 left-4 z-50">
            {/* Toggle Button */}
            <button
                onClick={() => setOpen(!open)}
                className="flex items-center justify-center gap-2 p-3 py-2 rounded-md
                   bg-black/40 backdrop-blur-md border border-white/20 text-white
                   hover:bg-black/60 transition-all duration-200 ease-in-out cursor-pointer"
            >
                {lang === "en" ? "EN" : "MK"}
            </button>

            {/* Language List (UPWARD) */}
            {open && (
                <div
                    className="
                        absolute bottom-full mb-1 left-0
                        flex flex-col  w-40
                        bg-black/80 text-white-text
                        shadow-lg rounded-md
                    "
                >
                    <div
                        onClick={() => { handleLangChange("mk") }}
                        className="flex items-center gap-3 px-4 py-2 hover:bg-white/10 cursor-pointer"
                    >
                        <ReactCountryFlag countryCode="MK" svg style={{ width: "20px", height: "20px" }} />
                        <span>Македонски</span>
                    </div>
                    <div
                        onClick={() => { handleLangChange("en") }}
                        className="flex items-center gap-3 px-4 py-2 hover:bg-white/10 cursor-pointer"
                    >
                        <ReactCountryFlag countryCode="GB" svg style={{ width: "20px", height: "20px" }} />
                        <span>English</span>
                    </div>
                </div>
            )}
        </div>
    );
}
