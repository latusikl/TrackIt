CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

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

CREATE TABLE IF NOT EXISTS user_data (
    user_id UUID PRIMARY KEY DEFAULT uuid_generate_v1 (),
    user_email VARCHAR (100) NOT NULL,
    password VARCHAR (250) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_devices (
    device_id VARCHAR (30) PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES user_data(user_id) ON DELETE CASCADE,
    device_name VARCHAR (25) NOT NULL,
    device_status VARCHAR (20) NOT NULL
);
