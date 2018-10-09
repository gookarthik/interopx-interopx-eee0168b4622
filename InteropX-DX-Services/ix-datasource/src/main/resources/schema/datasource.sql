CREATE TABLE data_sources
(
    ds_id    serial,
	ds_name    varchar(255),
	endpoint_url  text,
    is_secure     boolean,
	security_method  character varying(255),
	connector_type character varying(255),
	credentials json,
	last_updated_ts  TIMESTAMP NOT NULL  DEFAULT (NOW() at time zone 'UTC'),
    PRIMARY KEY (ds_id)
);

create unique index XPKdata_sources on data_sources
(
 ds_id
);