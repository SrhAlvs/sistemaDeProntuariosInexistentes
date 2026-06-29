package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DAO;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.Doctor;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.User;

import java.sql.SQLException;

public interface DoctorDAOInterface {
    void insert(Doctor doctor) throws SQLException;
    Doctor findByCrm(String crm);
    Doctor findByEmail(String email);
}
