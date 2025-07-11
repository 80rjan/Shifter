import { BrowserRouter as Router, Route, Routes, useLocation } from "react-router-dom";
import Home from "./pages/Home.tsx";
import Navbar from "./layout/Navbar.tsx";
import Footer from "./layout/Footer.tsx";
import Login from "./pages/Login.tsx";
import Register from "./pages/Register.tsx";
import Courses from "./pages/Courses.tsx";
import {useGlobalContext} from "./context/GlobalContext.tsx";
import {useEffect} from "react";
import CourseDetails from "./pages/CourseDetails.tsx";
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import AppLoader from "./AppLoader.tsx";

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
    const {loading} = useGlobalContext();

    if (loading)
        return (
            <div className="absolute inset-0 bg-white/60 backdrop-blur-sm
                        flex flex-col gap-2 items-center justify-start z-10 py-40">
                <div className="w-20 loader"></div>
            </div>
        )

    return (
        <Router>
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
        </Router>
    );
}

export default App;
