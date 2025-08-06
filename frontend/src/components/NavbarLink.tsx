import {Link} from "react-router-dom";

const NavbarLink = ({to, label, className}: { to: string; label: string, className?: string; }) => (
    <div className={`flex flex-col gap-0 overflow-clip p-1 group ${className}`}>
        <Link to={to} className="transition-all duration-300 ease-in-out z-10">
            {label}
        </Link>
        <hr className="relative -left-30 group-hover:-left-6 border-t-2 rounded-full transition-all duration-300 ease-in-out"/>
    </div>
);

export default NavbarLink;