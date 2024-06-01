import Chat from './component/chat';
import Card from './component/card';
import React from 'react';

export default function canvas() {
    return (
        <div className="flex h-screen">
            <div className='cards flex'>
                <Card />
                <Card />
                <Card />
                <Card />
                <Card />
                <Card />
            </div>
            <div className='chat'>
                <Chat />
            </div>
        </div>
    );
}