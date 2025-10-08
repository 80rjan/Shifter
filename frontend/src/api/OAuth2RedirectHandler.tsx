import { useEffect, useRef } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { useAuthContext } from "../context/AuthContext.tsx";
import {getUserApi} from "./userApi.ts";

export default function OAuth2RedirectHandler() {
    const navigate = useNavigate();
    const { setAccessToken, setUser, setAuthChecked } = useAuthContext();
    const location = useLocation();

    useEffect(() => {
        const params = new URLSearchParams(location.search);
        const token = params.get("token");
        const login = params.get("login") === "true";

        if (token) {
            if (login) {
                getUserApi(token)
                    .then(user => {
                        setUser(user);
                        setAuthChecked(true);
                        setAccessToken(token);
                        navigate("/", { replace: true }); // Use replace to avoid adding to history
                    })
                    .catch(err => console.log("Cannot fetch user: ", err));
            } else {
                navigate(`/welcome?token=${token}`, { replace: true });
            }
        } else {
            console.error("OAuth2 login failed: No token received.");
            navigate("/login?error", { replace: true });
        }
    }, [navigate, setAccessToken]);

    return <div className="h-screen"></div>;
}