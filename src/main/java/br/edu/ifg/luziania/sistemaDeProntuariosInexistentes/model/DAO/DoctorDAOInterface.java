package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DAO;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.Doctor;

import java.sql.SQLException;

public interface DoctorDAOInterface {
    void insert(Doctor doctor) throws SQLException;
    Doctor findByCrm(Integer idUser, String crm);
}
