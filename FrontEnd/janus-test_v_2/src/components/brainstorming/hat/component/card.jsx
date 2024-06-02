import React from 'react';

export default function Card({ color, title, content }) {
  return (
    <div className="bg-white pb-12 px-6 text-center rounded-md shadow-lg h-full">
      <img src={color} alt={title} className="w-full h-32 object-cover rounded-t-md" />
      <p className='mt-2 text-xl font-semibold'>{title}</p>
      <p className="mt-2 pt-3 text-left text-sm">{content}</p>
    </div>
  );
}