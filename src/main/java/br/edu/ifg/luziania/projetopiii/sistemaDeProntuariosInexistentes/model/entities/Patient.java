package br.edu.ifg.luziania.projetopiii.sistemaDeProntuariosInexistentes.model.entities;

import static br.edu.ifg.luziania.projetopiii.sistemaDeProntuariosInexistentes.util.UserValidator.validateCpf;

public class Patient extends User {
    private String cpf; //example: 11122233322

    public Patient (Integer id,
                   String name,
                   String email,
                   String password,
                   String cpf) {
        super(id, name, email, password, "PATIENT");
        setCpf(cpf);
    }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) {
        validateCpf(cpf);
        this.cpf = cpf;
    }
}
