import ShifterLogo from "../assets/Shifter-S2W-White-Transparent.png";
import {Link} from "react-router-dom";

function Footer() {
    return (
        <footer className="px-4 pt-4">
            <div className="bg-dark-blue flex justify-between rounded-lg px-50 py-20">
                <img src={ShifterLogo} alt="Shifter - Business Consulting, Mentoring & Online Courses Logo"
                     className="w-60 "
                />

                <div>
                    <div className="flex gap-4 text-lg text-white">
                        <div className="flex flex-col gap-0 overflow-clip p-1 group">
                            <Link to="/courses" className="transition-all
                                    duration-300 ease-in-out z-10">Courses</Link>
                            <hr className="relative -left-30 group-hover:-left-4 border-t-2
                                    rounded-full transition-all duration-300 ease-in-out"/>
                        </div>
                        <div className="flex flex-col gap-0 overflow-clip p-1 group">
                            <Link to="/mentoring" className="transition-all
                                    duration-300 ease-in-out z-10">Mentoring</Link>
                            <hr className="relative -left-30 group-hover:-left-4 border-t-2
                                    rounded-full transition-all duration-300 ease-in-out"/>
                        </div>
                        <div className="flex flex-col gap-0 overflow-clip p-1 group">
                            <Link to="/academies" className="transition-all
                                    duration-300 ease-in-out z-10">Academies</Link>
                            <hr className="relative -left-30 group-hover:-left-4 border-t-2
                                    rounded-full transition-all duration-300 ease-in-out"/>
                        </div>
                        <div className="flex flex-col gap-0 overflow-clip p-1 group">
                            <Link to="/about" className="transition-all
                                    duration-300 ease-in-out z-10">About</Link>
                            <hr className="relative -left-30 group-hover:-left-4 border-t-2
                                    rounded-full transition-all duration-300 ease-in-out"/>
                        </div>
                        <div className="flex flex-col gap-0 overflow-clip p-1 group">
                            <Link to="/profile" className="transition-all
                                    duration-300 ease-in-out z-10">Profile</Link>
                            <hr className="relative -left-30 group-hover:-left-4 border-t-2
                                    rounded-full transition-all duration-300 ease-in-out"/>
                        </div>
                    </div>
                </div>
            </div>
            <h6 className="text-sm my-2 font-light text-gray-600">
                &copy; {new Date().getFullYear()} Shifter. All rights reserved.
            </h6>
        </footer>
    )
}

export default Footer;