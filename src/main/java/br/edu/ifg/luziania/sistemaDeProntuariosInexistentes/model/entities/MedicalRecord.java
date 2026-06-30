package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities;

public class MedicalRecord {
    private Integer idAppointment;
    private String path;

    public MedicalRecord(Integer idAppointment, String path) {
        this.idAppointment = idAppointment;
        this.path = path;
    }

    public Integer getIdAppointment() {
        return idAppointment;
    }

    public void setIdAppointment(Integer idAppointment) {
        this.idAppointment = idAppointment;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
