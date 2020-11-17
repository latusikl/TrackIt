CREATE SCHEMA IF NOT EXISTS track_it;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS track_it.user_data (
                                                  user_id UUID PRIMARY KEY DEFAULT uuid_generate_v1(),
                                                  user_email VARCHAR (150) NOT NULL,
                                                  password VARCHAR (250) NOT NULL,
                                                  account_creation_date_time TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS track_it.user_devices (
                                                     device_id VARCHAR (150) PRIMARY KEY,
                                                     user_id UUID NOT NULL REFERENCES track_it.user_data(user_id) ON DELETE CASCADE,
                                                     device_name VARCHAR (80) NOT NULL,
                                                     device_status VARCHAR (30) NOT NULL
);

CREATE TABLE IF NOT EXISTS track_it.location_data (
                                                      location_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                                                      device_id VARCHAR (150) NOT NULL REFERENCES track_it.user_devices(device_id) ON DELETE CASCADE,
                                                      date_time_start TIMESTAMP NOT NULL,
                                                      longitude DOUBLE PRECISION NOT NULL,
                                                      latitude DOUBLE PRECISION NOT NULL,
                                                      UNIQUE (device_id,date_time_start)
);

CREATE TABLE IF NOT EXISTS track_it.device_info (
                                                    device_info_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                                                    device_id VARCHAR (150) NOT NULL REFERENCES track_it.user_devices(device_id) ON DELETE CASCADE,
                                                    message VARCHAR (300) NOT NULL,
                                                    server_date_time TIMESTAMP,
                                                    info_level VARCHAR (30)
);
