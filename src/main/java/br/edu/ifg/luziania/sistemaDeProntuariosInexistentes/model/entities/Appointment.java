package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities;

import java.sql.Time;
import java.util.Date;

public class Appointment {
    private Integer id_appointment;
    private Doctor doctor;
    private Date appointmentDate;
    private Time appointmentTime;

    public Appointment(Doctor doctor,
                       Date appointmentDate,
                       Time appointmentTime) {
        this.doctor = doctor;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
    }

    public Doctor getDoctor() { return doctor; }

    public Date getAppointmentDate() { return appointmentDate; }

    public Time getAppointmentTime() { return appointmentTime; }
}
