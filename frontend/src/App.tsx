import './App.css'
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import Home from "./pages/Home.tsx";
import Navbar from "./layout/Navbar.tsx";

function App() {

  return (
      <>
          <Router>
              <Navbar />
              <Routes>
                  <Route path="/" element={<Home />} />
              </Routes>
          </Router>
      </>
  )
}

export default App
