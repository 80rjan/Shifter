import {Link} from "react-router-dom";
import logo from "../../public/Shifter-S2W-White-Transparent.png"
import {useGlobalContext} from "../context/GlobalContext.tsx";

function Navbar() {
    const {user, logout} = useGlobalContext();

    return (
        <nav
            className="-mt-20 sticky top-2 border-3 border-white/30 bg-black/50 backdrop-blur-md
            z-50 w-85/100 mx-auto flex items-center justify-between py-3 px-10 pr-0 rounded-full
            text-white font-light overflow-clip ">

            {/* Left nav links */}
            <div className="flex gap-20 text-lg items-center">
                <div className="flex flex-col gap-0 overflow-clip p-1 group">
                    <Link to="/courses" className=" transition-all
                    duration-300 ease-in-out z-10">Courses</Link>
                    <hr className="relative -left-30 group-hover:-left-6 border-t-2
                     rounded-full transition-all duration-300 ease-in-out"/>
                </div>
                <div className="flex flex-col gap-0 overflow-clip p-1 group">
                    <Link to="/mentoring" className=" transition-all
                    duration-300 ease-in-out z-10">Mentoring</Link>
                    <hr className="relative -left-30 group-hover:-left-6 border-t-2
                     rounded-full transition-all duration-300 ease-in-out"/>
                </div>
                <div className="flex flex-col gap-0 overflow-clip p-1 group">
                    <Link to="/academies" className=" transition-all
                    duration-300 ease-in-out z-10">Academies</Link>
                    <hr className="relative -left-30 group-hover:-left-6 border-t-2
                     rounded-full transition-all duration-300 ease-in-out"/>
                </div>
            </div>

            {/* Centered Logo */}
            <Link
                to="/"
                className="absolute left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2"
            >
                <img src={logo} alt="Shifter Logo" width={160}/>
            </Link>

            {/* Right side icons and button */}
            <div className="flex gap-12 text-lg items-center">

                <div className="flex flex-col gap-0 overflow-clip p-1 group">
                    <Link to="/about" className=" transition-all
                    duration-300 ease-in-out z-10">About</Link>
                    <hr className="relative -left-30 group-hover:-left-4 border-t-2
                     rounded-full transition-all duration-300 ease-in-out"/>
                </div>
                {
                    user ? (
                        <>
                            <div className="flex flex-col gap-0 overflow-clip p-1 group">
                                <Link to="/dashboard" className=" transition-all
                                    duration-300 ease-in-out z-10 cursor-pointer">Dashboard</Link>
                                <hr className="relative -left-30 group-hover:-left-4 border-t-2
                                    rounded-full transition-all duration-300 ease-in-out"/>
                            </div>
                            <div className="flex gap-6 items-center">
                                <Link
                                    to="/profile"
                                    className="hover:bg-shifter transition-all duration-200 ease-in-out cursor-pointer
                                    h-full aspect-square rounded-full border-2 border-white/20 p-3 bg-shifter/40 text-white font-bold flex items-center justify-center">
                                    {user.name.split(" ")[0].charAt(0).toUpperCase()}
                                </Link>
                                <div onClick={logout}
                                     className="hover:-translate-x-4 transition-all duration-200 ease-in-out cursor-pointer
                                      relative -mr-4 px-6 pr-9 py-2 bg-shifter rounded-l-lg font-medium
                                      shadow-md shadow-shifter/30"
                                >Free Consultation
                                </div>
                            </div>
                        </>
                    ) : (
                        <Link to="/login"
                              className="hover:-translate-x-4 transition-all duration-200 ease-in-out cursor-pointer
                              relative -mr-4 px-6 pr-9 py-2 bg-shifter rounded-l-lg font-medium
                              shadow-md shadow-shifter/30"
                        >Login / Register</Link>
                    )
                }
            </div>
        </nav>

    )
}

export default Navbar