// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import logo from "../../public/Shifter-S2W-White-Transparent.png";
import {Link, useLocation} from "react-router-dom";
import {fromUrlFormat} from "../utils/toUrlFormat.ts";
import NavbarLink from "../components/NavbarLink.tsx";

function NavbarLearn() {
    const location = useLocation();
    const courseId = location.pathname.split("/")[2] || "";
    const courseTitleEncoded = location.pathname.split("/")[3] || "";
    const courseTitle = fromUrlFormat(courseTitleEncoded)

    return (
        <nav className="flex justify-between items-center bg-black w-full py-2 px-8 border-b-2 border-white/60">
            <div className="flex gap-4 items-center text-xl text-white ">
                <Link to="/learn">
                    <img src={logo} alt="Shifter Logo" className="h-12"/>
                </Link>
                <div className="w-[1px] bg-white/40 self-stretch my-2"/>
                {
                    courseTitle && <Link to={`/courses/${courseId}/${courseTitleEncoded}`}>{courseTitle}</Link>
                }
            </div>

            <div className="flex gap-4">
                <NavbarLink className="w-fit text-white"
                            to={"/learn"} label={"Dashboard"}/>
                <NavbarLink className="w-fit text-white"
                            to={"/"} label={"Return Home"}/>
            </div>
        </nav>
    )
}

export default NavbarLearn;