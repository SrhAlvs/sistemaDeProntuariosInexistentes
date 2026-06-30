package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities;

import java.time.LocalDate;

public class Appointment {
    private Integer id_appointment;
    private String crm;
    private String cpf;
    private LocalDate appointmentDate;
    private String appointmentTime;

    public Appointment(String crm,
                       String cpf,
                       LocalDate appointmentDate,
                       String appointmentTime) {
        this.crm = crm;
        this.cpf = cpf;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
    }

    public Appointment(Integer id_appointment,
                       String crm,
                       String cpf,
                       LocalDate appointmentDate,
                       String appointmentTime) {
        this.id_appointment = id_appointment;
        this.crm = crm;
        this.cpf = cpf;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
    }

    public String getCrm() { return crm; }

    public String getCpf() { return cpf; }

    public Integer getIdAppointment() { return id_appointment; }

    public LocalDate getAppointmentDate() { return appointmentDate; }

    public String getAppointmentTime() { return appointmentTime; }
}
