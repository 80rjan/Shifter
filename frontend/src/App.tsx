import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import Home from "./pages/Home.tsx";
import Navbar from "./layout/Navbar.tsx";
import Footer from "./layout/Footer.tsx";

function App() {

  return (
      <>
          <Router>
              <Navbar />
              <Routes>
                  <Route path="/" element={<Home />} />
              </Routes>
              <Footer />
          </Router>
      </>
  )
}

export default App
