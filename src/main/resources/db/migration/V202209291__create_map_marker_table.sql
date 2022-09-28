/* 맵 마커 관련 테이블 생성 */
CREATE TABLE map_marker(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    map_marker_type VARCHAR(31),
    title VARCHAR(31),
    sub_title VARCHAR(31),
    floor VARCHAR(31),
    room VARCHAR(31),
    name VARCHAR(31),
    icon VARCHAR(31),
    image VARCHAR(31),
    latitude DOUBLE,
    longitude DOUBLE
);
/* 운영시간 정보 관련 1대1 매핑 테이블 */
CREATE TABLE map_marker_operation_info(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    operation_time VARCHAR(31),
    phone VARCHAR(31),
    map_marker_id BIGINT,
    FOREIGN KEY(map_marker_id) REFERENCES map_marker(id)
);