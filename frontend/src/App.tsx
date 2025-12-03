import {Route, Routes, useLocation, useNavigate, useParams} from "react-router-dom";
import Home from "./pages/Home.tsx";
import Navbar from "./layout/Navbar.tsx";
import Login from "./pages/Login.tsx";
import Register from "./pages/Register.tsx";
import Courses from "./pages/Courses.tsx";
import {useEffect} from "react";
import CourseDetails from "./pages/CourseDetails.tsx";
import {ToastContainer} from 'react-toastify';
import Profile from "./pages/Profile.tsx";
import Learn from "./pages/Learn.tsx";
import PublicOnlyRoute from "./components/routeProtectors/PublicOnlyRoute.tsx";
import UserOnlyRoute from "./components/routeProtectors/UserOnlyRoute.tsx";
import {useAuthContext} from "./context/AuthContext.tsx";
import AdminNavbar from "./admin/layout/AdminNavbar.tsx";
import Admin from "./admin/Admin.tsx";
import AdminAddCourse from "./admin/pages/AdminAddCourse.tsx";
import CourseLearn from "./pages/CourseLearn.tsx";
import About from "./pages/About.tsx";
import Contact from "./pages/Contact.tsx";
import Mentoring from "./pages/Mentoring.tsx";
import Consulting from "./pages/Consulting.tsx";
import Academies from "./pages/Academies.tsx";
import Personalize from "./pages/Personalize.tsx";
import OAuth2RedirectHandler from "./api/OAuth2RedirectHandler.tsx";
import FooterNew from "./layout/FooterNew.tsx";
import FooterSmall from "./layout/FooterSmall.tsx";
import FreeConsultation from "./pages/FreeConsultation.tsx";
import LanguageToggle from "./layout/LanguageToggle.tsx";
import AdminTranslateCourse from "./admin/pages/AdminTranslateCourse.tsx";
import LoadingScreen from "./layout/LoadingScreen.tsx";
import {createNewVerificationTokenApi} from "./api/verificationTokenApi.ts";
import AppLoader from "./AppLoader.tsx";
import {useUserContext} from "./context/UserContext.tsx";
import i18n from "i18next";
import {getLangFromPath} from "./utils/langUrl.ts";
import AdminCourses from "./admin/pages/AdminCourses.tsx";
import ComingSoon from "./pages/ComingSoon.tsx";

function LayoutWrapper() {
    const { accessToken } = useAuthContext();
    const { user } = useUserContext();
    const location = useLocation();
    const navigate = useNavigate()

    const { lang: langPrefix } = useParams<{ lang: string }>(); // Use 'lang' from the parent route

    const pathSegments = location.pathname.split('/');
    const relativePath = `/${pathSegments.slice(2).join('/')}`;

    const hideLayout =
        relativePath === "/login" ||
        relativePath === "/register" ||
        relativePath === "/welcome" ||
        relativePath.startsWith("/learn/");
    const showSmallFooter =
        relativePath === "/profile" ||
        relativePath === "/courses" ||
        relativePath === "/learn" ||
        relativePath === "/mentoring" ||
        relativePath === "/consulting" ||
        relativePath === "/academies" ||
        relativePath === "/contact" ||
        relativePath === "/free-consultation";

    useEffect(() => {
        if (user && !user.isProfileComplete && accessToken && !location.pathname.startsWith("/welcome")) {
            createNewVerificationTokenApi(accessToken)
                .then(token => {
                    navigate(`/${langPrefix}/welcome?token=${token}`)
                })
                .catch(err => {
                    console.error("Error creating a new verification token api: ", err)
                })
        }
    }, [user, accessToken, navigate, relativePath, langPrefix, location.pathname])


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
                    <Personalize/>
                }/>

                <Route path="/" element={<Home/>}/>

                <Route path="/about" element={<About/>}/>

                <Route path="/mentoring" element={
                    <UserOnlyRoute>
                        <Mentoring/>
                    </UserOnlyRoute>
                }/>

                <Route path="/consulting" element={
                    <UserOnlyRoute>
                        <Consulting/>
                    </UserOnlyRoute>
                }/>

                <Route path="/academies" element={
                    <UserOnlyRoute>
                        <Academies/>
                    </UserOnlyRoute>
                }/>

                <Route path="/courses" element={<ComingSoon />}/>
                {/*<Route path="/courses/:courseId/:courseTitle" element={<CourseDetails/>}/>*/}

                <Route path="/contact" element={
                    <UserOnlyRoute>
                        <Contact/>
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

                {/*<Route path="/learn" element={*/}
                {/*    <UserOnlyRoute>*/}
                {/*        <Learn/>*/}
                {/*    </UserOnlyRoute>*/}
                {/*}/>*/}
                {/*<Route path="/learn/:courseId/:courseTitle" element={*/}
                {/*    <UserOnlyRoute>*/}
                {/*        <CourseLearn/>*/}
                {/*    </UserOnlyRoute>*/}
                {/*}/>*/}

            </Routes>
            {(!hideLayout) && (showSmallFooter ? <FooterSmall/> : <FooterNew/>)}
        </>
    );
}

