package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DAO;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.Appointment;

import java.time.LocalDate;
import java.util.ArrayList;

public interface AppointmentDAOInterface {
    void insert(Appointment appointment);
    ArrayList<Appointment> findAppointment(String crm, LocalDate appointmentDate);
}
