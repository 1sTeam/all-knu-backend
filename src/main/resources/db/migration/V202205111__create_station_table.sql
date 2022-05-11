CREATE TABLE station (
    station_name VARCHAR(63) PRIMARY KEY
);

CREATE TABLE station_timetable (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    stop_time TIME,
    station_name VARCHAR(63) NOT NULL,
    FOREIGN KEY(station_name) REFERENCES station(station_name)
);

