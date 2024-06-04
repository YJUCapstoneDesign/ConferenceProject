import React, { useCallback, useState, useRef } from "react";
import GeminiSummarizer from "./gemini"; 

const Crazy = () => {
  const [time, setTime] = useState(480);
  const [isRunning, setIsRunning] = useState(false);
  const [texts, setTexts] = useState(Array(8).fill(""));
  const intervalRef = useRef(null);
  const [showSummary, setShowSummary] = useState(false); 
  const [triggerSummarize, setTriggerSummarize] = useState(false);

  const formatTime = (seconds) => {
    const minutes = Math.floor(seconds / 60);
    const secs = seconds % 60;
    return `${minutes}:${secs < 10 ? "0" : ""}${secs}`;
  };

  const handleStartPause = () => {
    if (isRunning) {
      clearInterval(intervalRef.current);
    } else {
      intervalRef.current = setInterval(() => {
        setTime((prevTime) => {
          if (prevTime === 0) {
            clearInterval(intervalRef.current);
            return 0;
          }
          return prevTime - 1;
        });
      }, 1000);
    }
    setIsRunning(!isRunning);
  };

  const handleReset = () => {
    clearInterval(intervalRef.current);
    setTime(480);
    setIsRunning(false);
  };

  const handleTextChange = (index, event) => {
    const newTexts = [...texts];
    const newValue = event.target.value;
    if (newValue.length <= 48) {
      newTexts[index] = newValue;
      setTexts(newTexts);
    }
  };

  const handleSummarize = () => {
    setShowSummary(true); 
    setTriggerSummarize(true); 
  };

  // GeminiSummarizer 컴포넌트에서 요약이 완료된 후 호출될 함수
  const handleSummaryComplete = () => {
    setTriggerSummarize(false); // 요약 완료 후 다시 false로 변경
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-bgcolor p-4">
      <div className="text-black text-6xl mb-4">{formatTime(time)}</div>
      <div className="flex space-x-2 mb-4">
        <button className="text-white text-2xl" onClick={handleStartPause}>
          {isRunning ? (
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              viewBox="0 0 24 24"
              style={{ fill: "rgba(0, 0, 0, 1)" }}
            >
              <path d="M8 7h3v10H8zm5 0h3v10h-3z"></path>
            </svg>
          ) : (
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              viewBox="0 0 24 24"
              style={{ fill: "rgba(0, 0, 0, 1)" }}
            >
              <path d="M7 6v12l10-6z"></path>
            </svg>
          )}
        </button>
        <button className="text-white text-2xl" onClick={handleReset}>
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="24"
            height="24"
            viewBox="0 0 24 24"
            style={{ fill: "rgba(0, 0, 0, 1)" }}
          >
            <path d="M12 16c1.671 0 3-1.331 3-3s-1.329-3-3-3-3 1.331-3 3 1.329 3 3 3z"></path>
            <path d="M20.817 11.186a8.94 8.94 0 0 0-1.355-3.219 9.053 9.053 0 0 0-2.43-2.43 8.95 8.95 0 0 0-3.219-1.355 9.028 9.028 0 0 0-1.838-.18V2L8 5l3.975 3V6.002c.484-.002.968.044 1.435.14a6.961 6.961 0 0 1 2.502 1.053 7.005 7.005 0 0 1 1.892 1.892A6.967 6.967 0 0 1 19 13a7.032 7.032 0 0 1-.55 2.725 7.11 7.11 0 0 1-.644 1.188 7.2 7.2 0 0 1-.858 1.039 7.028 7.028 0 0 1-3.536 1.907 7.13 7.13 0 0 1-2.822 0 6.961 6.961 0 0 1-2.503-1.054 7.002 7.002 0 0 1-1.89-1.89A6.996 6.996 0 0 1 5 13H3a9.02 9.02 0 0  0 1.539 5.034 9.096 9.096 0 0 0 2.428 2.428A8.95 8.95 0 0 0 12 22a9.09 9.09 0 0 0 1.814-.183 9.014 9.014 0 0 0 3.218-1.355 8.886 8.886 0 0 0 1.331-1.099 9.228 9.228 0 0 0 1.1-1.332A8.952 8.952 0 0 0 21 13a9.09 9.09 0 0 0-.183-1.814z"></path>
          </svg>
        </button>
      </div>
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-4 gap-4">
        {Array.from({ length: 8 }, (_, i) => (
          <div
            key={i}
            className="flex flex-col justify-between w-full sm:w-48 h-32 bg-white rounded-lg shadow-lg p-2"
          >
            <div className="flex-grow flex items-center justify-center">
              <textarea
                className="text-black text-center text-sm font-semibold w-full h-full resize-none bg-transparent border-none placeholder-slate-600"
                value={texts[i]}
                onChange={(event) => handleTextChange(i, event)}
                placeholder="텍스트를 입력하세요"
              />
            </div>
            <span className="text-black text-center text-md text-gray-500 font-semibold">
              IDEA-{i + 1} : {texts[i].length}/48자
            </span>
          </div>
        ))}
      </div>
      <button
        className="inline-block w-24 h-8 mt-2.5 text-xs text-white font-bold py-2 px-4 rounded-full bg-gradient-to-r from-pink-500 to-purple-800 border border-transparent transform hover:scale-110 hover:border-white transition-transform duration-3000 ease-in-out"
        onClick={handleSummarize}
      >
        요약 하기
      </button>

        {/* showSummary가 true일 때만 GeminiSummarizer 컴포넌트 렌더링 */}
    {showSummary && (
      <GeminiSummarizer
        textToSummarize={texts.join(" ")}
        triggerSummarize={triggerSummarize}
        onSummaryComplete={handleSummaryComplete} // 추가
      />
    )}
    </div>
  );
};

export default Crazy;