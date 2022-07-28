create TABLE IF NOT EXISTS stockfeed(
    id bigint AUTO_INCREMENT PRIMARY KEY,
    text VARCHAR(1024) NOT NULL,
    url VARCHAR(1024) NOT NULL,
    country VARCHAR(255) NOT NULL,
    feeddate TIMESTAMP NOT NULL,
    feedcreateddate TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
    constraint UK_TEXT unique (text)
);