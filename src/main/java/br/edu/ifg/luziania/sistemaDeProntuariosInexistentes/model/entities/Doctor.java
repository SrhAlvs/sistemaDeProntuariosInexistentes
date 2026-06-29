package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities;

import static br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.UserValidator.validateCrm;

public class Doctor extends User {
    private String crm; //example: 123456-UF
    private DoctorSpecialty specialty;

    public Doctor (Integer id,
                   String name,
                   String email,
                   String password,
                   String crm) {
        super(id, name, email, password, "DOCTOR");
        setCrm(crm);
        this.specialty = specialty;
    }

    public Doctor (String name,
                   String email,
                   String crm,
                   DoctorSpecialty specialty) {
        super(name, email);
        setCrm(crm);
        this.specialty = specialty;
    }

    public Doctor(String name,
                  String email,
                  String password,
                  String type,
                  String crm,
                  DoctorSpecialty specialty) {
        super(name, email);
        setPassword(password);
        setType(type);
        setCrm(crm);
        this.specialty = specialty;
    }

    public String getCrm() { return crm; }
    public void setCrm(String crm) {
        validateCrm(crm);
        this.crm = crm;
    }

    public DoctorSpecialty getSpecialty() { return specialty; }
}
