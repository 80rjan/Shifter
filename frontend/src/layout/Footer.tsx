
import ShifterLogo from "../../public/Shifter-S2W-White-Transparent.png";
import {useAuthContext} from "../context/AuthContext.tsx";
import LinkedIn from "../assets/icons/LinkedIn.tsx";
import Instagram from "../assets/icons/Instagram.tsx";
import NavbarLink from "../components/links/NavbarLink.tsx";
import {useUserContext} from "../context/UserContext.tsx";

function Footer() {
    const {logout} = useAuthContext();
    const { user } = useUserContext();

    return (
        <footer className="bg-dark-blue border-t-4 border-white/20">
            <div className="flex justify-between px-20 py-20">
                <section className="flex flex-col gap-4 max-w-90">
                    <img src={ShifterLogo} alt="Shifter - Business Consulting, Mentoring & Online Courses Logo"
                         className="w-60 "
                    />
                    <p className="text-white font-light text-left">
                        Practical strategies and hands-on guidance to help you overcome obstacles, unlock new
                        opportunities, and build a thriving business.
                    </p>
                </section>

                <div>
                    <div className="flex gap-20 text-lg text-white">

                        {/*SERVICES*/}
                        <section className="flex flex-col gap-2 text-left font-light text-lg">
                            <h3 className="text-white font-bold text-2xl mb-4">Services</h3>

                            <NavbarLink to={"/mentoring"} label={"Mentoring"}/>
                            <NavbarLink to={"/consulting"} label={"Consulting"}/>
                            <NavbarLink to={"/courses"} label={"Courses"}/>
                            <NavbarLink to={"/academies"} label={"Academies"}/>
                        </section>

                        {/*ABOUT SHIFTER*/}
                        <section className="flex flex-col gap-2 text-left font-light text-lg">
                            <h3 className="text-white font-bold text-2xl mb-4">About Us</h3>

                            <NavbarLink to={"/about"} label={"About Shifter"}/>
                        </section>

                        {/*PROFILE*/}
                        <section className="flex flex-col gap-2 text-left font-light text-lg">
                            <h3 className="text-white font-bold text-2xl mb-4">Profile</h3>

                            {user ?
                                <>
                                    <NavbarLink to={"/profile"} label={"Profile"}/>
                                    <div className="flex flex-col gap-0 overflow-clip group w-fit">
                                        <button
                                            onClick={logout}
                                            className="transition-all duration-300 ease-in-out z-10 cursor-pointer"
                                        >
                                            Log Out
                                        </button>
                                        <hr className="relative -left-30 group-hover:-left-4 border-t-2
                                            rounded-full transition-all duration-300 ease-in-out"/>
                                    </div>
                                </> :
                                <NavbarLink to={"/login"} label={"Log In / Sign Up"}/>
                            }
                        </section>

                        {/*DASHBOARD*/}
                        {
                            user &&
                            <section className="flex flex-col gap-2 text-left font-light text-lg">
                                <h3 className="text-white font-bold text-2xl mb-4">Dashboard</h3>

                                <NavbarLink to={"/profile"} label={"Profile"}/>
                                <NavbarLink to={"/learn"} label={"My Learning"}/>
                            </section>
                        }

                        {/*CONTACT*/}
                        <section className="flex flex-col gap-2 text-left font-light text-lg">
                            <h3 className="text-white font-bold text-2xl mb-4">Contact</h3>

                            <NavbarLink to={"/contact"} label={"Contact Us"}/>
                            <hr className="border-t-1 border-white/40 rounded-full" />
                            <p>contact@mail.shifter.com</p>
                            <div className="flex gap-2 items-center">
                                Visit us at:
                                <a
                                    href="http://www.google.com"
                                    target="_blank"
                                    rel="noopener noreferrer"
                                    className=""
                                >
                                    <LinkedIn className="w-6 h-6 text-white"/>
                                </a>
                                <a
                                    href="http://www.google.com"
                                    target="_blank"
                                    rel="noopener noreferrer"
                                    className=""
                                >
                                    <Instagram className="w-7 h-7 text-white"/>
                                </a>
                            </div>
                        </section>
                    </div>
                </div>
            </div>
            <div className="text-sm py-2 font-light text-white/80 border-t-2 border-white/20 w-9/10 mx-auto">
                &copy; {new Date().getFullYear()} Shifter. All rights reserved.
            </div>
        </footer>
    )
}

export default Footer;