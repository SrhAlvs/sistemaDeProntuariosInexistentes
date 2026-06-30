package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DAO;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DB.DataBase;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.Appointment;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.Doctor;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.DoctorSpecialty;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.LogWriter;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class AppointmentDAO implements AppointmentDAOInterface{
    private Connection connection = null;

    public AppointmentDAO() {
        this.connection = DataBase.getConnection();
    }

    //PreparedStatment é o guarda-caixão para os valores de verdade (são as ?)
    //ResultSet é o resultado da pesquisa do SELECT

    @Override
    public void insert(Appointment appointment) {
        String query = "INSERT INTO appointment(crm, cpf, date, time) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);

            preparedStatement.setString(1, appointment.getCrm());
            preparedStatement.setString(2, appointment.getCpf());
            preparedStatement.setDate(3, java.sql.Date.valueOf(appointment.getAppointmentDate()));
            preparedStatement.setString(4, appointment.getAppointmentTime());

            assert preparedStatement != null; // garante pro sql que o bglh não é nulo (valor do preparedStatement)
            preparedStatement.executeUpdate(); //pra INSERT, UPDATE, DELETE

            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LogWriter.write("[ERRO | INSERT] Erro ao realizar o insert na tabela de Consultas (fodeo).");
            }
        }
    }

    @Override
    public ArrayList<Appointment> findAppointment(String crm, LocalDate appointmentDate) {

        String query = """
            SELECT *
            FROM appointment
            WHERE crm = ? AND date = ?
            """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, crm);
            preparedStatement.setDate(2, Date.valueOf(appointmentDate));

            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Appointment> appointment = new ArrayList<>();

            while (resultSet.next()) {
                appointment.add(
                        new Appointment(
                                resultSet.getString("crm"),
                                resultSet.getString("cpf"),
                                resultSet.getDate("date").toLocalDate(),
                                resultSet.getString("time")
                        )
                );
            }

            return appointment;
        } catch (SQLException e) {
            LogWriter.write("[ERRO | SELECT] Erro ao procurar pela data na tabela de Consultas (fodeo).");

            return null;
        }

    }
}
