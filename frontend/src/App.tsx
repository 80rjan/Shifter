import { BrowserRouter as Router, Route, Routes, useLocation } from "react-router-dom";
import Home from "./pages/Home.tsx";
import Navbar from "./layout/Navbar.tsx";
import Footer from "./layout/Footer.tsx";
import Login from "./pages/Login.tsx";
import Register from "./pages/Register.tsx";

function LayoutWrapper() {
    const location = useLocation();
    const hideLayout = location.pathname === "/login" || location.pathname === "/register"; // You can add more paths like || location.pathname === "/signup"

    return (
        <>
            {!hideLayout && <Navbar />}
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
            </Routes>
            {!hideLayout && <Footer />}
        </>
    );
}

function App() {
    return (
        <Router>
            <LayoutWrapper />
        </Router>
    );
}

export default App;
