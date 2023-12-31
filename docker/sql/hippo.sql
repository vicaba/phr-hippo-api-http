CREATE DATABASE hippodb;
\c hippodb;

CREATE TABLE record (
  id uuid NOT NULL,
  created_at timestamp with time zone NOT NULL,
  updated_at timestamp with time zone,
  patient_id uuid NOT NULL,
  headline text NOT NULL,
  body text
);

ALTER TABLE record
ADD CONSTRAINT record_id_pk PRIMARY KEY (id);

INSERT INTO record (id, created_at, updated_at, patient_id, headline, body) VALUES
    ('292a485f-a56a-4938-8f1a-bbbbbbbbbbb1', '2023-08-20 23:00:00', '2023-08-20 23:00:00', '292a485f-a56a-4938-8f1a-bbbbbbbbbbb1', 'Sudden back pain', 'It started hurting just when I woke up. I do not recall doing anything unusual yesterday');
COMMIT;
