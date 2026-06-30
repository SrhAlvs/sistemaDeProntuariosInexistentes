package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities;

import java.time.LocalDate;

public class Agenda {
    private final LocalDate date;
    private final String time;
    private final String patientName;
    private final Patient patient;
    private final String doctorName;
    private final Doctor doctor;
    private final String doctorSpecialty;

    public Agenda(LocalDate date,
                  String time,
                  String patientName,
                  Patient patient) {
        this.date = date;
        this.time = time;
        this.patientName = patientName;
        this.patient = patient;
        this.doctorName = null;
        this.doctor = null;
        this.doctorSpecialty = null;
    }

    public Agenda(LocalDate date,
                  String time,
                  Doctor doctor,
                  String doctorName) {
        this.date = date;
        this.time = time;
        this.doctor = doctor;
        this.doctorName = doctorName;
        this.doctorSpecialty = doctor.getSpecialty().getSpecialtyName();
        this.patient = null;
        this.patientName = null;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getPatientName() {
        return patientName;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getDoctorSpecialty() {return doctorSpecialty;}
}
