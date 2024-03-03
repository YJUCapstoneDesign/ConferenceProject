import React, { Component } from 'react';
import 'react-responsive-carousel/lib/styles/carousel.min.css';
import { Carousel } from 'react-responsive-carousel';

export default class Testimonials extends Component {
    render() {
        return (
            <Carousel
                showArrows={true}
                infiniteLoop={true}
                showThumbs={false}
                showStatus={false}
                autoPlay={true}
                interval={6100}
            >
                <div>
                    <img src="/img/messi.jpg" />
                    <div className="myCarousel">
                        <h3>messi</h3>
                        <h4>Soccer Player</h4>
                        <p>축구선수 메시</p>
                    </div>
                </div>

                <div>
                    <img src="/img/ronaldo.jpg" />
                    <div className="myCarousel">
                        <h3>ronaldo</h3>
                        <h4>Soccer Player</h4>
                        <p>축구선수 알나스르두</p>
                    </div>
                </div>

                <div>
                    <img src="/img/황희찬.jpg" />
                    <div className="myCarousel">
                        <h3>황희찬</h3>
                        <h4>Soccer Player</h4>
                        <p>Korean guy</p>
                    </div>
                </div>
            </Carousel>
        );
    }
}
