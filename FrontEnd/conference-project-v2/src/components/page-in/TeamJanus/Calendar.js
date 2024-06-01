import React, { useState } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import '../css/Calendar.css';

function MyCalendar() {
  const [value, setValue] = useState(new Date());

  return (
      <Calendar
        onChange={setValue}
        value={value}
        minDetail="month" 
        maxDetail="month" 
        navigationLabel={null}
        showNeighboringMonth={false} 
        className="w-full text-sm font-bold mx-auto"
      />
  );
}

export default MyCalendar;
