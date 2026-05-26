package br.edu.ifg.luziania.projetopiii.sistemaDeProntuariosInexistentes.model;

import static br.edu.ifg.luziania.projetopiii.sistemaDeProntuariosInexistentes.util.UserValidator.validateCrm;

public class Doctor extends User {
    private String crm; //example: 123456-UF

    public Doctor (Integer id,
                   String name,
                   String email,
                   String password,
                   String crm) {
        super(id, name, email, password, "DOCTOR");
        setCrm(crm);
    }

    public String getCrm() { return crm; }
    public void setCrm(String crm) {
        validateCrm(crm);
        this.crm = crm;
    }
}
