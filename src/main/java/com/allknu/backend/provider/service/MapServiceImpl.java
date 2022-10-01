package com.allknu.backend.provider.service;

import com.allknu.backend.core.service.MapService;
import com.allknu.backend.domain.GPSLocation;
import com.allknu.backend.domain.MapMarker;
import com.allknu.backend.domain.MapMarkerOperationInfo;
import com.allknu.backend.exception.errors.NotFoundMapMarkerException;
import com.allknu.backend.repository.MapMarkerInfoRepository;
import com.allknu.backend.repository.MapMarkerRepository;
import com.allknu.backend.web.dto.RequestMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {
    private final MapMarkerRepository mapMarkerRepository;
    private final MapMarkerInfoRepository mapMarkerInfoRepository;

    /**
     * 맵 마커를 추가한다.
     * @param createMarkerDto
     */
    @Override
    @Transactional
    public Long createMarker(RequestMap.CreateMarker createMarkerDto) {
        // 마커생성
        MapMarker marker = MapMarker.builder()
                .mapMarkerType(createMarkerDto.getType())
                .title(createMarkerDto.getTitle())
                .floor(createMarkerDto.getFloor())
                .icon(createMarkerDto.getIcon())
                .image(createMarkerDto.getImage())
                .name(createMarkerDto.getName())
                .room(createMarkerDto.getRoom())
                .subTitle(createMarkerDto.getSubTitle())
                .gpsLocation(GPSLocation.builder()
                        .latitude(createMarkerDto.getGpsInfo().getLatitude())
                        .longitude(createMarkerDto.getGpsInfo().getLongitude())
                        .build())
                .build();

        // 운영 시간 관련 정보가 담겨있는 경우, 1대1 매핑해준다.
        if(createMarkerDto.getOperationInfo() != null) {
            marker.mappingOperationInfo(MapMarkerOperationInfo
                    .builder()
                            .operationTime(createMarkerDto.getOperationInfo().getOperationTime())
                            .phone(createMarkerDto.getOperationInfo().getPhone())
                    .build());
        }

        marker = mapMarkerRepository.save(marker);
        return marker.getId();
    }
    @Override
    @Transactional
    public void deleteMarker(String name) {
        MapMarker mapMarker= mapMarkerRepository.findByName(name);

        if(mapMarker == null){
            throw new NotFoundMapMarkerException();
        }
        mapMarkerRepository.delete(mapMarker);
    }
}
