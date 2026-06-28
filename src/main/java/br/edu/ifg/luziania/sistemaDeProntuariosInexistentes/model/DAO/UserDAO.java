package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DAO;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.User;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DB.DataBase;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.LogWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements UserDAOInterface {
    private Connection connection = null;

    public UserDAO() {
        this.connection = DataBase.getConnection();
    }

    //PreparedStatment é o guarda-caixão para os valores de verdade (são as ?)
    //ResultSet é o resultado da pesquisa do SELECT


    @Override
    public User insert(User user) {
        String query = "INSERT INTO user (name, email, password, type) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getType());

            assert preparedStatement != null; // garante pro sql que o bglh não é nulo (valor do preparedStatement)
            preparedStatement.executeUpdate(); //pra INSERT, UPDATE, DELETE

            connection.commit();

            return findByEmail(user.getEmail());
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LogWriter.write("[ERRO | INSERT] Erro ao realizar o insert na tabela de Usuário (fodeo).");
            }
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        String query = "SELECT * FROM user WHERE email = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            User user = null;

            while (resultSet.next()) {
                user = new User(
                    resultSet.getInt("id_user"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("type")
                );
            }

            return user;
        } catch (SQLException e) {
            LogWriter.write("[ERRO | SELECT] Erro ao realizar o select na tabela de Usuário (fodeo).");

            return null;
        }
    }
}
