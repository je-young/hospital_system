DROP DATABASE IF EXISTS hospital_management;
CREATE DATABASE hospital_management;
USE hospital_management;

-- 환자 (Patient) 테이블
CREATE TABLE patient
(
    patientid INT AUTO_INCREMENT,
    name      VARCHAR(50)  NOT NULL,
    birthdate DATE         NOT NULL,
    phone     VARCHAR(20)  NOT NULL UNIQUE,
    address   VARCHAR(100) NOT NULL,
    createdat DATETIME DEFAULT NOW(),
    CONSTRAINT pk_patient PRIMARY KEY (patientid)
);

-- 의사 (Doctor) 테이블
CREATE TABLE doctor
(
    doctorid  INT AUTO_INCREMENT,
    name      VARCHAR(50) NOT NULL,
    specialty VARCHAR(50) NOT NULL,
    phone     VARCHAR(20) NOT NULL,
    createdat DATETIME DEFAULT NOW(),
    CONSTRAINT pk_doctor PRIMARY KEY (doctorid)
);

-- 진료 예약 (Appointment) 테이블
CREATE TABLE appointment
(
    appointmentid   INT AUTO_INCREMENT,
    patientid       INT  NOT NULL,
    doctorid        INT  NOT NULL,
    appointmentdate DATE NOT NULL,
    appointmenttime TIME NOT NULL,
    status          tinyint  default 1,
    createdat       DATETIME DEFAULT NOW(),
    CONSTRAINT pk_appointment PRIMARY KEY (appointmentid),
    CONSTRAINT fk_appointment_patient FOREIGN KEY (patientid) REFERENCES patient (patientid) ON DELETE CASCADE,
    CONSTRAINT fk_appointment_doctor FOREIGN KEY (doctorid) REFERENCES doctor (doctorid) ON DELETE CASCADE
); 
