import React from 'react';

export default function Card({ color, title, content, hoverTitle, hoverContent }) {
  const contentLines = content.split('\n').map((line, index) => (
    <p key={index} className="text-left text-sm">
      {line}
    </p>
  ));

  return (
    <div className="bg-white h-64 w-64 pb-12 px-6 text-center rounded-md shadow-lg transition duration-300 hover:bg-gray-300 hover:text-white group">
      <img src={color} alt={title} className="w-full h-32 object-cover rounded-t-md" />
      <div className="group-hover:hidden">
        <p className='mt-2 text-xl font-semibold'>{title}</p>
        <div className="mt-2 pt-3">
          {contentLines}
        </div>
      </div>
      <div className="hidden group-hover:block">
        <p className='mt-2 text-xl font-semibold'>{hoverTitle}</p>
        <p className="mt-2 pt-3 text-left text-sm">{hoverContent}</p>
      </div>
    </div>
  );
}