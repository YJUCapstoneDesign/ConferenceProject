import React from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Navigation, Pagination, Scrollbar, A11y, Autoplay } from 'swiper/modules';
import 'swiper/css/bundle';
import Card from '../Card';
import { Button } from '@material-tailwind/react';
import './Notice.css';

function NoticeContent() {
    return (
        <div className="flex">
            <div className="inline-flex flex-col justify-center w-full text-left mx-9 px-5">
                <h1
                    style={{
                        fontSize: '36px',
                        fontWeight: '600',
                    }}
                >
                    Zoom의 새 소식
                </h1>
                <div>
                    <h2
                        className="my-5"
                        style={{
                            fontStyle: 'normal',
                            fontWeight: '400',
                            fontSize: '20px',
                            lineHeight: '130%',
                        }}
                    >
                        최신 소식을 받아 보고, 모범 사례를 배우고, 더 많은 정보를 얻으세요
                    </h2>
                    <Button variant="filled">filled</Button>
                </div>
            </div>
            <Swiper
                modules={[Navigation, Pagination, Scrollbar, A11y]}
                onSwiper={(swiper) => console.log(swiper)}
                loop={true}
                pagination={{ clickable: true }}
                loopAdditionalSlides={1}
                zoom={false}
                slidesPreView={1}
                autoplay={{
                    delay: 1500,
                }}
                navigation
            >
                <SwiperSlide>
                    <Card
                        title="Zoom AI Companion소개"
                        contents="사용자에게 도움을 주는 신뢰할 수 있는 디지털 어시스턴트입니다. Zoom 사용자 계정의 유료 서비스에 추가 비용 없이 포함되어 있습니다. 일부 업종과 지역은 이용 대답에서 제외될 수 있습니다."
                        button="자세히 살펴보기"
                    />
                </SwiperSlide>
                <SwiperSlide>
                    <Card
                        title="온디맨드로 즐기는 Zoomtopia"
                        contents="지금 Zoomtopia 세션을 시청해 보세요. 아직 등록하지 않으셨나요? 등록하신 후, 키노트 세션과 다른 세션들을 원하는 때에 온디맨드로 확인하실 수 있습니다!"
                        button="지금 보기"
                    />
                </SwiperSlide>
                <SwiperSlide>
                    <Card
                        title="Zoom 화이트보드로 창의력 발휘하기"
                        contents="개인, 하이브리드 팀, 원격 팀이 함께 모여 브레인스토밍하고 학습할 수 있는 협업 공간입니다. "
                        button="협업 시작"
                    />
                </SwiperSlide>
                <SwiperSlide>
                    <Card
                        title="Zoom IQ for Sales 소개"
                        contents="영업 대화를 실행 가능한 통찰력으로 바꾸는 Zoom Meetings 및 Zoom Phone용 대화형 인텔리전스 솔루션입니다."
                        button="무료 가입"
                    />
                </SwiperSlide>
                <SwiperSlide>
                    <Card
                        title="참여도 높은 가상 이벤트 설계"
                        contents="백스테이지, 세션 브랜딩 및 웹 세미나 반응으로 가상 및 하이브리드 이벤트를 강화하세요"
                        button="무료 가입"
                    />
                </SwiperSlide>
            </Swiper>
        </div>
    );
}

export default NoticeContent;
