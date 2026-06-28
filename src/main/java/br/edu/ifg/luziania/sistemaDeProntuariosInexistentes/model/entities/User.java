package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities;

import static br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.UserValidator.*;

public class User {
    private Integer idUser;
    private String name, email, password, type;

    public User(Integer idUser,
                String name,
                String email,
                String password,
                String type) {
        this.idUser = idUser;
        setName(name);
        setEmail(email);
        setPassword(password);
        setType(type);
    }

    public User(Integer idUser,
                String name,
                String email,
                String type) {
        this.idUser = idUser;
        setName(name);
        setEmail(email);
        setType(type);
    }

    public User(String name, String email) {
        setName(name);
        setEmail(email);
    }

    public Integer getIdUser() { return idUser; }

    public String getName() { return name; }
    public void setName(String name) {
        validateName(name);
        this.name = name;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        validateEmail(email);
        this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) {
        validatePassword(password);
        this.password = password;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = (type == null) ? "--não informado--" : type; }

}
