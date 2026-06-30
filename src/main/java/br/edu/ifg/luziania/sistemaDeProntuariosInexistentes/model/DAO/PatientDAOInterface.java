package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DAO;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.Appointment;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.Doctor;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.Patient;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PatientDAOInterface {
    void insert (Patient patient) throws SQLException;
    Patient findByCpf(String cpf);
    Patient findByEmail(String email);
    ArrayList<Doctor> findAllDoctorsByPatient(Patient patient);
}
