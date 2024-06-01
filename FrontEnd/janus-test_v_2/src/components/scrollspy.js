import React from 'react'
import Navbar from './Navbar';
import Hero from './Hero';
import About from './About';
import Testimonials from './Testimonials';
import ServiceContent from './Service';
import Footer from './Footer';

function scrollspy() {
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

export default scrollspy;