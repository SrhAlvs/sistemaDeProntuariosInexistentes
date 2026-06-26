package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DAO;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.Doctor;

public interface DoctorDAOInterface {
    void insert(Doctor doctor);
    Doctor findByCrm(Integer idUser, String crm);
}
