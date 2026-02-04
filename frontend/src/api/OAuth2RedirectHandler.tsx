import { useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { useAuthContext } from "../context/AuthContext.tsx";
import {oAuthLoginApi} from "./authApi.ts";
import {useUserContext} from "../context/UserContext.tsx";
import {useTranslation} from "react-i18next";
import type {Language} from "../models/types/Language.tsx";

export default function OAuth2RedirectHandler() {
    const navigate = useNavigate();
    const { setAccessToken, setAuthChecked } = useAuthContext();
    const { loadUserProfile } = useUserContext();
    const location = useLocation();
    const { i18n } = useTranslation();

    useEffect(() => {
        const params = new URLSearchParams(location.search);
        const token = params.get("token");
        const login = params.get("login") === "true";
        const language = (i18n.language as Language).toLowerCase();

        if (token) {
            if (login) {
                oAuthLoginApi(token)
                    .then(async data => {
                        // TODO: check if because of this i dont need to call refresh token func
                        setAccessToken(data.accessToken);
                        await loadUserProfile(data.accessToken);

                        setAuthChecked(true);
                        navigate(`/${language}`, { replace: true });
                    })
                    .catch(err => console.error("Cannot login user via login provider: ", err));
            } else {
                navigate(`/${language}/welcome?token=${token}`, { replace: true });
            }
        } else {
            console.error("OAuth2 login failed: No token received.");
            navigate("/login?error", { replace: true });
        }
    }, [i18n.language, loadUserProfile, location.search, navigate, setAccessToken, setAuthChecked]);

    return <div className="h-screen"></div>;
}