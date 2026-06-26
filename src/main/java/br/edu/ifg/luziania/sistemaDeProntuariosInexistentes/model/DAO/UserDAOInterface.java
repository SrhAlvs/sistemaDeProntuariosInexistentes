package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DAO;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.User;

public interface UserDAOInterface {
    User insert(User user);
    User findByEmail(String email);
}
