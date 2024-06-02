import React from 'react';

export default function Card({ color, title, content }) {
  return (
    <div className="bg-white pb-5 px-10 text-center rounded-md shadow-lg transform-translate-y-20 h-full sm:-translate-y-24 max-w-xs mx-auto text-base">
      <img src={color} alt="Hat" />
      <p className='mt-2'>{title}</p>
      <p className="mt-2 text-left">{content}</p>
    </div>
  );
}
