import {Navigate} from "react-router-dom";
import type {JSX} from "react";
import {useAuthContext} from "../../context/AuthContext.tsx";

const UserOnlyRoute = ({ children }: {
    children: JSX.Element;
}) => {
    const { user } = useAuthContext();

    if (!user)
        return <Navigate to="/login" replace />;

    return children;
};

export default UserOnlyRoute;