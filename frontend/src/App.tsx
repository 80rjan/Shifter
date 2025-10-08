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
import Learn from "./pages/Learn.tsx";
import FreeConsultation from "./pages/FreeConsultation.tsx";
import PublicOnlyRoute from "./components/routeProtectors/PublicOnlyRoute.tsx";
import UserOnlyRoute from "./components/routeProtectors/UserOnlyRoute.tsx";
import {useAuthContext} from "./context/AuthContext.tsx";
import AdminNavbar from "./admin/utils/AdminNavbar.tsx";
import Admin from "./admin/Admin.tsx";
import AdminAddCourse from "./admin/pages/AdminAddCourse.tsx";
import CourseLearn from "./pages/CourseLearn.tsx";
import About from "./pages/About.tsx";
import Contact from "./pages/Contact.tsx";
import Mentoring from "./pages/Mentoring.tsx";
import Consulting from "./pages/Consulting.tsx";
import Academies from "./pages/Academies.tsx";
import Personalize from "./pages/Personalize.tsx";

function LayoutWrapper() {
    const location = useLocation();
    const hideLayout =
        location.pathname === "/login" ||
        location.pathname === "/register" ||
        location.pathname === "/welcome" ||
        location.pathname.startsWith("/learn/");
    const hideFooter =
        location.pathname === "/mentoring" ||
        location.pathname === "/consulting" ||
        location.pathname === "/academies" ||
        location.pathname === "/contact";
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
                <Route path="/welcome" element={
                    <PublicOnlyRoute>
                        <Personalize/>
                    </PublicOnlyRoute>
                }/>

                <Route path="/about" element={<About/>}/>

                <Route path="/" element={<Home/>}/>

                <Route path="/mentoring" element={<Mentoring/>}/>

                <Route path="/consulting" element={<Consulting/>}/>

                <Route path="/academies" element={<Academies/>}/>

                <Route path="/courses" element={<Courses/>}/>
                <Route path="/courses/:courseId/:courseTitle" element={<CourseDetails/>}/>

                <Route path="/contact" element={
                    <UserOnlyRoute>
                        <Contact />
                    </UserOnlyRoute>
                }/>

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
                        <Learn/>
                    </UserOnlyRoute>
                }/>
                <Route path="/learn/:courseId/:courseTitle" element={
                    <UserOnlyRoute>
                        <CourseLearn/>
                    </UserOnlyRoute>
                }/>

            </Routes>
            {(!hideLayout && !hideFooter) && <Footer/>}
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
