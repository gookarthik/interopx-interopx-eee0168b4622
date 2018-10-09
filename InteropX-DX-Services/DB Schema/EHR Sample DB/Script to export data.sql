-- Export PatientData
COPY (
    select * from patient
) TO '{EXPORT_DIR}\Patient.csv' DELIMITER ',' CSV HEADER;  -- {EXPORT_DIR} will be replaced while executing

-- Export AllergyIntoleranceData
COPY (
    select * from allergy_intolerance	--table name
) TO '{EXPORT_DIR}\AllergyIntolerance.csv' DELIMITER ',' CSV HEADER;

-- Export EncounterData
COPY (
    select * from encounter	--table name
) TO '{EXPORT_DIR}\Encounter.csv' DELIMITER ',' CSV HEADER;

-- Export ImmunizationData
COPY (
    select * from immunization	--table name
) TO '{EXPORT_DIR}\Immunization.csv' DELIMITER ',' CSV HEADER;

-- Export LabResultsData
COPY (
    select * from lab_results	--table name
) TO '{EXPORT_DIR}\LabResults.csv' DELIMITER ',' CSV HEADER;

-- Export LocationData
COPY (
    select * from location	--table name
) TO '{EXPORT_DIR}\Location.csv' DELIMITER ',' CSV HEADER;

-- Export MedicationData
COPY (
    select * from medication	--table name
) TO '{EXPORT_DIR}\Medication.csv' DELIMITER ',' CSV HEADER;

-- Export ProblemData
COPY (
    select * from problem	--table name
) TO '{EXPORT_DIR}\Problem.csv' DELIMITER ',' CSV HEADER;

-- Export ProcedureData
COPY (
    select * from procedure	--table name
) TO '{EXPORT_DIR}\Procedure.csv' DELIMITER ',' CSV HEADER;

-- Export SmokingData
COPY (
    select * from smoking	--table name
) TO '{EXPORT_DIR}\Smoking.csv' DELIMITER ',' CSV HEADER;

-- Export VitalsData
COPY (
    select * from vitals	--table name
) TO '{EXPORT_DIR}\Vitals.csv' DELIMITER ',' CSV HEADER;