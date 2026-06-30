package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DAO;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DB.DataBase;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.*;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.LogWriter;

import java.sql.*;
import java.util.ArrayList;

public class DoctorDAO implements DoctorDAOInterface {
    private Connection connection = null;
    private UserDAO userDAO = new UserDAO();

    public DoctorDAO() {
        this.connection = DataBase.getConnection();
    }

    //PreparedStatment é o guarda-caixão para os valores de verdade (são as ?)
    //ResultSet é o resultado da pesquisa do SELECT

    @Override
    public void insert(Doctor doctor) throws SQLException {
        User user = userDAO.insert(doctor);
        if (user == null) {
            LogWriter.write("[ERRO | INSERT] Falha ao inserir médico no banco de dados (usuário nulo).");
            throw new SQLException("Erro ao inserir usuário (médico).");
        }

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
    public Doctor findByCrm(String crm) {
        String query = "SELECT * FROM doctor JOIN user ON user.id_user = doctor.id_user WHERE crm = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, crm);

            ResultSet resultSet = preparedStatement.executeQuery();

            Doctor doctor = null;

            while (resultSet.next()) {
                doctor = new Doctor(
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        "DOCTOR",
                        resultSet.getString("crm"),
                        DoctorSpecialty.valueOf(resultSet.getString("specialty"))
                );
            }

            return doctor;
        } catch (SQLException e) {
            LogWriter.write("[ERRO | SELECT] Erro ao procurar pelo CRM na tabela de Médico (fodeo).");

            return null;
        }
    }

    @Override
    public Doctor findByEmail(String email) {

        String query = "SELECT * FROM user JOIN doctor ON user.id_user = doctor.id_user WHERE email = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            Doctor doctor = null;

            while (resultSet.next()) {
                doctor = new Doctor(
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        "DOCTOR",
                        resultSet.getString("crm"),
                        DoctorSpecialty.valueOf(resultSet.getString("specialty"))
                );
            }

            return doctor;
        } catch (SQLException e) {
            LogWriter.write("[ERRO | SELECT] Erro ao procurar pelo Email na tabela de Médico (fodeo).");

            return null;
        }
    }

    @Override
    public Doctor findBySpecialty(DoctorSpecialty specialty) {
        String query = "SELECT * FROM doctor JOIN user ON user.id_user = doctor.id_user WHERE specialty = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, specialty.name());

            ResultSet resultSet = preparedStatement.executeQuery();

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
            LogWriter.write("[ERRO | SELECT] Erro ao procurar pela especialidade na tabela de Médico (fodeo).");

            return null;
        }
    }

    public ArrayList<Patient> findAllPatientsByDoctor(Doctor doctor) {

        String query = """
            SELECT DISTINCT
                u.name,
                u.email,
                p.cpf
            FROM patient p
            JOIN user u
                ON p.id_user = u.id_user
            JOIN appointment a
                ON a.cpf = p.cpf
            WHERE a.crm = ?
            """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, doctor.getCrm());

            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Patient> patients = new ArrayList<>();

            while (resultSet.next()) {

                patients.add(
                        new Patient(
                                resultSet.getString("name"),
                                resultSet.getString("email"),
                                resultSet.getString("cpf")
                        )
                );

            }

            return patients;

        } catch (SQLException e) {

            LogWriter.write(
                    "[ERRO | SELECT] Erro ao selecionar todos os pacientes de um médico por CRM."
            );

            return new ArrayList<>();
        }
    }
}
