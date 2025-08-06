import type {JSX} from "react";
import { useAuthContext } from "../../context/AuthContext.tsx";
import { Navigate } from "react-router-dom";

const PublicOnlyRoute = ({ children }: { children: JSX.Element }) => {
    const { user, authChecked } = useAuthContext();

    if (!authChecked) {
        return null;
    }

    if (user) return <Navigate to="/" replace />;

    return children;
};

export default PublicOnlyRoute;
