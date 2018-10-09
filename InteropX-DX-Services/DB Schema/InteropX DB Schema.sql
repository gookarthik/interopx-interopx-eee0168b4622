CREATE SEQUENCE data_sets_dataset_id_seq;
CREATE SEQUENCE data_sources_ds_id_seq;
CREATE SEQUENCE extraction_resource_id_seq;
CREATE SEQUENCE extraction_id_seq;
CREATE SEQUENCE metadata_id_seq;
CREATE SEQUENCE group_id_seq;
CREATE SEQUENCE conflict_id_seq;

CREATE TABLE data_sets
(
    dataset_id integer NOT NULL DEFAULT nextval('data_sets_dataset_id_seq'::regclass),
    dataset_name character varying(255) ,
    schedule_frequency character varying(50),
    scheduled_time character varying(25),
    last_updated_ts timestamp without time zone NOT NULL DEFAULT timezone('UTC'::text, now()),
    PRIMARY KEY (dataset_id)
);


CREATE UNIQUE INDEX XPKddata_sets ON data_sets
(
	dataset_id
);


CREATE TABLE data_sources
(
    ds_id integer NOT NULL DEFAULT nextval('data_sources_ds_id_seq'::regclass),
    ds_name character varying(255),
    endpoint_url text,
    is_secure boolean,
    security_method character varying(255),
    connector_type character varying(255),
    credentials json,
    last_updated_ts timestamp without time zone NOT NULL DEFAULT timezone('UTC'::text, now()),
	ehr_admin_email character varying(100),
    database_server character varying(50),
    ehr_admin_contact character varying(25),
    PRIMARY KEY (ds_id)
);

CREATE UNIQUE INDEX XPKdata_sources ON data_sources 
(
	ds_id
);


CREATE TABLE dataset_datasource
(
    dataset_id integer NOT NULL,
    ds_id integer NOT NULL,
    PRIMARY KEY (dataset_id, ds_id),
	CONSTRAINT IX_1 FOREIGN KEY (dataset_id) REFERENCES data_sets (dataset_id),
	CONSTRAINT IX_2 FOREIGN KEY	(ds_id) REFERENCES data_sources (ds_id)
);

create unique index XPKDataset_datasource on dataset_datasource
(
	dataset_id,ds_id
);

CREATE TABLE extraction
(
    extraction_id integer NOT NULL DEFAULT nextval('extraction_id_seq'::regclass),
    dataset_id integer NOT NULL,
    dataset_name character varying(100),
    datasource_id integer NOT NULL,
    datasource_name character varying(100),
    files_location text,
    status character varying(100),
	data_processing_status		json,
    PRIMARY KEY (extraction_id)
);

create unique index XPKExtraction on extraction
(
	extraction_id
);

CREATE INDEX extraction_transmission_status ON extraction(((data_processing_status->>'transformationStatus')::BOOLEAN));
CREATE INDEX extraction_data_quality_status ON extraction(((data_processing_status->>'qualityStatus')::BOOLEAN));
CREATE INDEX extraction_patient_id_matching_status ON extraction(((data_processing_status->>'idMatchingStatus')::BOOLEAN));
CREATE INDEX extraction_deduplication_status ON extraction(((data_processing_status->>'deDuplicationStatus')::BOOLEAN));


CREATE TABLE extraction_metadata
(
    metadata_id integer NOT NULL DEFAULT nextval('metadata_id_seq'::regclass),
    extraction_id integer NOT NULL,
    metadata json,
    PRIMARY KEY (metadata_id),
	CONSTRAINT IX_3 FOREIGN KEY (extraction_id) REFERENCES extraction (extraction_id)
);

CREATE UNIQUE INDEX XPKextraction_metadata on extraction_metadata
(
	metadata_id
);
    
