import React from 'react';
import Chat from './component/chat';
import Card from './component/card';
import black from './images/black.png';
import blue from './images/blue.png';
import green from './images/green.png';
import red from './images/red.png';
import white from './images/white.png';
import yellow from './images/yellow.png';

export default function Canvas() {
  const colors = [black, blue, green, red, white, yellow];
  const title = [
    '비판적 사고',
    '사고의 통제',
    '창조적 사고',
    '직관',
    '객관적 사실',
    '긍정적 가치'
  ]
  const descriptions = [
    '경험, 지식, 정보와 맞지 않는 것, 부정확한 것, 위험한 것 등을 논리적으로 제시',
    '객관적, 이성적 판단을 기반으로 사고를 정리하고 요약하여 결론을 제시',
    '기존의 사고에서 벗어나 새로운 시각과 창의적인 아이디어를 제시',
    '감정들에 의해 의견을 제시, 의견에 대한 정당성, 이유, 근거를 제시하지 않음',
    '이미 검증된 정확한 정보를 제시하고 중립적이고 객관성을 유지한 의견을 제시',
    '근거를 기반으로 장점을 찾고 달성 가능한 실행 방안을 제시',
  ];

  return (
    <div className="flex flex-col sm:flex-row sm:overflow-hidden">
      <div className="grid grid-cols-1 mt-24 sm:grid-cols-2 gap-4">
        {colors.map((color, index) => (
          <Card key={index} color={color} title={title[index]} content={descriptions[index]} />
        ))}
      </div>
      <div className='chat w-full sm:w-screen'>
        <Chat />
      </div>
    </div>
  );
}
