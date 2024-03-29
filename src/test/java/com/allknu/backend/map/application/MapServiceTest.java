package com.allknu.backend.map.application;

import com.allknu.backend.map.domain.GPSLocation;
import com.allknu.backend.map.domain.MapMarker;
import com.allknu.backend.map.domain.MapMarkerOperationInfo;
import com.allknu.backend.map.domain.MapMarkerType;
import com.allknu.backend.map.application.MapServiceImpl;
import com.allknu.backend.map.domain.MapMarkerInfoRepository;
import com.allknu.backend.map.domain.MapMarkerRepository;
import com.allknu.backend.map.application.dto.RequestMap;
import com.allknu.backend.map.application.dto.ResponseMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
@ActiveProfiles("test")
class MapServiceTest {
    @Autowired
    private MapMarkerRepository mapMarkerRepository;
    @Autowired
    private MapMarkerInfoRepository mapMarkerInfoRepository;
    MapServiceImpl mapService;

    @BeforeEach
    void setup() {
        mapService = new MapServiceImpl(mapMarkerRepository, mapMarkerInfoRepository);
    }

    @Test
    @DisplayName("맵 마커 생성 성공 테스트(운영시간 정보 없음)")
    void createMarkerSuccessWithoutOperationTime() {
        //given
        RequestMap.CreateMarker dto = RequestMap.CreateMarker.builder()
                .type(MapMarkerType.NORMAL)
                .title("흡연장")
                .subTitle("흡연장")
                .floor("5층")
                .room("503호 강의실 뒷문 밖")
                .icon("smoking")
                .image("shalom_image")
                .gpsInfo(RequestMap.GPSInfo.builder()
                        .latitude(37.27545381952122)
                        .longitude(127.13062209315156)
                        .build())
                .build();
        //when
        Long id = mapService.createMarker(dto);
        //then
        MapMarker marker = mapMarkerRepository.getById(id);

        assertEquals(marker.getTitle(), dto.getTitle());
        assertEquals(marker.getIcon(), dto.getIcon());
        assertNotNull(marker.getGpsLocation());
        assertNull(marker.getMapMarkerOperationInfo());
    }
    @Test
    @DisplayName("맵 마커 생성 성공 테스트(운영시간 정보 있음)")
    void createMarkerSuccessWithOperationTime() {
        //given
        RequestMap.CreateMarker dto = RequestMap.CreateMarker.builder()
                .type(MapMarkerType.NORMAL)
                .title("흡연장")
                .subTitle("흡연장")
                .floor("5층")
                .room("503호 강의실 뒷문 밖")
                .icon("smoking")
                .image("shalom_image")
                .gpsInfo(RequestMap.GPSInfo.builder()
                        .latitude(37.27545381952122)
                        .longitude(127.13062209315156)
                        .build())
                .operationInfo(RequestMap.OperationInfo.builder()
                        .operationTime("평일 09:00 ~ 21:00 이외 무인 운영")
                        .phone("010")
                        .build())
                .build();
        //when
        Long id = mapService.createMarker(dto);
        //then
        MapMarker marker = mapMarkerRepository.getById(id);
        assertEquals(marker.getTitle(), dto.getTitle());
        assertEquals(marker.getIcon(), dto.getIcon());
        assertEquals(marker.getMapMarkerOperationInfo().getOperationTime(), dto.getOperationInfo().getOperationTime());
        assertNotNull(marker.getGpsLocation());
    }
    @Test
    @DisplayName("맵 마커 삭제 성공 테스트")
    void deleteMapMarker() {
        //given
        MapMarker mapMarker = MapMarker.builder()
                .mapMarkerType(MapMarkerType.NORMAL)
                .title("흡연장")
                .subTitle("흡연장")
                .name("샬롬관 흡연장")
                .floor("5층")
                .room("503호 강의실 뒷문 밖")
                .icon("smoking")
                .image("shalom_image")
                .gpsLocation(GPSLocation.builder()
                        .latitude(37.27545381952122)
                        .longitude(127.13062209315156)
                        .build())
                .build();
        MapMarkerOperationInfo mapMarkerOperationInfo = MapMarkerOperationInfo.builder()
                .operationTime("평일 09:00 ~ 21:00 이외 무인 운영")
                .phone("010")
                .build();
        mapMarker.mappingOperationInfo(mapMarkerOperationInfo);
        mapMarker = mapMarkerRepository.save(mapMarker);
        assertEquals(mapMarkerRepository.findAll().size(), 1);
        assertEquals(mapMarkerInfoRepository.findAll().size(), 1);
        //when
        mapService.deleteMarker(mapMarkerRepository.findAll().get(0).getId());
        //then
        assertEquals(mapMarkerRepository.findAll().size(), 0);
        assertEquals(mapMarkerInfoRepository.findAll().size(), 0);
    }
    @Test
    @DisplayName("맵 마커 조회 테스트")
    void getMarkerTest(){
        //추가
        MapMarker marker1 = MapMarker.builder()
                .mapMarkerType(MapMarkerType.NORMAL)
                .title("흡연장1")
                .subTitle("흡연장1")
                .floor("5층")
                .room("503호 강의실 뒷문 밖")
                .icon("smoking")
                .image("shalom_image")
                .gpsLocation(GPSLocation.builder()
                        .latitude(37.1123)
                        .longitude(127.213213)
                        .build())
                .build();
        marker1 = mapMarkerRepository.save(marker1);
        MapMarkerOperationInfo info1 = MapMarkerOperationInfo.builder()
                .operationTime("평일 09:00 ~ 21:00 이외 무인 운영")
                .phone("010")
                .build();
        info1 = mapMarkerInfoRepository.save(info1);
        marker1.mappingOperationInfo(info1);
        //추가
        MapMarker marker = MapMarker.builder()
                .mapMarkerType(MapMarkerType.NORMAL)
                .title("흡연장")
                .subTitle("흡연장")
                .floor("5층")
                .room("503호 강의실 뒷문 밖")
                .icon("smoking")
                .image("shalom_image")
                .gpsLocation(GPSLocation.builder()
                        .latitude(37.1123)
                        .longitude(127.213213)
                        .build())
                .build();
        marker = mapMarkerRepository.save(marker);
        MapMarkerOperationInfo info = MapMarkerOperationInfo.builder()
                .operationTime("평일 09:00 ~ 21:00 이외 무인 운영")
                .phone("010")
                .build();
        info = mapMarkerInfoRepository.save(info);
        marker.mappingOperationInfo(info);
        //조회
        List<ResponseMap.GetMapMarker> list =  mapService.getMapMarkers();
        for(ResponseMap.GetMapMarker res : list){
            assertNotNull(res.getMore());
            assertNotNull(res.getTitle());
        }
    }
}