CREATE TABLE extracted_resources
(
    extraction_resource_id integer NOT NULL DEFAULT nextval('extraction_resource_id_seq'::regclass),
    extraction_id integer,
    location character varying(20),
    type character varying(20),
	data_processing_status		json,
	PRIMARY KEY (extraction_resource_id),
    CONSTRAINT IX_4 FOREIGN KEY (extraction_id) REFERENCES extraction (extraction_id) 
);

CREATE UNIQUE INDEX XPKextracted_resources on extracted_resources
(
	extraction_resource_id
);

CREATE TABLE conflict_groups
(
	group_id				serial,
	datasource_id				Integer not null,
	extraction_id				Integer not null,
	ix_patient_id		varchar(25),
	ix_patient_record			json,
	queried_elements		json,
	last_updated_ts		TIMESTAMP NOT NULL DEFAULT (NOW() at time zone 'UTC'),
	PRIMARY KEY(group_id)
);

CREATE UNIQUE INDEX XPKconflict_groups on conflict_groups
(
	group_id
);


CREATE TABLE conflicts
(
	conflict_id				serial,
	datasource_id				Integer not null,
	extraction_id				Integer not null,
	group_id 		Integer not null,
	record_id		Integer,
	matched_elements			json,
	mismatched_elements		json,
    score		DOUBLE PRECISION,
	conflicted_record			json,
    status				varchar(25),
	is_excluded			boolean,
	last_updated_ts		TIMESTAMP NOT NULL DEFAULT (NOW() at time zone 'UTC'),
	PRIMARY KEY(conflict_id),
	CONSTRAINT R_24 FOREIGN KEY (group_id) REFERENCES conflict_groups (group_id)
);

CREATE UNIQUE INDEX XPKconflicts on conflicts
(
	conflict_id
);

alter SEQUENCE data_sets_dataset_id_seq RESTART with 10001;
alter SEQUENCE data_sources_ds_id_seq RESTART with 10001;
alter SEQUENCE extraction_resource_id_seq RESTART with 10001;
alter SEQUENCE extraction_id_seq RESTART with 10001;
alter SEQUENCE metadata_id_seq RESTART with 10001;
alter SEQUENCE group_id_seq RESTART with 10001;
alter SEQUENCE conflict_id_seq RESTART with 10001;


CREATE TABLE patient
(
	id					serial,
	interopx_patient_id	VARCHAR(25),
	ds_id				Integer not null,
	extraction_id				Integer not null,
	actual_patient_id	VARCHAR(255),
	first_name			varchar(255),
	last_name			varchar(255),
	dob					varchar(25),
	gender				varchar(10),
	ds_name				varchar(255),
	data		json,
	data_processing_status		json,
	last_updated_ts		TIMESTAMP NOT NULL DEFAULT (NOW() at time zone 'UTC'),
	PRIMARY KEY(id),
	CONSTRAINT R_2 FOREIGN KEY (ds_id) REFERENCES data_sources (ds_id),
	CONSTRAINT R_3 FOREIGN KEY (extraction_id) REFERENCES extraction (extraction_id)
);

create unique index XPKpatient on patient
(
	id
);

CREATE INDEX patient_transmission_status ON patient(((data_processing_status->>'transformationStatus')::BOOLEAN));
CREATE INDEX patient_data_quality_status ON patient(((data_processing_status->>'qualityStatus')::BOOLEAN));
CREATE INDEX patient_patient_id_matching_status ON patient(((data_processing_status->>'idMatchingStatus')::BOOLEAN));
CREATE INDEX patient_deduplication_status ON patient(((data_processing_status->>'deDuplicationStatus')::BOOLEAN));


CREATE TABLE allergy_intolerance
(
	id					serial,
	interopx_ai_id		varchar(25),
	ds_id				Integer not null,
	extraction_id				Integer not null,
    interopx_patient_id			VARCHAR(25),
	actual_ai_id		varchar(255),
	ds_name				varchar(255),
	data		json,
	data_processing_status		json,
	last_updated_ts		TIMESTAMP NOT NULL DEFAULT (NOW() at time zone 'UTC'),
	PRIMARY KEY(id),
	CONSTRAINT R_4 FOREIGN KEY (ds_id) REFERENCES data_sources (ds_id),
	CONSTRAINT R_5 FOREIGN KEY (extraction_id) REFERENCES extraction (extraction_id)
);

