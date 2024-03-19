import React from 'react'
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Scrollspy from './components/scrollspy';
import PassWord from './components/password';

// App
function App() {
  return (
    <BrowserRouter>
            <div className="App"> 
                <Routes>
                    <Route path="/" element={<Scrollspy />} />
                    <Route path="/Pass" element={<PassWord />} />
                </Routes>
            </div>
    </BrowserRouter>
  );
}

export default App;