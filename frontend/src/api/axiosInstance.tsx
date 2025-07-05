import axios, { AxiosError, type InternalAxiosRequestConfig } from "axios";

const backendUrl = import.meta.env.VITE_BACKEND_URL;

const createAxiosInstance = (getAccessToken: () => string | null) => {
    const axiosInstance = axios.create({
        baseURL: backendUrl,
        withCredentials: true,
    });

    // Attach access token dynamically before each request
    axiosInstance.interceptors.request.use(
        (config: InternalAxiosRequestConfig) => {
            const token = getAccessToken();
            if (token) {
                config.headers = config.headers || {};
                config.headers.Authorization = `Bearer ${token}`;
            }
            return config;
        },
        (error: AxiosError) => {
            return Promise.reject(error);
        }
    );

    // Response interceptor: handle 401 by refreshing token
    axiosInstance.interceptors.response.use(
        (response) => response,
        async (error: AxiosError) => {
            const originalRequest = error.config as InternalAxiosRequestConfig & { _retry?: boolean };

            if (
                error.response?.status === 401 &&
                !originalRequest._retry
            ) {
                originalRequest._retry = true;

                try {
                    // Call refresh endpoint; no token needed in body, cookie sent automatically
                    const res = await axiosInstance.post("/api/auth/refresh");

                    // The new access token is in res.data.accessToken
                    const newAccessToken = res.data.accessToken;

                    // Optionally you can call a function here to update your React global state
                    // But since this is outside React, you may want to pass an "onTokenRefresh" callback
                    // For example: onTokenRefresh(newAccessToken);

                    // Update header and retry original request
                    originalRequest.headers = originalRequest.headers || {};
                    originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;

                    return axiosInstance(originalRequest);
                } catch (refreshError) {
                    console.error("Token refresh failed:", refreshError);
                    // Optional: trigger logout or token cleanup via a callback
                }
            }

            return Promise.reject(error);
        }
    );

    return axiosInstance;
};

export default createAxiosInstance;