create unique index XPKallergy_intolerance on allergy_intolerance
(
	id
);

CREATE INDEX allergy_intolerance_transmission_status ON allergy_intolerance(((data_processing_status->>'transformationStatus')::BOOLEAN));
CREATE INDEX allergy_intolerance_data_quality_status ON allergy_intolerance(((data_processing_status->>'qualityStatus')::BOOLEAN));
CREATE INDEX allergy_intolerance_patient_id_matching_status ON allergy_intolerance(((data_processing_status->>'idMatchingStatus')::BOOLEAN));
CREATE INDEX allergy_intolerance_deduplication_status ON allergy_intolerance(((data_processing_status->>'deDuplicationStatus')::BOOLEAN));

CREATE TABLE condition
(
	id				serial,
	ds_id				Integer not null,
	extraction_id				Integer not null,
	interopx_condition_id		varchar(25),
    interopx_patient_id			VARCHAR(25),
	actual_condition_id		varchar(255),
	ds_name				varchar(255),
	data		json,
	data_processing_status		json,
	last_updated_ts		TIMESTAMP NOT NULL DEFAULT (NOW() at time zone 'UTC'),
	PRIMARY KEY(id),
	CONSTRAINT R_6 FOREIGN KEY (ds_id) REFERENCES data_sources (ds_id),
	CONSTRAINT R_7 FOREIGN KEY (extraction_id) REFERENCES extraction (extraction_id)
);

create unique index XPKcondition on condition
(
	id
);

CREATE INDEX condition_transmission_status ON condition(((data_processing_status->>'transformationStatus')::BOOLEAN));
CREATE INDEX condition_data_quality_status ON condition(((data_processing_status->>'qualityStatus')::BOOLEAN));
CREATE INDEX condition_patient_id_matching_status ON condition(((data_processing_status->>'idMatchingStatus')::BOOLEAN));
CREATE INDEX condition_deduplication_status ON condition(((data_processing_status->>'deDuplicationStatus')::BOOLEAN));

CREATE TABLE encounter
(
	id				serial,
	ds_id				Integer not null,
	extraction_id				Integer not null,
	interopx_encounter_id		varchar(25),
    interopx_patient_id			VARCHAR(25),
	actual_encounter_id		varchar(255),
	ds_name				varchar(255),
	data		json,
	data_processing_status		json,
	last_updated_ts		TIMESTAMP NOT NULL DEFAULT (NOW() at time zone 'UTC'),
	PRIMARY KEY(id),
	CONSTRAINT R_8 FOREIGN KEY (ds_id) REFERENCES data_sources (ds_id),
	CONSTRAINT R_9 FOREIGN KEY (extraction_id) REFERENCES extraction (extraction_id)
);

create unique index XPKencounter on encounter
(
	id
);

CREATE INDEX encounter_transmission_status ON encounter(((data_processing_status->>'transformationStatus')::BOOLEAN));
CREATE INDEX encounter_data_quality_status ON encounter(((data_processing_status->>'qualityStatus')::BOOLEAN));
CREATE INDEX encounter_patient_id_matching_status ON encounter(((data_processing_status->>'idMatchingStatus')::BOOLEAN));
CREATE INDEX encounter_deduplication_status ON encounter(((data_processing_status->>'deDuplicationStatus')::BOOLEAN));

CREATE TABLE immunization
(
	id				serial,
	ds_id				Integer not null,
	extraction_id				Integer not null,
	interopx_immunization_id		varchar(25),
    interopx_patient_id			VARCHAR(25),
	actual_immunization_id		varchar(255),
	ds_name				varchar(255),
	data		json,
	data_processing_status		json,
	last_updated_ts		TIMESTAMP NOT NULL DEFAULT (NOW() at time zone 'UTC'),
	PRIMARY KEY(id),
	CONSTRAINT R_10 FOREIGN KEY (ds_id) REFERENCES data_sources (ds_id),
	CONSTRAINT R_11 FOREIGN KEY (extraction_id) REFERENCES extraction (extraction_id)
);

