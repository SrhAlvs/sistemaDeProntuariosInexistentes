package br.edu.ifg.luziania.projetopiii.sistemaDeProntuariosInexistentes.model.entities;

import static br.edu.ifg.luziania.projetopiii.sistemaDeProntuariosInexistentes.util.UserValidator.*;

public class User {
    private final Integer id;
    private String name, email, password, type;

    public User(Integer id,
                String name,
                String email,
                String password,
                String type) {
        this.id = id;
        setName(name);
        setEmail(email);
        setPassword(password);
        setType(type);
    }

    public Integer getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) {
        validateName(name);
        this.name = name;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        validateEmail(email);
        this.email = email; }

    public String setPassword() { return password; }
    public void setPassword(String password) {
        validatePassword(password);
        this.password = password;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = (type == null) ? "--não informado--" : type; }

}
