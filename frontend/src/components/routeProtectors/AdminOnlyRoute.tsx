import type {JSX} from "react";
import {useAuthContext} from "../../context/AuthContext.tsx";
import {Navigate} from "react-router-dom";

const AdminOnlyRoute = ({children}: { children: JSX.Element }) => {
    const {authChecked, role} = useAuthContext();

    if (!authChecked) {
        return null;
    }

    if (role === "EXPERT")
        return children;


    return <Navigate to="/" replace/>;
};

export default AdminOnlyRoute;
