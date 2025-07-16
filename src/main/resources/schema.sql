CREATE TABLE IF NOT EXISTS photoz (
    id bigint auto_increment PRIMARY KEY,
    file_name varchar(255),
    content_type varchar(255),
    data varbinary(max)
);