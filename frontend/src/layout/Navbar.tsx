import {Link} from "react-router-dom";
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import logo from "../../public/Shifter-S2W-White-Transparent.png"
import {useAuthContext} from "../context/AuthContext.tsx";
import NavbarLink from "../components/NavbarLink.tsx";
import {useTranslation} from "react-i18next";

function Navbar() {
    const {user} = useAuthContext();
    const { t } = useTranslation("navbar");

    return (
        <nav
            className="fixed top-2 z-50 w-11/12 left-1/2 -translate-x-1/2 flex items-center justify-between py-2 rounded-full
            border-3 border-white/30 bg-black/50 backdrop-blur-md text-white font-light overflow-clip gap-20"
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
                <Link to="/">
                    <img src={logo} alt="Shifter Logo" className="h-12"/>
                </Link>
            </div>

            {/* Right nav links + profile */}
            <div className="flex w-40/100 justify-between text-md items-center gap-6">
                <NavbarLink to="/about" label={t("about")}/>
                {
                    user ?
                        <NavbarLink to="/learn" label={t("learn")}/> :
                        <NavbarLink to="/login" label={t("login")}/>
                }
                <div className="flex gap-4 items-center">
                    {
                        user &&
                        <Link
                            to="/profile"
                            className="hover:bg-shifter transition-all duration-200 ease-in-out cursor-pointer
                                h-full aspect-square rounded-full border-2 border-white/20 p-3 bg-shifter/40 text-white font-bold flex items-center justify-center"
                        >
                            {user.name.split(" ")[0].charAt(0).toUpperCase()}
                        </Link>
                    }
                    <Link
                        to={`${user?.hasUsedFreeConsultation ? "/contact" : "/free-consultation"}`}
                        className={`hover:-translate-x-2 transition-all duration-200 ease-in-out cursor-pointer
                                relative -mr-4 py-2 bg-shifter rounded-l-lg font-medium
                                shadow-md shadow-shifter/30 px-8 pr-10 ${user?.hasUsedFreeConsultation && "px-10 pr-12"}`}
                    >
                        {user?.hasUsedFreeConsultation ? t("contact") : t("freeConsultation")}
                    </Link>
                </div>
            </div>
        </nav>

    )
}


export default Navbar