function ScrollToTop() {
    const location = useLocation();

    // Get the current language prefix from the path (e.g., 'en' or 'mk')
    const langPrefix = getLangFromPath(location.pathname);

    // Get the path excluding the language prefix.
    const contentPath = location.pathname.replace(new RegExp(`^/${langPrefix}`), '') || '/';

    useEffect(() => {
        window.scrollTo(0, 0);
    }, [contentPath]);

    return null;
}

const VALID_LANGS = ['en', 'mk'];

export function UserRoutesWithLang() {
    const { lang } = useParams<{ lang: string }>();
    const navigate = useNavigate();
    const location = useLocation();

    const langUpper = lang?.toUpperCase();

    useEffect(() => {
        if (!lang || !VALID_LANGS.includes(lang.toLowerCase())) {
            const defaultLang = i18n.language.toLowerCase() || 'en';
            navigate(`/${defaultLang}`, { replace: true });
            return;
        }

        if (langUpper && i18n.language !== langUpper) {
            i18n.changeLanguage(langUpper);
        }
    }, [lang, langUpper, navigate, location.pathname]);

    return <LayoutWrapper />;
}

export default function App() {
    const { authChecked, accessToken } = useAuthContext();
    const { user } = useUserContext()
    const location = useLocation();
    const navigate = useNavigate();

    const urlLang = getLangFromPath(location.pathname);
    const currentI18nLang = i18n.language.toLowerCase();

    useEffect(() => {
        if (!urlLang && location.pathname !== "/oauth2/redirect" && !location.pathname.startsWith("/admin")) {
            const langToRedirect = currentI18nLang || 'en';
            const newPath = `/${langToRedirect}${location.pathname === '/' ? '' : location.pathname}${location.search}`;
            navigate(newPath, { replace: true });
        }
    }, [urlLang, currentI18nLang, location.pathname, navigate, location.search]);

    useEffect(() => {
        if (user?.isAdmin && !location.pathname.startsWith("/admin")) {
            navigate("/admin", { replace: true });
        }
    }, [user, location.pathname, navigate]);


    if (!authChecked || (accessToken && !user)) {
        return <LoadingScreen />;
    }

    // if (user?.isAdmin) {
    //     return (
    //         <>
    //             <AdminNavbar/>
    //             <Routes>
    //                 <Route path="/admin" element={<Admin/>}/>
    //                 <Route path="/admin/courses" element={<AdminCourses />}/>
    //                 <Route path="/admin/courses/add" element={<AdminAddCourse />}/>
    //                 <Route path="/admin/courses/:courseId/translate" element={<AdminTranslateCourse />}/>
    //             </Routes>
    //         </>
    //     );
    // }


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
            <Routes>
                <Route path="/oauth2/redirect" element={<OAuth2RedirectHandler/>}/>

                <Route path="/:lang/*" element={<UserRoutesWithLang/>}/>

                {/* <Route path="*" element={ <RedirectToDefaultLang /> } /> */}
            </Routes>
            <LanguageToggle/>
        </>
    );
}