create unique index XPKimmunization on immunization
(
	id
);

CREATE INDEX immunization_transmission_status ON immunization(((data_processing_status->>'transformationStatus')::BOOLEAN));
CREATE INDEX immunization_data_quality_status ON immunization(((data_processing_status->>'qualityStatus')::BOOLEAN));
CREATE INDEX immunization_patient_id_matching_status ON immunization(((data_processing_status->>'idMatchingStatus')::BOOLEAN));
CREATE INDEX immunization_deduplication_status ON immunization(((data_processing_status->>'deDuplicationStatus')::BOOLEAN));

CREATE TABLE medication
(
	id				serial,
	ds_id				Integer not null,
	extraction_id				Integer not null,
	interopx_medication_id		varchar(25),
    interopx_patient_id			VARCHAR(25),
	actual_medication_id		varchar(255),
	ds_name				varchar(255),
	data		json,
	data_processing_status		json,
	last_updated_ts		TIMESTAMP NOT NULL DEFAULT (NOW() at time zone 'UTC'),
	PRIMARY KEY(id),
	CONSTRAINT R_12 FOREIGN KEY (ds_id) REFERENCES data_sources (ds_id),
	CONSTRAINT R_13 FOREIGN KEY (extraction_id) REFERENCES extraction (extraction_id)
);

create unique index XPKmedication on medication
(
	id
);

CREATE INDEX medication_transmission_status ON medication(((data_processing_status->>'transformationStatus')::BOOLEAN));
CREATE INDEX medication_data_quality_status ON medication(((data_processing_status->>'qualityStatus')::BOOLEAN));
CREATE INDEX medication_patient_id_matching_status ON medication(((data_processing_status->>'idMatchingStatus')::BOOLEAN));
CREATE INDEX medication_deduplication_status ON medication(((data_processing_status->>'deDuplicationStatus')::BOOLEAN));

CREATE TABLE procedure
(
	id				serial,
	ds_id				Integer not null,
	extraction_id				Integer not null,
	interopx_procedure_id		varchar(25),
    interopx_patient_id			VARCHAR(25),
	actual_procedure_id		varchar(255),
	ds_name				varchar(255),
	data		json,
	data_processing_status		json,
	last_updated_ts		TIMESTAMP NOT NULL DEFAULT (NOW() at time zone 'UTC'),
	PRIMARY KEY(id),
	CONSTRAINT R_14 FOREIGN KEY (ds_id) REFERENCES data_sources (ds_id),
	CONSTRAINT R_15 FOREIGN KEY (extraction_id) REFERENCES extraction (extraction_id)
);

create unique index XPKprocedure on procedure
(
	id
);

CREATE INDEX procedure_transmission_status ON procedure(((data_processing_status->>'transformationStatus')::BOOLEAN));
CREATE INDEX procedure_data_quality_status ON procedure(((data_processing_status->>'qualityStatus')::BOOLEAN));
CREATE INDEX procedure_patient_id_matching_status ON procedure(((data_processing_status->>'idMatchingStatus')::BOOLEAN));
CREATE INDEX procedure_deduplication_status ON procedure(((data_processing_status->>'deDuplicationStatus')::BOOLEAN));

CREATE TABLE lab_results
(
	id				serial,
	ds_id				Integer not null,
	extraction_id				Integer not null,
	interopx_lab_id		varchar(25),
    interopx_patient_id			VARCHAR(25),
	actual_lab_id		varchar(255),
	ds_name				varchar(255),
	category			varchar(255),
	data		json,
	data_processing_status		json,
	last_updated_ts		TIMESTAMP NOT NULL DEFAULT (NOW() at time zone 'UTC'),
	PRIMARY KEY(id),
	CONSTRAINT R_16 FOREIGN KEY (ds_id) REFERENCES data_sources (ds_id),
	CONSTRAINT R_17 FOREIGN KEY (extraction_id) REFERENCES extraction (extraction_id)
);

