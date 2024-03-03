import React from 'react';
import Testimonials from './Testimonals';
import './news.css';

function NewsContent() {
    return (
        <div className="News">
            <header className="News-header">
                <Testimonials />
            </header>
        </div>
    );
}

export default NewsContent;
