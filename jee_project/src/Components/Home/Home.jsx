import NavBar from "./NavBar.jsx";
import Footer from "./Footer.jsx";
import About from "./About.jsx";
import Header from "./Header.jsx";
import "../../index.css";
import "./Home.css";

const App = () => {
  return (
    <div>
      <div className="up">
        <div className="navbar">
          <NavBar />
        </div>
        <div className="header" style={{ position: "relative", top: "50px" }}>
          <Header />
        </div>
      </div>
      <div>
        <About />
        <Footer />
      </div>
    </div>
  );
};

export default App;
