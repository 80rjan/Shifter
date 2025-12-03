import type {JSX} from "react";
import { Navigate } from "react-router-dom";
import {useUserContext} from "../../context/UserContext.tsx";
import {useTranslation} from "react-i18next";

const PublicOnlyRoute = ({ children }: { children: JSX.Element }) => {
    const { user } = useUserContext();
    const { i18n } = useTranslation();

    if (user) return <Navigate to={`/${i18n.language.toLowerCase()}`} replace />;

    return children;
};

export default PublicOnlyRoute;
