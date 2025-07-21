import {Link} from "react-router-dom";
import logo from "../../public/Shifter-S2W-White-Transparent.png"
import {useGlobalContext} from "../context/GlobalContext.tsx";

function Navbar() {
    const {user} = useGlobalContext();

    return (
        <nav
            className=" fixed top-2 z-50 w-11/12 left-1/2 -translate-x-1/2 flex items-center justify-between py-3 px-10 pr-0 rounded-full
            border-3 border-white/30 bg-black/50 backdrop-blur-md text-white font-light overflow-clip"
        >
            {/* Left nav links */}
            <div className="flex w-36/100 justify-between text-lg items-center">
                {/* Link group */}
                <LinkGroup to="/courses" label="Courses"/>
                <LinkGroup to="/mentoring" label="Mentoring"/>
                <LinkGroup to="/academies" label="Academies"/>
            </div>

            {/* Centered Logo (NO ABSOLUTE!) */}
            <div className="flex justify-center items-center w-fit px-20">
                <Link to="/">
                    <img src={logo} alt="Shifter Logo" className="h-14"/>
                </Link>
            </div>

            {/* Right nav links + profile */}
            <div className="flex w-36/100 justify-between text-lg items-center gap-6">
                <LinkGroup to="/about" label="About"/>
                {user ? (
                    <>
                        <LinkGroup to="/dashboard" label="Dashboard"/>
                        <div className="flex gap-4 items-center">
                            <Link
                                to="/profile"
                                className="hover:bg-shifter transition-all duration-200 ease-in-out cursor-pointer
                                h-full aspect-square rounded-full border-2 border-white/20 p-3 bg-shifter/40 text-white font-bold flex items-center justify-center"
                            >
                                {user.name.split(" ")[0].charAt(0).toUpperCase()}
                            </Link>
                            <Link
                                to="/free-consultation"
                                className="hover:-translate-x-2 transition-all duration-200 ease-in-out cursor-pointer
                                relative -mr-4 px-6 pr-9 py-2 bg-shifter rounded-l-lg font-medium
                                shadow-md shadow-shifter/30"
                            >Free Consultation
                            </Link>
                        </div>
                    </>
                ) : (
                    <Link to="/login"
                          className="hover:-translate-x-4 transition-all duration-200 ease-in-out cursor-pointer
                              relative -mr-4 px-6 pr-9 py-2 bg-shifter rounded-l-lg font-medium
                              shadow-md shadow-shifter/30"
                    >Login / Register</Link>
                )}
            </div>
        </nav>

    )
}

const LinkGroup = ({to, label}: { to: string; label: string }) => (
    <div className="flex flex-col gap-0 overflow-clip p-1 group">
        <Link to={to} className="transition-all duration-300 ease-in-out z-10">
            {label}
        </Link>
        <hr className="relative -left-30 group-hover:-left-6 border-t-2 rounded-full transition-all duration-300 ease-in-out"/>
    </div>
);


export default Navbar