create unique index XPKlab_results on lab_results
(
	id
);

CREATE INDEX lab_results_transmission_status ON lab_results(((data_processing_status->>'transformationStatus')::BOOLEAN));
CREATE INDEX lab_results_data_quality_status ON lab_results(((data_processing_status->>'qualityStatus')::BOOLEAN));
CREATE INDEX lab_results_patient_id_matching_status ON lab_results(((data_processing_status->>'idMatchingStatus')::BOOLEAN));
CREATE INDEX lab_results_deduplication_status ON lab_results(((data_processing_status->>'deDuplicationStatus')::BOOLEAN));

CREATE TABLE vital_signs
(
	id				serial,
	ds_id				Integer not null,
	extraction_id				Integer not null,
	interopx_vitals_id		varchar(25),
    interopx_patient_id			VARCHAR(25),
	actual_vitals_id		varchar(255),
	ds_name				varchar(255),
	category			varchar(255),
	data		json,
	data_processing_status		json,
	last_updated_ts		TIMESTAMP NOT NULL DEFAULT (NOW() at time zone 'UTC'),
	PRIMARY KEY(id),
	CONSTRAINT R_18 FOREIGN KEY (ds_id) REFERENCES data_sources (ds_id),
	CONSTRAINT R_19 FOREIGN KEY (extraction_id) REFERENCES extraction (extraction_id)
);

create unique index XPKvital_signs on vital_signs
(
	id
);

CREATE INDEX vital_signs_transmission_status ON vital_signs(((data_processing_status->>'transformationStatus')::BOOLEAN));
CREATE INDEX vital_signs_data_quality_status ON vital_signs(((data_processing_status->>'qualityStatus')::BOOLEAN));
CREATE INDEX vital_signs_patient_id_matching_status ON vital_signs(((data_processing_status->>'idMatchingStatus')::BOOLEAN));
CREATE INDEX vital_signs_deduplication_status ON vital_signs(((data_processing_status->>'deDuplicationStatus')::BOOLEAN));

CREATE TABLE smoking
(
	id				serial,
	ds_id				Integer not null,
	extraction_id				Integer not null,
	interopx_smoking_id		varchar(25),
    interopx_patient_id			VARCHAR(25),
	actual_smoking_id		varchar(255),
	ds_name				varchar(255),
	category			varchar(255),
	data		json,
	data_processing_status		json,
	last_updated_ts		TIMESTAMP NOT NULL DEFAULT (NOW() at time zone 'UTC'),
	PRIMARY KEY(id),
	CONSTRAINT R_20 FOREIGN KEY (ds_id) REFERENCES data_sources (ds_id),
	CONSTRAINT R_21 FOREIGN KEY (extraction_id) REFERENCES extraction (extraction_id)
);

create unique index XPKsmoking on smoking
(
	id
);

CREATE INDEX smoking_transmission_status ON smoking(((data_processing_status->>'transformationStatus')::BOOLEAN));
CREATE INDEX smoking_data_quality_status ON smoking(((data_processing_status->>'qualityStatus')::BOOLEAN));
CREATE INDEX smoking_patient_id_matching_status ON smoking(((data_processing_status->>'idMatchingStatus')::BOOLEAN));
CREATE INDEX smoking_deduplication_status ON smoking(((data_processing_status->>'deDuplicationStatus')::BOOLEAN));

CREATE TABLE location
(
 id    serial,
 ds_id    Integer not null,
 extraction_id    Integer not null,
 interopx_location_id  varchar(25),
 interopx_patient_id   VARCHAR(25),
 actual_location_id  varchar(255),
 ds_name    varchar(255),
 category   varchar(255),
 data  json,
 data_processing_status		json,
 last_updated_ts  TIMESTAMP NOT NULL DEFAULT (NOW() at time zone 'UTC'),
 PRIMARY KEY(id),
 CONSTRAINT R_22 FOREIGN KEY (ds_id) REFERENCES data_sources (ds_id),
 CONSTRAINT R_23 FOREIGN KEY (extraction_id) REFERENCES extraction (extraction_id)
);

