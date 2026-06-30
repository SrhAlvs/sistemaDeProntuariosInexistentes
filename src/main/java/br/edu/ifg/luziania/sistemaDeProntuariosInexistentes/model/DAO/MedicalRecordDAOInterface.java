package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DAO;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.MedicalRecord;

public interface MedicalRecordDAOInterface {
    void insert(MedicalRecord medicalRecord);
    MedicalRecord findByIdAppointment(Integer idAppointment);
}
