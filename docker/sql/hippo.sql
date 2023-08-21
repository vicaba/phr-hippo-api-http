CREATE DATABASE hippodb;
\c hippodb;

CREATE TABLE record (
  id uuid NOT NULL,
  created_at timestamp without time zone NOT NULL,
  updated_at timestamp without time zone,
  patient_id uuid NOT NULL,
  headline text NOT NULL,
  body text
);

ALTER TABLE record
ADD CONSTRAINT record_id_pk PRIMARY KEY (record_id);
