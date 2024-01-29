import React from "react";

function HomeText() {
  return (
    <div>
      <div className="flex items-center gap-3"></div>
      <p className="text-[40px] lg:text-[45px] xl:text-[55px] font-bold leading-tight mt-5 sm:mt-0 text-left text-white">
        Zoom
      </p>
      <p className="mt-5 md:text-md text-left text-white">
        Zoom Ai Companion은 사용자에게 도움을 주는 신뢰할 수<br />
        있는 디지털 어시스턴트입니다.{" "}
        <b>
          유료 Zoom 사용자 계정에
          <br />
          추가 비용 없이 포함되어 있습니다.
        </b>
      </p>
    </div>
  );
}

export default HomeText;
