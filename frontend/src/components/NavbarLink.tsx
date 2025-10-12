import {Link} from "react-router-dom";

const NavbarLink = ({to, label, className, classNameLine}: { to: string; label: string, className?: string; classNameLine?: string; }) => (
    <div className={`flex flex-col gap-0 overflow-clip group w-fit ${className}`}>
        <Link to={to} className="transition-all duration-300 ease-in-out z-10">
            {label}
        </Link>
        <hr className={`relative -translate-x-60 group-hover:-translate-x-1/4 border-t-2 rounded-full transition-all duration-300 ease-in-out ${classNameLine}`}/>
    </div>
);

export default NavbarLink;