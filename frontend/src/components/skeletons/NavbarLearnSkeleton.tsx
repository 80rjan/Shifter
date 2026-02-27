

import logo from "../../assets/images/Shifter-S2W-White-Transparent.png";
import NavbarLink from "../links/NavbarLink.tsx";
import {LocalizedLink} from "../links/LocalizedLink.tsx";

function NavbarLearnSkeleton() {
    return (
        <nav className="flex justify-between items-center bg-black w-full py-2 px-8 border-b-2 border-white/60">
            <div className="flex gap-4 items-center text-xl text-white">
                <LocalizedLink to="/">
                    <img src={logo} alt="Shifter Logo" className="h-12"/>
                </LocalizedLink>
                <div className="w-[1px] bg-white/40 self-stretch my-2"/>

                {/* Course title skeleton */}
                <div className="h-6 bg-gray-300 rounded w-48 animate-pulse"></div>

                {/* Star rating skeleton */}
                <div className="flex gap-1">
                    {[1, 2, 3, 4, 5].map((star) => (
                        <div key={star} className="w-4 h-4 bg-gray-300 rounded animate-pulse"></div>
                    ))}
                </div>
            </div>

            <div className="flex gap-4">
                <NavbarLink className="w-fit text-white"
                            to={"/learn"} label={"Learn"}/>
                <NavbarLink className="w-fit text-white"
                            to={"/"} label={"Return Home"}/>
            </div>
        </nav>
    );
}

export default NavbarLearnSkeleton;