import { jwtDecode } from 'jwt-decode';

interface DecodedToken {
    sub: string; // email
    userId: number;
    role: 'EXPERT' | 'USER';
    exp: number;
    iat: number;
}

export const getDecodedToken = (token: string | null): DecodedToken | null => {
    if (!token) return null;

    try {
        return jwtDecode<DecodedToken>(token);
    } catch (error) {
        console.error('Invalid token:', error);
        return null;
    }
};

export const getUserRole = (token: string | null): 'EXPERT' | 'USER' | null => {
    const decoded = getDecodedToken(token);
    return decoded?.role || null;
};

export const isExpert = (token: string | null): boolean => {
    return getUserRole(token) === 'EXPERT';
};

export const getUserId = (token: string | null): number | null => {
    const decoded = getDecodedToken(token);
    return decoded?.userId || null;
};

export const isTokenExpired = (token: string | null): boolean => {
    const decoded = getDecodedToken(token);
    if (!decoded) return true;

    const currentTime = Date.now() / 1000;
    return decoded.exp < currentTime;
};