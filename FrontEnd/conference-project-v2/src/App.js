import React from 'react'
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Scrollspy from './components/scrollspy';
import PassWord from './components/password';
import OurService from './components/page-in/OurService';

// App
function App() {
  return (
    <BrowserRouter>
            <div className="App"> 
                <Routes>
                    <Route path="/" element={<Scrollspy />} />
                    <Route path="/Pass" element={<PassWord />} />
                    <Route path="/OurService" element={<OurService />} />
                </Routes>
            </div>
    </BrowserRouter>
  );
}

export default App;