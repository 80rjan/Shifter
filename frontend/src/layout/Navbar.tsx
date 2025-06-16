import {Link} from "react-router-dom";
import logo from "../assets/Shifter-S2W-White-Transparent.png"
import {CircleUser} from "lucide-react";

function Navbar() {

    return (
        <nav
            className="-mt-20 sticky top-4 bg-black/30 backdrop-blur-md z-50 w-8/10 mx-auto flex items-center justify-between py-3 px-10 pr-0 rounded-full text-white font-montserrat font-medium overflow-clip">

            {/* Left nav links */}
            <div className="flex gap-8 text-lg items-center">
                <Link to="/courses">Courses</Link>
                <Link to="/mentoring">Mentoring</Link>
                <Link to="/about">About</Link>
            </div>

            {/* Centered Logo */}
            <Link
                to="/"
                className="absolute left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2"
            >
                <img src={logo} alt="Shifter Logo" width={120}/>
            </Link>

            {/* Right side icons and button */}
            <div className="flex gap-4 text-lg items-center">
                <CircleUser size={32} strokeWidth={1.5}/>
                <Link to="/mentoring"
                      className="hover:mr-0 transition-all duration-200 ease-in-out cursor-pointer
                      relative -mr-4 px-5 pr-9 py-1 bg-shifter rounded-l-full font-medium shadow-md shadow-shifter/30"
                >Free Consultation</Link>
            </div>
        </nav>

    )
}

export default Navbar