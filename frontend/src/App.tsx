import { Route, Routes, useLocation } from "react-router-dom";
import Home from "./pages/Home.tsx";
import Navbar from "./layout/Navbar.tsx";
import Footer from "./layout/Footer.tsx";
import Login from "./pages/Login.tsx";
import Register from "./pages/Register.tsx";
import Courses from "./pages/Courses.tsx";
import {useEffect} from "react";
import CourseDetails from "./pages/CourseDetails.tsx";
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import AppLoader from "./AppLoader.tsx";
import Profile from "./pages/Profile.tsx";

function LayoutWrapper() {
    const location = useLocation();
    const hideLayout = location.pathname === "/login" || location.pathname === "/register"; // You can add more paths like || location.pathname === "/signup"

    return (
        <>
            {!hideLayout && <Navbar />}
            <Routes>
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />

                <Route path="/" element={<Home />} />
                <Route path="/profile" element={<Profile />} />
                <Route path="/courses" element={<Courses />} />
                <Route path="/courses/:courseId/:courseTitle" element={<CourseDetails />} />
            </Routes>
            {!hideLayout && <Footer />}
        </>
    );
}

function ScrollToTop() {
    const { pathname } = useLocation();

    useEffect(() => {
        window.scrollTo(0, 0);
    }, [pathname]);

    return null;
}

function App() {

    return (
        <>
            <AppLoader />
            <ScrollToTop />
            <ToastContainer
                position="top-right"
                autoClose={3000}
                hideProgressBar={false}
                newestOnTop
                closeOnClick
                pauseOnHover
            />
            <LayoutWrapper/>
        </>
    );
}

export default App;
