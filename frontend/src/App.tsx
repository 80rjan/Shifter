import {Route, Routes, useLocation} from "react-router-dom";
import Home from "./pages/Home.tsx";
import Navbar from "./layout/Navbar.tsx";
import Footer from "./layout/Footer.tsx";
import Login from "./pages/Login.tsx";
import Register from "./pages/Register.tsx";
import Courses from "./pages/Courses.tsx";
import {useEffect} from "react";
import CourseDetails from "./pages/CourseDetails.tsx";
import {ToastContainer} from 'react-toastify';
import AppLoader from "./AppLoader.tsx";
import Profile from "./pages/Profile.tsx";
import Dashboard from "./pages/Dashboard.tsx";
import FreeConsultation from "./pages/FreeConsultation.tsx";
import PublicOnlyRoute from "./components/routeProtectors/PublicOnlyRoute.tsx";
import UserOnlyRoute from "./components/routeProtectors/UserOnlyRoute.tsx";
import {useAuthContext} from "./context/AuthContext.tsx";
import AdminNavbar from "./admin/utils/AdminNavbar.tsx";
import Admin from "./admin/Admin.tsx";
import AdminAddCourse from "./admin/pages/AdminAddCourse.tsx";
import CourseLearn from "./pages/CourseLearn.tsx";
import NavbarLearn from "./layout/NavbarLearn.tsx";

function LayoutWrapper() {
    const location = useLocation();
    const isLearn = location.pathname.startsWith("/learn");
    const hideLayout =
        location.pathname === "/login" ||
        location.pathname === "/register" ||
        isLearn;
    const {user, authChecked} = useAuthContext();

    if (!authChecked)
        return null;

    if (user?.isAdmin) {
        return (
            <>
                <AdminNavbar/>
                <Routes>
                    <Route path="/" element={<Admin/>}/>
                    <Route path="/add-course" element={<AdminAddCourse/>}/>
                    <Route path="/analytics" element={<p>Analytics</p>}/>
                </Routes>
            </>
        )
    }


    return (
        <>
            {!hideLayout && <Navbar/>}
            {isLearn && <NavbarLearn/>}
            <Routes>
                <Route path="/login" element={
                    <PublicOnlyRoute>
                        <Login/>
                    </PublicOnlyRoute>
                }/>
                <Route path="/register" element={
                    <PublicOnlyRoute>
                        <Register/>
                    </PublicOnlyRoute>
                }/>

                <Route path="/" element={<Home/>}/>
                <Route path="/courses" element={<Courses/>}/>
                <Route path="/courses/:courseId/:courseTitle" element={<CourseDetails/>}/>

                <Route path="/free-consultation" element={
                    <UserOnlyRoute>
                        <FreeConsultation/>
                    </UserOnlyRoute>
                }/>

                <Route path="/profile" element={
                    <UserOnlyRoute>
                        <Profile/>
                    </UserOnlyRoute>
                }/>

                <Route path="/learn" element={
                    <UserOnlyRoute>
                        <Dashboard/>
                    </UserOnlyRoute>
                }/>
                <Route path="/learn/:courseId/:courseTitle" element={
                    <UserOnlyRoute>
                        <CourseLearn/>
                    </UserOnlyRoute>
                }/>

            </Routes>
            {!hideLayout && <Footer/>}
        </>
    );
}

function ScrollToTop() {
    const {pathname} = useLocation();

    useEffect(() => {
        window.scrollTo(0, 0);
    }, [pathname]);

    return null;
}

function App() {

    return (
        <>
            <AppLoader/>
            <ScrollToTop/>
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
