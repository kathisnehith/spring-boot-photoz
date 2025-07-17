CREATE TABLE IF NOT EXISTS PHOTOZ (
    id varchar(255) PRIMARY KEY,
    file_name varchar(255),
    content_type varchar(255),
    data varbinary(max)
);