create unique index XPKlocation on location
(
 id
);

CREATE INDEX location_transmission_status ON location(((data_processing_status->>'transformationStatus')::BOOLEAN));
CREATE INDEX location_data_quality_status ON location(((data_processing_status->>'qualityStatus')::BOOLEAN));
CREATE INDEX location_patient_id_matching_status ON location(((data_processing_status->>'idMatchingStatus')::BOOLEAN));
CREATE INDEX location_deduplication_status ON location(((data_processing_status->>'deDuplicationStatus')::BOOLEAN));

CREATE TABLE audit
(
  id serial,
  category varchar(255),
  created TIMESTAMP NOT NULL  DEFAULT (NOW() at time zone 'UTC'),
  event_data json,
  event_name varchar(255),
  event_status varchar(255),
  resource_name varchar(255),
  PRIMARY KEY (id )
);

create unique index XPKaudit on audit
(
 id
);

CREATE TABLE data_source_quality
(
	id				 serial,
	ds_id			 Integer,
	score		     double precision,
	no_of_issues	 Integer,
	data_source_name varchar(255),
	PRIMARY KEY(id)
);

CREATE UNIQUE INDEX XPKdata_source_quality on data_source_quality
(
	id
);


CREATE TABLE data_quality
(
	id				  serial,
	et_id			  Integer,
	ds_id			  Integer,
	overall_score	  double precision,
	no_of_issues	  Integer,
	last_updated_time TIMESTAMP NOT NULL  DEFAULT (NOW() at time zone 'UTC'),
	data_source_quality_id Integer,
	PRIMARY KEY(id),
	CONSTRAINT R_33 FOREIGN KEY (data_source_quality_id) REFERENCES data_source_quality(id)
);

CREATE UNIQUE INDEX XPKdata_quality on data_quality
(
	id
);


CREATE TABLE data_quality_information
(
  id 			serial,
  resource_name varchar(255),
  record_id	    Integer,
  ix_patient_id varchar(255),
  score         Integer,
  no_of_issues  Integer,
  issue_list    json,
  last_updated_time TIMESTAMP NOT NULL  DEFAULT (NOW() at time zone 'UTC'),
  data_quality_id	Integer,
  PRIMARY KEY (id ),
  CONSTRAINT R_32 FOREIGN KEY (data_quality_id) REFERENCES data_quality (id)
);
create unique index XPKdata_quality_information on data_quality_information
(
	id
);


CREATE SEQUENCE group_details_id_seq;
CREATE TABLE group_details
(
  group_id bigint NOT NULL DEFAULT nextval('group_details_id_seq'::regclass),
  group_name varchar(255) NOT NULL,
  group_desc text,
  create_user_name varchar(255),
  createtimestamp TIMESTAMP NOT NULL  DEFAULT (NOW() at time zone 'UTC'),
  update_user_name varchar(255),
  lastupdttimestamp TIMESTAMP NOT NULL  DEFAULT (NOW() at time zone 'UTC'),
  create_timestamp TIMESTAMP NOT NULL  DEFAULT (NOW() at time zone 'UTC'),
  group_enabled boolean,
  update_timestamp TIMESTAMP NOT NULL  DEFAULT (NOW() at time zone 'UTC'),
  PRIMARY KEY (group_id),
  CONSTRAINT unq_group_name UNIQUE(group_name)
);
create unique index XPKgroup_details on group_details
(
 group_id
);

