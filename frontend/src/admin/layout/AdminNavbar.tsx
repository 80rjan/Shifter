import {Link} from "react-router-dom";

import logo from "../../../public/Shifter-S2W-Transparent.png"
import {useAuthContext} from "../../context/AuthContext.tsx";
import AdminNavbarLink from "../components/AdminNavbarLink.tsx";

function AdminNavbar() {
    const {logout} = useAuthContext();

    return (
        <nav className="flex items-center justify-between px-horizontal py-2 z-10 shadow-md shadow-black/10">
            <Link to="/">
                <img
                    src={logo} alt="Shifter Logo"
                    className="h-16 w-auto"
                />
            </Link>

            <div className="flex items-center gap-20">
                <ul className="flex gap-8 items-center text-lg font-medium">
                    <li>
                        <AdminNavbarLink
                            to="/admin/courses"
                            label="Courses"
                        />
                    </li>
                    <li>
                        <AdminNavbarLink
                            to="/admin/courses/add"
                            label="Add Course"
                        />
                    </li>
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