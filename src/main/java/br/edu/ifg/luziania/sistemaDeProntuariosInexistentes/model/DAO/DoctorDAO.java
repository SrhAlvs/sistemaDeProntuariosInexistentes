package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DAO;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DB.DataBase;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.Doctor;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.DoctorSpecialty;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.User;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.LogWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorDAO implements DoctorDAOInterface {
    private Connection connection = null;
    private UserDAO userDAO = new UserDAO();

    public DoctorDAO() {
        this.connection = DataBase.getConnection();
    }

    //PreparedStatment é o guarda-caixão para os valores de verdade (são as ?)
    //ResultSet é o resultado da pesquisa do SELECT

    @Override
    public void insert(Doctor doctor) {
        User user = userDAO.insert(doctor);

        String query = "INSERT INTO doctor (crm, id_user, specialty) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);

            preparedStatement.setString(1, doctor.getCrm());
            preparedStatement.setInt(2, user.getIdUser());
            preparedStatement.setString(3, doctor.getSpecialty().name());

            assert preparedStatement != null; // garante pro sql que o bglh não é nulo (valor do preparedStatement)
            preparedStatement.executeUpdate(); //pra INSERT, UPDATE, DELETE

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LogWriter.write("[ERRO | INSERT] Erro ao realizar o insert na tabela de Médico (fodeo).");
            }
        }
    }

    @Override
    public Doctor findByCrm(Integer idUser, String crm) {
        String query = "SELECT * FROM doctor JOIN user ON user.id_user = ? WHERE crm = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idUser);
            preparedStatement.setString(2, crm);

            ResultSet resultSet = preparedStatement.executeQuery(query);

            Doctor doctor = null;

            while (resultSet.next()) {
                doctor = new Doctor(
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("crm"),
                        DoctorSpecialty.valueOf(resultSet.getString("specialty"))
                );
            }

            return doctor;
        } catch (SQLException e) {
            LogWriter.write("[ERRO | SELECT] Erro ao realizar o select na tabela de Médico (fodeo).");

            return null;
        }
    }

}
