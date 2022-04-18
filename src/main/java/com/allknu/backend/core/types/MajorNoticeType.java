package com.allknu.backend.core.types;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

//학과공지사항 타입 //전체 조회
@Getter
public enum MajorNoticeType {
    SOFTWARE("ICT융합공학부", Arrays.asList("ICT융합공학부","소프트웨어응용학부","ICT융합공학부","응용수학과","문헌정보학과"),"https://sae.kangnam.ac.kr/menu/e408e5e7c9f27b8c0d5eeb9e68528b48.do","https://sae.kangnam.ac.kr/menu/board/info/e408e5e7c9f27b8c0d5eeb9e68528b48.do"),
    WELFARE("사회복지학부",Arrays.asList("사회복지학부","사회복지학부(야)","wel-tech융합전공"),"https://knusw.kangnam.ac.kr/menu/22dd7f703ec676ffdecdd6b4e4fe1b1b.do","https://knusw.kangnam.ac.kr/menu/board/info/22dd7f703ec676ffdecdd6b4e4fe1b1b.do"),
    SENIOR("실버산업학과",Arrays.asList("실버산업학과"),"https://senior-industry.kangnam.ac.kr/menu/48ca5089900546629c7268ccd159230c.do","https://senior-industry.kangnam.ac.kr/menu/board/info/48ca5089900546629c7268ccd159230c.do"),
    UVD("유니버셜디자인전공",Arrays.asList("유니버셜디자인전공","유니버설아트디자인전공"),"https://uvd.kangnam.ac.kr/menu/42d9111a4520432b4c18cd32ca720da9.do","https://uvd.kangnam.ac.kr/menu/board/info/42d9111a4520432b4c18cd32ca720da9.do"),
    VISUAL_ART("미술문화복지전공",Arrays.asList("미술문화복지전공"),"https://visualart.kangnam.ac.kr/menu/01b041e5243b5c7625c55ed526f541b4.do","https://visualart.kangnam.ac.kr/menu/board/info/01b041e5243b5c7625c55ed526f541b4.do"),
    SPORT("스포츠복지전공",Arrays.asList("스포츠복지전공"),"https://ksps.kangnam.ac.kr/menu/b5e36d184e074885cb8a34034317f0fa.do","https://ksps.kangnam.ac.kr/menu/board/info/b5e36d184e074885cb8a34034317f0fa.do"),
    CHRISTIANITY("기독교학과",Arrays.asList("기독교학과","신학과"),"https://dcs.kangnam.ac.kr/menu/21c5e4a10139dc43a78a70eafa4b658e.do","https://dcs.kangnam.ac.kr/menu/board/info/21c5e4a10139dc43a78a70eafa4b658e.do"),
    KOREA_CULTURE("한영문화콘텐츠학과",Arrays.asList("한영문화콘텐츠학과","영문학과"),"https://kcc.kangnam.ac.kr/menu/38c11cb08767885a5b407bfbf0465804.do","https://kcc.kangnam.ac.kr/menu/board/info/38c11cb08767885a5b407bfbf0465804.do"),
    INTERNATIONAL_REGIONAL("국제지역학전공",Arrays.asList("국제관계학전공"),"https://dgs.kangnam.ac.kr/menu/3716ae3188921044251796974ddb2904.do","https://dgs.kangnam.ac.kr/menu/board/info/3716ae3188921044251796974ddb2904.do"),
    CHINA_REGIONAL("중국지역학전공",Arrays.asList("중국지역학전공","중국실용지역학과"),"https://cas.kangnam.ac.kr/menu/7aafb9047ed470003dacc909bb5117a4.do","https://cas.kangnam.ac.kr/menu/board/info/7aafb9047ed470003dacc909bb5117a4.do"),
    MUSIC("음악학과",Arrays.asList("음악학과"),"https://musicdpt.kangnam.ac.kr/menu/ac37fa802cc891d4909f48061ae97ecc.do","https://musicdpt.kangnam.ac.kr/menu/board/info/ac37fa802cc891d4909f48061ae97ecc.do"),
    EDU("교육학과",Arrays.asList("교육학과"),"https://educ.kangnam.ac.kr/menu/8e4d118b43e9fb0f1d3d8ff4a35911c4.do","https://educ.kangnam.ac.kr/menu/board/info/8e4d118b43e9fb0f1d3d8ff4a35911c4.do"),
    CHILDHOOD_EDU("유아교육과",Arrays.asList("유아교육과"),"https://knece.kangnam.ac.kr/menu/1e179ef9d06b26f3ae133a18a5ee1ba7.do","https://knece.kangnam.ac.kr/menu/board/info/1e179ef9d06b26f3ae133a18a5ee1ba7.do"),
    SPECIAL_EDU("초.중등특수교육과",Arrays.asList("초.중등특수교육과","중등특수교육학과","초등특수교육학과"),"https://sped.kangnam.ac.kr/menu/f1b0fb76ba66f0a7319fc278bed29175.do","https://sped.kangnam.ac.kr/menu/board/info/f1b0fb76ba66f0a7319fc278bed29175.do"),
    GLOBAL_BIZ("글로벌경영학부",Arrays.asList("글로벌경영학부","국제통상학과","글로벌경영학부(야)","글로벌문화학부","법학과","법학과(야)","행정학과"),"https://globalbiz.kangnam.ac.kr/menu/c40027d71fb6c2a591f45a70d4422395.do","https://globalbiz.kangnam.ac.kr/menu/board/info/c40027d71fb6c2a591f45a70d4422395.do"),
    PUBLIC("공공인재학과",Arrays.asList("공공인재학과","공공인재학과(야)"),"https://pub.kangnam.ac.kr/menu/99ef00b9eb617020f7931b6c84a1a503.do","https://pub.kangnam.ac.kr/menu/board/info/99ef00b9eb617020f7931b6c84a1a503.do"),
    TAX_ECONOMIC("정경학부",Arrays.asList("정경학부","정경학부(야)","경제세무학과","경제세무학과(야)","경제학과","경제학과(야)","세무학과","세무학과(야)"),"https://taxeconomics.kangnam.ac.kr/menu/c5ff390e01c264667edaff49e31d35f3.do","https://taxeconomics.kangnam.ac.kr/menu/board/info/c5ff390e01c264667edaff49e31d35f3.do"),
    IOT("IOT전자공학과",Arrays.asList("IOT전자공학과"),"https://iotelectronic.kangnam.ac.kr/menu/eeab9197653989afe36b19b3b0b34bf5.do","https://iotelectronic.kangnam.ac.kr/menu/board/info/eeab9197653989afe36b19b3b0b34bf5.do"),
    INTELLIGENCE("인공지능융합공학부",Arrays.asList("인공지능융합공학부","산업시스템공학과"),"https://ie.kangnam.ac.kr/menu/f3a3bfbbc5715e4180657f71177d8bcf.do","https://ie.kangnam.ac.kr/menu/board/info/f3a3bfbbc5715e4180657f71177d8bcf.do"),
    CONSTRUCTION("부동산건설학부",Arrays.asList("부동산건설학부","건축공학과","도시공학과","부동산학과"),"https://knureal.kangnam.ac.kr/menu/26c80ce9728657a14fe638d4c566ead8.do","https://knureal.kangnam.ac.kr/menu/board/info/26c80ce9728657a14fe638d4c566ead8.do");

    private String korean;
    private List<String> majorList;
    private String url;
    private String boardUrl;

    MajorNoticeType(String korean,List<String> majorList, String url, String boardUrl) {
        this.korean = korean;
        this.majorList = majorList;
        this.url = url;
        this.boardUrl = boardUrl;
    }
    public static MajorNoticeType findByMajor(String major){
        return Arrays.stream(MajorNoticeType.values())
                .filter(majorNoticeType -> majorNoticeType.hasMajor(major))
                .findAny().orElseThrow();
    }
    public boolean hasMajor(String major){
        return majorList.stream().anyMatch(m -> m.equals(major));
    }
}
