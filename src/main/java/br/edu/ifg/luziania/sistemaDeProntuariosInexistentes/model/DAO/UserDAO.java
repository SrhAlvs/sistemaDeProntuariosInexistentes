package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DAO;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.User;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.repository.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO implements UserDAOInterface {
    private Connection connection = null;

    public UserDAO() {
        this.connection = DataBase.getConnection();
    }

    @Override
    public void insert() {
        PreparedStatement preparedStatement = null;

        try {
            connection.setAutoCommit(false);

            /*if () {

            } else {

            }*/
            assert preparedStatement != null;
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("[ERRO | INSERT] Erro ao realizar o insert (fodeo).");
            }
        }
    }

    @Override
    public User findById() {
        return null;
    }
}