CREATE SEQUENCE user_details_id_seq;
CREATE TABLE user_details
(
  user_id bigint NOT NULL DEFAULT nextval('user_details_id_seq'::regclass),
  user_name varchar(255) NOT NULL,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  email_address varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  account_expired boolean,
  account_locked boolean,
  credentials_expired boolean,
  account_enabled boolean,
  createtimestamp TIMESTAMP NOT NULL  DEFAULT (NOW() at time zone 'UTC'),
  lastupdttimestamp TIMESTAMP NOT NULL  DEFAULT (NOW() at time zone 'UTC'),
  pwdlastupdtimestamp TIMESTAMP NOT NULL  DEFAULT (NOW() at time zone 'UTC'),
  PRIMARY KEY (user_id),
  CONSTRAINT unq_user_name UNIQUE(user_name)
);
create unique index XPKuser_details on user_details
(
 user_id
);


CREATE SEQUENCE user_groups_id_seq;
CREATE TABLE user_groups
(
  id bigint NOT NULL DEFAULT nextval('user_groups_id_seq'::regclass),
  user_user_id bigint NOT NULL,
  group_group_id bigint NOT NULL,
  create_user_name varchar(255),
  createtimestamp TIMESTAMP NOT NULL  DEFAULT (NOW() at time zone 'UTC'),
  update_user_name varchar(255),
  lastupdttimestamp TIMESTAMP NOT NULL  DEFAULT (NOW() at time zone 'UTC'),
  PRIMARY KEY (id),
  CONSTRAINT R_24 FOREIGN KEY (group_group_id) REFERENCES group_details (group_id),
  CONSTRAINT R_25 FOREIGN KEY (user_user_id) REFERENCES user_details (user_id)
);
create unique index XPKuser_groups on user_groups
(
 id
);

CREATE SEQUENCE user_login_id_seq;
CREATE TABLE user_login
(
  id bigint NOT NULL DEFAULT nextval('user_login_id_seq'::regclass),
  firstname varchar(255) NOT NULL,
  lastname varchar(255) NOT NULL,
  emailaddress varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  createtimestamp TIMESTAMP NOT NULL  DEFAULT (NOW() at time zone 'UTC'),
  lastupdttimestamp TIMESTAMP NOT NULL  DEFAULT (NOW() at time zone 'UTC'),
  pwdlastupdtimestamp TIMESTAMP NOT NULL  DEFAULT (NOW() at time zone 'UTC'),
  PRIMARY KEY (id)
);
create unique index XPKuser_login on user_login
(
 id
);


DROP INDEX IF EXISTS patient_id_idx;
CREATE INDEX patient_id_idx ON patient (id);

DROP INDEX IF EXISTS patient_interopx_id_idx;
CREATE INDEX patient_interopx_id_idx ON patient (interopx_patient_id);

DROP INDEX IF EXISTS patient_ds_id_idx;
CREATE INDEX patient_ds_id_idx ON patient (ds_id);

DROP INDEX IF EXISTS patient_et_id_idx;
CREATE INDEX patient_et_id_idx ON patient (extraction_id);

DROP INDEX IF EXISTS allergy_intolerance_id_idx;
CREATE INDEX allergy_intolerance_id_idx ON allergy_intolerance (id);

DROP INDEX IF EXISTS allergy_intolerance_interopx_id_idx;
CREATE INDEX allergy_intolerance_interopx_id_idx ON allergy_intolerance (interopx_ai_id);

DROP INDEX IF EXISTS allergy_intolerance_interopx_patient_id_idx;
CREATE INDEX allergy_intolerance_interopx_patient_id_idx ON allergy_intolerance (interopx_patient_id);

DROP INDEX IF EXISTS allergy_intolerance_ds_id_idx;
CREATE INDEX allergy_intolerance_ds_id_idx ON allergy_intolerance (ds_id);

DROP INDEX IF EXISTS allergy_intolerance_et_id_idx;
CREATE INDEX allergy_intolerance_et_id_idx ON allergy_intolerance (extraction_id);

DROP INDEX IF EXISTS condition_id_idx;
CREATE INDEX condition_id_idx ON condition (id);

