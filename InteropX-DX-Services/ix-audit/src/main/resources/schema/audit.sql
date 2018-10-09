CREATE TABLE audit
(
  id serial,
  category varchar(255),
  created TIMESTAMP NOT NULL  DEFAULT (NOW() at time zone 'UTC'),
  destination varchar(255),
  event_data text,
  event_name varchar(255),
  event_status varchar(255),
  request_type varchar(255),
  resource_name varchar(255),
  source varchar(255),
  workflowid integer,
  PRIMARY KEY (id )
  
);

create unique index XPKdata_audit on audit
(
 id
);