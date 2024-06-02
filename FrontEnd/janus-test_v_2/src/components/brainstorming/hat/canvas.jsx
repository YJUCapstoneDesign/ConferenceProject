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
  const titles = [
    '비판적 사고',
    '사고의 통제',
    '창조적 사고',
    '직관',
    '객관적 사실',
    '긍정적 가치'
  ];
  const descriptions = [
    '- 논리적\n- 부정적\n- 실패 요인',
    '- 모니터링\n- 냉정함\n- 결론 도출',
    '- 창의적\n- 확산적\n- 혁신적',
    '- 직감\n- 감정\n- 정서',
    '- 객관적\n- 중립적인 사실과 정보',
    '- 긍정적\n- 낙관적\n- 실현 가능성',
  ];

  const hoverContents = [
    '경험, 지식, 정보와 맞지 않는 것, 부정확한 것, 위험한 것 등을 논리적으로 제시',
    '객관적, 이성적 판단을 기반으로 사고를 정리하고 요약하여 결론을 제시',
    '기존의 사고에서 벗어나 새로운 시각과 창의적인 아이디어를 제시',
    '감정들에 의해 의견을 제시, 의견에 대한 정당성, 이유, 근거를 제시하지 않음',
    '이미 검증된 정확한 정보를 제시하고 중립적이고 객관성을 유지한 의견을 제시',
    '근거를 기반으로 장점을 찾고 달성 가능한 실행 방안을 제시'
  ];

  return (
    <div className="flex flex-row">
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-2 gap-4 p-4 w-2/5 overflow-hidden">
        {colors.map((color, index) => (
          <div key={index} className="w-full">
            <Card
              color={color}
              title={titles[index]}
              content={descriptions[index]}
              hoverTitle={titles[index]}
              hoverContent={hoverContents[index]}
            />
          </div>
        ))}
      </div>
      <div className='chat w-screen sm:w-screen overflow-y-auto'>
        <Chat />
      </div>
    </div>
  );
}