DROP INDEX IF EXISTS condition_interopx_id_idx;
CREATE INDEX condition_interopx_id_idx ON condition (interopx_condition_id);

DROP INDEX IF EXISTS condition_interopx_patient_id_idx;
CREATE INDEX condition_interopx_patient_id_idx ON condition (interopx_patient_id);

DROP INDEX IF EXISTS condition_ds_id_idx;
CREATE INDEX condition_ds_id_idx ON condition (ds_id);

DROP INDEX IF EXISTS condition_et_id_idx;
CREATE INDEX condition_et_id_idx ON condition (extraction_id);

DROP INDEX IF EXISTS encounter_id_idx;
CREATE INDEX encounter_id_idx ON encounter (id);

DROP INDEX IF EXISTS encounter_interopx_id_idx;
CREATE INDEX encounter_interopx_id_idx ON encounter (interopx_encounter_id);

DROP INDEX IF EXISTS encounter_interopx_patient_id_idx;
CREATE INDEX encounter_interopx_patient_id_idx ON encounter (interopx_patient_id);

DROP INDEX IF EXISTS encounter_ds_id_idx;
CREATE INDEX encounter_ds_id_idx ON encounter (ds_id);

DROP INDEX IF EXISTS encounter_et_id_idx;
CREATE INDEX encounter_et_id_idx ON encounter (extraction_id);

DROP INDEX IF EXISTS immunization_id_idx;
CREATE INDEX immunization_id_idx ON immunization (id);

DROP INDEX IF EXISTS immunization_interopx_id_idx;
CREATE INDEX immunization_interopx_id_idx ON immunization (interopx_immunization_id);

DROP INDEX IF EXISTS immunization_interopx_patient_id_idx;
CREATE INDEX immunization_interopx_patient_id_idx ON immunization (interopx_patient_id);

DROP INDEX IF EXISTS immunization_ds_id_idx;
CREATE INDEX immunization_ds_id_idx ON immunization (ds_id);

DROP INDEX IF EXISTS immunization_et_id_idx;
CREATE INDEX immunization_et_id_idx ON immunization (extraction_id);

DROP INDEX IF EXISTS medication_id_idx;
CREATE INDEX medication_id_idx ON medication (id);

DROP INDEX IF EXISTS medication_interopx_id_idx;
CREATE INDEX medication_interopx_id_idx ON medication (interopx_medication_id);

DROP INDEX IF EXISTS medication_interopx_patient_id_idx;
CREATE INDEX medication_interopx_patient_id_idx ON medication (interopx_patient_id);

DROP INDEX IF EXISTS medication_ds_id_idx;
CREATE INDEX medication_ds_id_idx ON medication (ds_id);

DROP INDEX IF EXISTS medication_et_id_idx;
CREATE INDEX medication_et_id_idx ON medication (extraction_id);

DROP INDEX IF EXISTS procedure_id_idx;
CREATE INDEX procedure_id_idx ON procedure (id);

DROP INDEX IF EXISTS procedure_interopx_id_idx;
CREATE INDEX procedure_interopx_id_idx ON procedure (interopx_procedure_id);

DROP INDEX IF EXISTS procedure_interopx_patient_id_idx;
CREATE INDEX procedure_interopx_patient_id_idx ON procedure (interopx_patient_id);

DROP INDEX IF EXISTS procedure_ds_id_idx;
CREATE INDEX procedure_ds_id_idx ON procedure (ds_id);

DROP INDEX IF EXISTS procedure_et_id_idx;
CREATE INDEX procedure_et_id_idx ON procedure (extraction_id);

DROP INDEX IF EXISTS audit_id_idx;
CREATE INDEX audit_id_idx ON audit (id);

DROP INDEX IF EXISTS data_quality_id_idx;
CREATE INDEX data_quality_id_idx ON data_quality (id);

DROP INDEX IF EXISTS data_quality_information_id_idx;
CREATE INDEX data_quality_information_id_idx ON data_quality_information (id);