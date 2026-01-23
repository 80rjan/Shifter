// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import logo from "../../public/Shifter-S2W-White-Transparent.png"
import NavbarLink from "../components/links/NavbarLink.tsx";
import {useTranslation} from "react-i18next";
import {useUserContext} from "../context/UserContext.tsx";
import {LocalizedLink} from "../components/links/LocalizedLink.tsx";

function Navbar() {
    const {user} = useUserContext();
    const { t } = useTranslation("navbar");

    return (
        <nav
            className="fixed top-2 z-50 w-11/12 left-1/2 -translate-x-1/2 flex items-center justify-between py-2 rounded-2xl
            border-1 border-white/30 bg-black/50 backdrop-blur-md text-white font-light overflow-clip gap-20"
        >
            {/* Left nav links */}
            <div className={`flex w-40/100 justify-between text-md items-center
                                pl-10 ${user?.hasUsedFreeConsultation && "pl-12"}`}>
                {/* Link group */}
                <NavbarLink to="/courses" label={t("courses")}/>
                <NavbarLink to="/mentoring" label={t("mentoring")}/>
                <NavbarLink to="/consulting" label={t("consulting")}/>
                <NavbarLink to="/academies" label={t("academies")}/>
            </div>

            {/* Centered Logo (NO ABSOLUTE!) */}
            <div className="flex justify-center items-center w-fit">
                <LocalizedLink to="/">
                    <img src={logo} alt="Shifter Logo" className="h-12"/>
                </LocalizedLink>
            </div>

            {/* Right nav links + profile */}
            <div className="flex w-40/100 justify-between text-md items-center gap-6">
                <div className="flex justify-center items-center gap-12 h-fit w-full ">
                    <NavbarLink to="/about" label={t("about")}/>
                    {
                        !user &&
                        // <NavbarLink to="/learn" label={t("learn")}/> :
                        <NavbarLink to="/login" label={t("login")}/>
                    }
                </div>
                <div className="flex gap-4 items-center">
                    {
                        user &&
                        <LocalizedLink
                            to="/profile"
                            className="hover:bg-shifter transition-all duration-200 ease-in-out cursor-pointer
                                h-full aspect-square rounded-full border-2 border-white/20 p-3 bg-shifter/40 text-white font-bold flex items-center justify-center"
                        >
                            {user.name.split(" ")[0].charAt(0).toUpperCase()}
                        </LocalizedLink>
                    }
                    <LocalizedLink
                        to={`${user?.hasUsedFreeConsultation ? "/contact" : "/free-consultation"}`}
                        className={`hover:-translate-x-2 transition-all duration-200 ease-in-out cursor-pointer
                                relative -mr-4 py-2 bg-cta rounded-l-lg font-bold whitespace-nowrap
                                shadow-md shadow-shifter/30 px-8 pr-10 ${user?.hasUsedFreeConsultation && "px-10 pr-12"}`}
                    >
                        {user?.hasUsedFreeConsultation ? t("contact") : t("freeConsultation")}
                    </LocalizedLink>
                </div>
            </div>
        </nav>

    )
}


export default Navbar