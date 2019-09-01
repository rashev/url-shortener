CREATE TABLE url_binding (
    id BIGINT auto_increment PRIMARY KEY,
    short_url_id VARCHAR(200) NOT NULL,
    short_url_domain VARCHAR(200) NOT NULL,
    original_url VARCHAR(2000) NOT NULL,
    partition INTEGER NOT NULL
);

CREATE UNIQUE INDEX ub_short_url ON url_binding(short_url_id, short_url_domain, original_url);
CREATE INDEX ub_orig_url ON url_binding(original_url);

CREATE SEQUENCE counter START WITH 1 INCREMENT BY 1000;

SELECT NEXTVAL('counter');