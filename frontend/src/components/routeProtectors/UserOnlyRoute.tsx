import {Navigate} from "react-router-dom";
import type {JSX} from "react";
import {useUserContext} from "../../context/UserContext.tsx";

const UserOnlyRoute = ({ children }: {
    children: JSX.Element;
}) => {
    const { user } = useUserContext();

    if (!user)
        return <Navigate to="/login" replace />;

    return children;
};

export default UserOnlyRoute;