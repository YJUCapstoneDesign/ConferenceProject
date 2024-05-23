import axios from 'axios';
import { refresh } from "./auth"


// axios에 대한 인스턴스 생성 
// axios.create 메서드 사용 시 axios 인스턴스를 생성 가능함
const instance = axios.create({
    baseURL: "http://localhost:4000/api/test",
});

const logout = () => {
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');
};

// 엑세스 토큰 재발급 
const getRefreshToken = async () => {
    try {
      const res = await refresh();
      const accessToken = res.data.data?.accessToken;
      return accessToken;
    } catch (e) {
      // 로그아웃 처리
      logout();
    }
  };

// 요청 인터셉터 설정하기
instance.interceptors.request.use(
    async (config) => {
    // 헤더에 엑세스 토큰 담음
    const access_token = localStorage.getItem('access_token');
    // 엑세스 토큰 존재 시 Authorization에 토큰 추가
    if(access_token) {
        config.headers.Authorization = `Bearer ${access_token}`;
    }
    return config;
    },
    (error) => {
        // 요청 설정 중에 오류가 발생하면, Promise.reject를 통해 오류를 반환함
        return Promise.reject(error);
    }
);

// 응답 인터셉터 설정하기
instance.interceptors.response.use(
    (response) => {
      return response;
    },
    async (error) => {
      const { config, response } = error;
      //  401에러가 아니거나 재요청이면 에러발생함
      if (response.status !== 401 || config.sent) {
        return Promise.reject(error);
      }
      // 아닌 경우 토큰 갱신
      config.sent = true; // 무한 재요청 방지
      const accessToken = await getRefreshToken();
      if (accessToken) {
        localStorage.setItem('accessToken', accessToken);
        config.headers.Authorization = `Bearer ${accessToken}`;
      }
      return axios(config); // 재요청
    },
  );


  export default instance;