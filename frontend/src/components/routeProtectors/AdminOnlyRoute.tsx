import type {JSX} from "react";
import {useAuthContext} from "../../context/AuthContext.tsx";
import {Navigate} from "react-router-dom";

const AdminOnlyRoute = ({children}: { children: JSX.Element }) => {
    const {user, authChecked} = useAuthContext();

    if (!authChecked) {
        return null;
    }

    if (user?.isAdmin)
        return children;


    return <Navigate to="/" replace/>;
};

export default AdminOnlyRoute;
