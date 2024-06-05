import axios from "axios";

// url 호출 시 기본 값 셋팅
const api = axios.create({
  baseURL: "http://localhost:8080",
  headers: { "Content-type": "application/json" }, // 헤더에 데이터 타입 json으로 담기
});

// 요청 인터셉터 추가
api.interceptors.request.use(
  function (config) {
    const token = localStorage.getItem("accessToken");

    //요청시 AccessToken 계속 보내주기
    if (!token) {
      config.headers.Authorization = null;
      config.headers['authorization-refresh'] = null;
      return config;
    }

    if (config.headers && token) {
      const accessToken = JSON.parse(token);
      const refreshToken = JSON.parse(localStorage.getItem("refreshToken"));
      config.headers.Authorization = `Bearer ${accessToken}`;
      config.headers['authorization-refresh'] = `Bearer ${refreshToken}`;
      return config;
    }
    console.log("request start", config);
  },
  function (error) {
    console.log("request error", error);
    return Promise.reject(error);
  }
);

// 응답 인터셉터 추가
api.interceptors.response.use(
  function (response) {
    console.log("get response", response);
    return response;
  },
  async (error) => {
    const {
      config,
      response: { status },
    } = error;
    if (status === 401) {
      // if (error.response.data.message === "expired") {
        const originalRequest = config;
        const refreshToken = await JSON.parse(localStorage.getItem("refreshToken"));
        // refresh 토큰 요청
        const { data } = await axios.post(
          `http://localhost:8080/api/team/join`, // refresh 토큰 api 주소
          {},
          { headers: { 'Authorization-refresh': `Bearer ${refreshToken}` } }
        );
        // 새로운 토큰 저장
        const { accessToken: newAccessToken, refreshToken: newRefreshToken } =
          data;
        await localStorage.multiSet([
          ["accessToken", newAccessToken],
          ["refreshToken", newRefreshToken],
        ]);
        originalRequest.headers.authorization = `Bearer ${newAccessToken}`;
        // 401로 요청 실패했던 요청 새로운 accessToken으로 재요청
        return axios(originalRequest);
      // }
    }
    console.log("response error", error);
    return Promise.reject(error);
  }
);

export default api;