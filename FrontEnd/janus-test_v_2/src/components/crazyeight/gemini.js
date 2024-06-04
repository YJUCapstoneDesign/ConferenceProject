import React, { useState, useEffect } from "react";
import { GoogleGenerativeAI } from "@google/generative-ai";

const apiKey = process.env.REACT_APP_GEMINI_API_KEY; 
const genAI = new GoogleGenerativeAI(apiKey);

const model = genAI.getGenerativeModel({
  model: "gemini-1.5-flash",
});

const generationConfig = {
  temperature: 1,
  topP: 0.95,
  topK: 64,
  maxOutputTokens: 8192,
  responseMimeType: "text/plain",
};

const GeminiSummarizer = ({ textToSummarize, triggerSummarize, onSummaryComplete }) => {
  const [response, setResponse] = useState("");

  useEffect(() => {
    const fetchData = async () => {
      if (triggerSummarize) {
        try {
          const chatSession = model.startChat({
            generationConfig,
            history: [],
          });

          const contentsType = "배틀그라운드 분석";
          const result = await chatSession.sendMessage(
            `다음 텍스트를 요약해주세요 그리고 주제(${contentsType})에 안맞는 단어는 삭제해 주세요: ${textToSummarize}`
          );
          setResponse(result.response.text());

          // API 호출이 완료된 후 onSummaryComplete 함수 호출
          if (onSummaryComplete) {
            onSummaryComplete(); 
          }
        } catch (error) {
          console.error("Gemini API 요청 오류:", error);
          setResponse("요약을 가져오는 중 오류가 발생했습니다.");
        }
      }
    };

    fetchData();
  }, [triggerSummarize, onSummaryComplete]); // onSummaryComplete 추가

  return (
    <div>
      <h3>요약:</h3>
      <p>{response}</p>
    </div>
  );
};

export default GeminiSummarizer;