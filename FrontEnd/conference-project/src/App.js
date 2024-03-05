import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import HomeContent from './Component/home/Home';
import AboutContent from './Component/about-in/AboutPage';
import ServiceContent from './Component/service-in/OurService';
import NoticeContent from './Component/notice-in/NoticePage';
import PriceContent from './Component/price/Price';
import LoginContent from './Component/login/Login';
import SignupContent from './Component/signup/Signup';
import Adminpage from './Component/admin/adminpage';
import PricePage from './Component/price/Price';

import NoticePage from './Component/notice/Notice';

function App() {
    return (
        <BrowserRouter>
            <div className="App">
                <Routes>
                    <Route path="/" element={<HomeContent />} />
                    <Route path="/About" element={<AboutContent />} />
                    <Route path="/Service" element={<ServiceContent />} />
                    <Route path="/Notice" element={<NoticeContent />} />
                    <Route path="/Price" element={<PriceContent />} />
                    <Route path="/Login" element={<LoginContent />} />
                    <Route path="/Signup" element={<SignupContent />} />
                    <Route path="/Notice" element={<NoticePage />} />
                    <Route path="/Admin" element={<Adminpage />} />
                    <Route path="/Price" element={<PricePage />} />
                </Routes>
            </div>
        </BrowserRouter>
    );
}

export default App;
