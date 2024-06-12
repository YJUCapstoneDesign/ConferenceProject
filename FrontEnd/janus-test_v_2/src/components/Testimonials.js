import React from 'react'
import user1 from './images/user1.jpeg'
import user2 from './images/user2.jpeg'
import user3 from './images/user3.jpeg'
import './css/Testimonials.css'

const Testimonials = () => {
    return (
        <div className='testimonials' id='notice'>
            <div className='container'>
                <h2>Developer Note</h2>
                <span className='line'></span>
                <div className='content'>
                    <div className='card h-72'>
                        <p className='pb-8'>We're excited to share some fantastic updates to our video conferencing platform that we believe will make your virtual meetings smoother and more productive than ever before. Here’s a quick overview of what’s new</p>
                        <p><span>Johnson.M.J.</span></p>
                        <p>Director of "Financial Times"</p>
                    </div>
                    <div className='card h-72'>
                        <p className='pb-8'>We're excited to share some fantastic updates to our video conferencing platform that we believe will make your virtual meetings smoother and more productive than ever before. Here’s a quick overview of what’s new</p>
                        <p><span>Carol Harper</span></p>
                        <p>Director at Risktec Solutions Ltd</p>
                    </div>
                    <div className='card h-72'>
                        <p className='pb-8'>We're excited to share some fantastic updates to our video conferencing platform that we believe will make your virtual meetings smoother and more productive than ever before. Here’s a quick overview of what’s new</p>
                        <p><span>Snow.J.R.</span></p>
                        <p>Managing Director of BPW Global</p>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Testimonials