package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DAO;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.Patient;

public interface PatientDAOInterface {
    void insert(Patient patient);
    Patient findByCpf(Integer idUser, String cpf);
}
