package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities;

import java.time.LocalDate;

public class Agenda {
    private final LocalDate date;
    private final String time;
    private final String patientName;
    private final Patient patient;

    public Agenda(LocalDate date, String time, String patientName, Patient patient) {
        this.date = date;
        this.time = time;
        this.patientName = patientName;
        this.patient = patient;
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
}
