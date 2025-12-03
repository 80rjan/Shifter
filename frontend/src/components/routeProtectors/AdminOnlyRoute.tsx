import type {JSX} from "react";
import {useAuthContext} from "../../context/AuthContext.tsx";
import {Navigate} from "react-router-dom";
import {useUserContext} from "../../context/UserContext.tsx";

const AdminOnlyRoute = ({children}: { children: JSX.Element }) => {
    const {authChecked} = useAuthContext();
    const { user } = useUserContext();

    if (!authChecked) {
        return null;
    }

    if (user?.isAdmin)
        return children;


    return <Navigate to="/" replace/>;
};

export default AdminOnlyRoute;
