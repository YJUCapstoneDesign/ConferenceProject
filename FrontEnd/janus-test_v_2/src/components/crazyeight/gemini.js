import React, { useState, useEffect } from "react";
import { GoogleGenerativeAI } from "@google/generative-ai";
import { uploadToS3 } from './FileUploadDownload';

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

const GeminiSummarizer = ({ textToSummarize, triggerSummarize, onSummaryComplete, topic ,onSummaryUpload}) => {
  const [response, setResponse] = useState("");
  const [isSummarizing, setIsSummarizing] = useState(false); // 요약 진행 여부 상태 추가

  useEffect(() => {
    const fetchData = async () => {
      if (triggerSummarize && !isSummarizing) { // 요약 진행 중이 아니라면 요약 진행
        setIsSummarizing(true); // 요약 진행 중 표시
        try {
          const chatSession = model.startChat({
            generationConfig,
            history: [],
          });

          const result = await chatSession.sendMessage(
            `다음 텍스트를 요약해주세요 그리고 주제(${topic})에 안맞는 단어는 삭제해 주세요 그리고 요약한 단어만 출력해주세요: ${textToSummarize}`
          );
          setResponse(result.response.text());

          // API 호출이 완료된 후 onSummaryComplete 함수 호출
          if (onSummaryComplete) {
            onSummaryComplete();
            
            const uploadResult = await uploadToS3(result.response.text(),'2222');
            if (uploadResult.success) {
              alert('파일 저장 성공!');
            } else {
              alert('파일 저장 실패!');
            }
          
          }
          
        } catch (error) {
          console.error("Gemini API 요청 오류:", error);
          setResponse("요약을 가져오는 중 오류가 발생했습니다.");
        } finally {
          setIsSummarizing(false); // 요약 완료 후 진행 중 표시 해제
        }
      }
    };

    fetchData();
  }, [triggerSummarize]); // onSummaryComplete 제거

  return (
    <div>
      <h3>요약</h3>
      <p>{response}</p>
    </div>
  );
};

export default GeminiSummarizer; 