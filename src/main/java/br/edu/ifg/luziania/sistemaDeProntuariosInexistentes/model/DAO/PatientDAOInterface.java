package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DAO;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.Patient;

import java.sql.SQLException;

public interface PatientDAOInterface {
    void insert (Patient patient) throws SQLException;
    Patient findByCpf(String cpf);
    Patient findByEmail(String email);
}
