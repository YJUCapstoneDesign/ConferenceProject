function AboutText() {
  return (
    <div className="lg:text-right mt-0">
      <h2 className="font-bold text-indigo-700 text-2xl lg:text-3xl xl:text-4xl">
        AI를 활용한 통합 커뮤니케이션 및 <br /> 협업 플랫폼
      </h2>
      <br />
      <p className="font-bold text-indigo-700 text-sm lg:text-base xl:text-lg">
        미팅, 팀 채팅, 화이트보드, 전화 등 다양한 기능을 하나로 통합
        <br />
        하여 의미있는 연결을 만들어 보세요.
      </p>
      <div className="space-y-5">
        <p className="text-indigo-700 font-bold inline-block text-sm lg:text-base xl:text-lg">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="w-6 h-6 inline-block text-blue-500"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"
            />
          </svg>
          <span className="mx-2">실시간 화상회의</span>
          &nbsp; &nbsp;
        </p>
        <p className="text-indigo-700 font-bold inline-block text-sm lg:text-base xl:text-lg">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="w-6 h-6 inline-block text-blue-500"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"
            />
          </svg>
          <span className="mx-2">일정 관리</span>
        </p>
        <br />
        <p className="text-indigo-700 font-bold inline-block text-sm lg:text-base xl:text-lg">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="w-6 h-6 inline-block text-blue-500"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"
            />
          </svg>
          <span className="mx-2">브레인 스토밍</span>
          &nbsp; &nbsp;
        </p>
        <p className="text-indigo-700 font-bold inline-block text-sm lg:text-base xl:text-lg">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="w-6 h-6 inline-block -mt-1 mr-2 text-blue-500"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"
            />
          </svg>
          <span className="">화이트 보드</span>
        </p>
        <br />
        <p className="text-indigo-700 font-bold inline-block text-sm lg:text-base xl:text-lg">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="w-6 h-6 inline-block text-blue-500"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"
            />
          </svg>
          <span className="mx-2">실시간 채팅</span>
          &nbsp; &nbsp;
        </p>
        <p className="text-indigo-700 font-bold inline-block text-sm lg:text-base xl:text-lg">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="w-6 h-6 inline-block -mt-1 mr-2 text-blue-500"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"
            />
          </svg>
          <span className="">화면 공유</span>
        </p>
        <br />
        <p className="text-indigo-700 font-bold inline-block text-sm lg:text-base xl:text-lg">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="w-6 h-6 inline-block -mt-1 mr-2 text-blue-500"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"
            />
          </svg>
          <span className="">AI를 사용한 요약</span>
        </p>
      </div>
    </div>
  );
}

export default AboutText;
