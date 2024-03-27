  
function PassWord() {
  return (
    <div className="App animated-background h-screen bg-gradient-to-r from-blue-500 via-blue-500 to-indigo-500">
      <div className="min-h-screen flex items-center justify-center">
        <div className="max-w-md w-full p-6 bg-white rounded-lg shadow-lg">
          <h1 className="text-2xl font-semibold text-center text-gray-500 mt-8 mb-6">비밀번호를 잊어버리셨나요?</h1> 
          <p className="text-sm text-gray-600 text-center mt-8 mb-6">걱정하지 마세요. 비밀번호 재설정은 간단합니다.<br/> UNMUTE에 등록하신 이메일 주소만 알려주세요.</p> 
          <form>
            <div className="mb-6">
              <label for="email" className="block mb-2 text-sm text-gray-600"></label>
              <input type="email" id="email" name="em il" className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-700" required placeholder="이메일 주소" />
            </div>
            <button type="submit" className="w-32 bg-gradient-to-r from-indigo-700 to-cyan-600 text-white py-2 rounded-lg mx-auto block focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-cyan-500 mt-4 mb-4">전 송</button>
          </form>
          <div className="text-center">
            <p className="text-sm">도움이 필요하신가요? <a href="/" className="text-indigo-700">도움말</a></p>
          </div>
          <p className="text-xs text-gray-600 text-center mt-8">&copy; 2024 UNMUTE</p>
        </div>
      </div>
    </div>
  );
}

export default PassWord;
