import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import HomeContent from "./Component/home/Home";
import AboutContent from "./Component/About";
import ServiceContent from "./Component/Service";
import NoticeContent from "./Component/Notice";
import PriceContent from "./Component/price/Price";
import Nav from "./Component/NavBar";

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <Nav />
        <Routes>
          <Route path="/" element={<HomeContent />} />
          <Route path="/About" element={<AboutContent />} />
          <Route path="/Service" element={<ServiceContent />} />
          <Route path="/Notice" element={<NoticeContent />} />
          <Route path="/Price" element={<PriceContent />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
