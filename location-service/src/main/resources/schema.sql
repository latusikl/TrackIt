CREATE TABLE IF NOT EXISTS location_data (
	location_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	device_id VARCHAR (30) NOT NULL,
	date_time_start TIMESTAMP NOT NULL,
	longitude DOUBLE PRECISION NOT NULL,
	latitude DOUBLE PRECISION NOT NULL,
	UNIQUE (device_id,date_time_start)
);

CREATE TABLE IF NOT EXISTS device_info (
    device_info_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	device_id VARCHAR (30) NOT NULL,
	message VARCHAR (300) NOT NULL,
	server_date_time TIMESTAMP,
	info_level VARCHAR (8)
);
