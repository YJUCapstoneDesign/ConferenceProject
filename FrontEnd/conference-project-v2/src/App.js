import React from 'react'
import Navbar from './components/Navbar';
import Hero from './components/Hero';
import About from './components/About';
import Testimonials from './components/Testimonials';
import ServiceContent from './components/Service';
import Footer from './components/Footer';

// App
function App() {
  return (
    <div>
      <Navbar />
      <Hero />
      <About />
      <ServiceContent />
      <Testimonials />
      <Footer />
    </div>
  );
}

export default App;