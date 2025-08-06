import {Link} from "react-router-dom";
import logo from "../../../public/Shifter-S2W-Transparent.png"
import NavbarLink from "../../components/NavbarLink.tsx";
import {useAuthContext} from "../../context/AuthContext.tsx";

function AdminNavbar() {
    const {logout} = useAuthContext();

    return (
        <nav className="flex items-center justify-between px-horizontal-sm py-2 z-10 shadow-md shadow-black/10">
            <Link to="/">
                <img
                    src={logo} alt="Shifter Logo"
                    className="h-16 w-auto"
                />
            </Link>

            <div className="flex items-center gap-20">
                <ul className="flex gap-8 items-center text-lg font-medium">
                    <li><NavbarLink
                        to="/add-course"
                        label="Add Course"
                    /></li>
                    <li><NavbarLink
                        to="/analytics"
                        label="Analytics"
                    /></li>
                </ul>

                <button
                    onClick={logout}
                    className="hover:bg-[#F00]/60 px-8 py-2 rounded-sm hover:text-white transition-all duration-300 ease-in-out
                    text-[#F00] text-lg font-medium cursor-pointer"
                >
                    Log Out
                </button>
            </div>
        </nav>
    )
}

export default AdminNavbar;