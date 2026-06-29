package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DAO;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DB.DataBase;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.Patient;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.User;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.LogWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientDAO implements PatientDAOInterface {
    private Connection connection = null;
    private UserDAO userDAO = new UserDAO();

    public PatientDAO() {
        this.connection = DataBase.getConnection();
    }

    //PreparedStatment é o guarda-caixão para os valores de verdade (são as ?)
    //ResultSet é o resultado da pesquisa do SELECT

    @Override
    public void insert(Patient patient) throws SQLException {

        User user = userDAO.insert(patient);
        if (user == null) {
            LogWriter.write("[ERRO | INSERT] Falha ao inserir paciente no banco de dados (usuário nulo).");
            throw new SQLException("Erro ao inserir usuário (paciente).");
        }

        String query = "INSERT INTO patient (cpf, id_user) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);

            preparedStatement.setString(1, patient.getCpf());
            preparedStatement.setInt(2, user.getIdUser());

            assert preparedStatement != null; // garante pro sql que o bglh não é nulo (valor do preparedStatement)
            preparedStatement.executeUpdate(); //pra INSERT, UPDATE, DELETE

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LogWriter.write("[ERRO | INSERT] Erro ao realizar o insert na tabela de Paciente (fodeo).");
            }
        }
    }

    @Override
    public Patient findByCpf(String cpf) {
        String query = "SELECT * FROM patient JOIN user ON user.id_user = patient.id_user WHERE cpf = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, cpf);

            ResultSet resultSet = preparedStatement.executeQuery();

            Patient patient = null;

            while (resultSet.next()) {
                patient = new Patient(
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        "PATIENT",
                        resultSet.getString("cpf")
                );
            }

            return patient;
        } catch (SQLException e) {
            LogWriter.write("[ERRO | SELECT] Erro ao procurar pelo CPF na tabela de Paciente (fodeo).");

            return null;
        }
    }

    @Override
    public Patient findByEmail(String email) {
        String query = "SELECT * FROM patient JOIN user ON user.id_user = patient.id_user WHERE email = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            Patient patient = null;

            while (resultSet.next()) {
                patient = new Patient(
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        "PATIENT",
                        resultSet.getString("cpf")
                );
            }

            return patient;
        } catch (SQLException e) {
            LogWriter.write("[ERRO | SELECT] Erro ao procurar pelo Email na tabela de Paciente (fodeo).");

            return null;
        }
    }
}
