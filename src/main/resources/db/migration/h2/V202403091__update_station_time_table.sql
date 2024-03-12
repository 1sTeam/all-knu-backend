CREATE TABLE station_time_table_day (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

ALTER TABLE station_timetable
ADD COLUMN day_id BIGINT;

ALTER TABLE station_timetable
ADD FOREIGN KEY (day_id)
REFERENCES station_time_table_day (id);