package com.allknu.backend.fcmapi.domain;

import lombok.Getter;

/**
 * all-knu-fcm의 SubscribeType과 동일해야한다.
 */
@Getter
public enum SubscribeType {

    ALL("전체(기본 구독 토픽)", "none"),
    EXCEPTION("예외유형", "none"), //예외
    //부서
    COUNSEL("마음나눔센터", "univ"), // 마음나눔센터
    CTL("교수학습지원센터", "univ"), // 교수학습지원센터
    CAREER("진로취창업센터", "univ"), // 진로취창업
    GLOBAL("대외교류센터", "univ"), // 대외교류
    CCE("창의융합교육센터", "univ"),
    DEC("원격교육지원센터", "univ"),
    JOB_PLUS("대학일자리플러스센터", "univ"),
    GLOBAL_CONTRIBUTE("글로벌사회공헌센터","univ"),
    DISABLED_SUPPORT("장애학생지원센터","univ"),
    SOCIAL_ENTERPRISE("사회적기업지원센터", "univ"),
    ROTC("ROTC", "univ"),

    //대학
    IWC("공과대학", "college"),
    BAM("경영관리대학", "college"),
    WC("복지융합대학", "college"),
    GT("글로벌인재대학", "college"),
    KNU_EDU("사범대학","college"),
    CLAS("KNU참인재대학", "college"),

    //학과
    SOFTWARE("ICT융합공학부", "major"), //소응
    WELFARE("사회복지학부", "major"),
    SENIOR("실버산업학과", "major"),
    UVD("유니버셜아트디자인학과", "major"),
    SPORT("스포츠복지학과", "major"),
    CHRISTIANITY("기독교학과", "major"),
    KOREA_CULTURE("한영문화콘텐츠학과", "major"),
    INTERNATIONAL_REGIONAL("글로벌문화학부", "major"),
    MUSIC("음악학과", "major"),
    EDU("교육학과", "major"),
    CHILDHOOD_EDU("유아교육과", "major"),
    SPECIAL_EDU("초.중등특수교육과", "major"),
    GLOBAL_BIZ("글로벌경영학부", "major"),
    PUBLIC("공공인재학과", "major"),
    TAX_ECONOMIC("정경학부", "major"),
    INTELLIGENCE("인공지능융합공학부", "major"),
    CONSTRUCTION("부동산건설학부", "major");


    private String korean;
    private String team; // department, major
    SubscribeType(String korean, String team) {
        this.korean = korean;
        this.team = team;
    }
}
