import React from 'react';
function Footer() {
    return (
        <React.Fragment>
            <footer class="bg-slate-700 dark:bg-gray-900">
                <div class="container px-6 py-12 mx-auto">
                    <div class="grid grid-cols-2 gap-6 lg:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-4">
                        <div>
                            <h3 class="text-lg font-bold text-white dark:text-gray-400">정보</h3>

                            <div class="flex flex-col items-center mt-4 space-y-4">
                                <a
                                    href="/"
                                    class="text-white transition-colors duration-200 dark:text-gray-200 dark:hover:text-blue-400 hover:underline hover:text-blue-600"
                                >
                                    unmute 팀
                                </a>
                                <a
                                    href="/"
                                    class="text-white transition-colors duration-200 dark:text-gray-200 dark:hover:text-blue-400 hover:underline hover:text-blue-600"
                                >
                                    기업목표
                                </a>
                            </div>
                        </div>

                        <div>
                            <h3 class="text-lg font-bold text-white dark:text-gray-400">다운로드</h3>

                            <div class="flex flex-col items-center mt-4 space-y-4">
                                <a
                                    href="/"
                                    class="text-white transition-colors duration-200 dark:text-gray-200 dark:hover:text-blue-400 hover:underline hover:text-blue-600"
                                >
                                    unmute 데스크톱
                                </a>
                                <a
                                    href="/"
                                    class="text-white transition-colors duration-200 dark:text-gray-200 dark:hover:text-blue-400 hover:underline hover:text-blue-600"
                                >
                                    unmute 모바일
                                </a>
                            </div>
                        </div>

                        <div>
                            <h3 class="text-lg font-bold text-white dark:text-gray-400">영업</h3>

                            <div class="flex flex-col items-center mt-4 space-y-4">
                                <a
                                    href="/"
                                    class="text-white transition-colors duration-200 dark:text-gray-200 dark:hover:text-blue-400 hover:underline hover:text-blue-600"
                                >
                                    영업 문의
                                </a>
                                <a
                                    href="/"
                                    class="text-white transition-colors duration-200 dark:text-gray-200 dark:hover:text-blue-400 hover:underline hover:text-blue-600"
                                >
                                    요금 및 가격
                                </a>
                            </div>
                        </div>

                        <div>
                            <h3 class="text-lg font-bold text-white dark:text-gray-400">지원</h3>

                            <div class="flex flex-col items-center mt-4 space-y-4">
                                <a
                                    href="/"
                                    class="text-white transition-colors duration-200 dark:text-gray-200 dark:hover:text-blue-400 hover:underline hover:text-blue-600"
                                >
                                    계정
                                </a>
                                <a
                                    href="/"
                                    class="text-white transition-colors duration-200 dark:text-gray-200 dark:hover:text-blue-400 hover:underline hover:text-blue-600"
                                >
                                    문의처
                                </a>
                            </div>
                        </div>
                    </div>
                    {/* 여기에  div 넣고 년도 줄 추가 */}
                    <hr class="h-px mt-8 bg-gray-300 border-none dark:bg-gray-700" />
                    <div class="pt-8 ">
                        <p class="text-white dark:text-gray-200 items-center">
                            Copyright © 2024 unmute｜010-1234-5678｜abc123@unmute.com
                        </p>
                    </div>
                </div>
            </footer>
        </React.Fragment>
    );
}
export default Footer;
