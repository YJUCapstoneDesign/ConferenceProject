import React from 'react'
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Scrollspy from './components/scrollspy';
import PassWord from './components/password';
import OurService from './components/page-in/OurService';
import Signin from './components/page-in/signin';
import Signup from './components/page-in/signup';

// App
function App() {
  return (
    <BrowserRouter>
            <div className="App"> 
                <Routes>
                    <Route path="/" element={<Scrollspy />} />
                    <Route path="/Pass" element={<PassWord />} />
                    <Route path="/OurService" element={<OurService />} />
                    <Route path="Login" element={<Signin/>} />
                    <Route path='signup' element={<Signup />} />
                </Routes>
            </div>
    </BrowserRouter>
  );
}

export default App;