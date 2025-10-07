import {Link} from "react-router-dom";

const NavbarLink = ({to, label, className}: { to: string; label: string, className?: string; }) => (
    <div className={`flex flex-col gap-0 overflow-clip group w-fit ${className}`}>
        <Link to={to} className="transition-all duration-300 ease-in-out z-10">
            {label}
        </Link>
        <hr className="relative -translate-x-60 group-hover:-translate-x-1/4 border-t-2 rounded-full transition-all duration-300 ease-in-out"/>
    </div>
);

export default NavbarLink;