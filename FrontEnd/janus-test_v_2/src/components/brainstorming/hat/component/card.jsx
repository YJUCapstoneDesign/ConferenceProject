import React from 'react';

export default function Card({ color, title, content, hoverTitle, hoverContent }) {
  const contentLines = content.split('\n').map((line, index) => (
    <p key={index} className="text-left text-xs sm:text-sm">
      {line}
    </p>
  ));

  return (
    <div className="bg-white h-48 w-56 sm:h-56 sm:w-56 md:h-64 md:w-64 lg:h-72 lg:w-72 pb-6 sm:pb-8 md:pb-10 lg:pb-12 px-4 sm:px-6 text-center rounded-md shadow-lg transition duration-300 hover:bg-gray-300 hover:text-white group">
      <img src={color} alt={title} className="w-full h-24 sm:h-28 md:h-32 lg:h-36 object-cover rounded-t-md" />
      <div className="group-hover:hidden">
        <p className='mt-2 text-lg sm:text-xl md:text-2xl font-semibold'>{title}</p>
        <div className="mt-2 pt-2 sm:pt-3">
          {contentLines}
        </div>
      </div>
      <div className="hidden group-hover:block">
        <p className='mt-2 text-lg sm:text-xl md:text-2xl font-semibold'>{hoverTitle}</p>
        <p className="mt-2 pt-2 sm:pt-3 text-left text-xs sm:text-sm">{hoverContent}</p>
      </div>
    </div>
  );
}
