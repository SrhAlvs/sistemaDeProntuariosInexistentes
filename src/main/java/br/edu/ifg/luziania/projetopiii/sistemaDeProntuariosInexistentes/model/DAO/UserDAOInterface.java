package br.edu.ifg.luziania.projetopiii.sistemaDeProntuariosInexistentes.model.DAO;

import br.edu.ifg.luziania.projetopiii.sistemaDeProntuariosInexistentes.model.entities.User;

public interface UserDAOInterface {
    void insert();
    User findById();
}
