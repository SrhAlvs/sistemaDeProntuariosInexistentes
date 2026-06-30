package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DAO;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DB.DataBase;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.Doctor;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.DoctorSpecialty;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.MedicalRecord;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.LogWriter;

import java.sql.*;
import java.util.ArrayList;

public class MedicalRecordDAO implements MedicalRecordDAOInterface {

    Connection connection = null;

    public MedicalRecordDAO() {this.connection = DataBase.getConnection();}

    @Override
    public void insert(MedicalRecord medicalRecord) {
        String query = """
                INSERT INTO medical_record (id_appointment, path)
                VALUES (?, ?)
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);

            preparedStatement.setInt(1, medicalRecord.getIdAppointment());
            preparedStatement.setString(2, medicalRecord.getPath());

            assert preparedStatement != null; // garante pro sql que o bglh não é nulo (valor do preparedStatement)
            preparedStatement.executeUpdate(); // pra INSERT, UPDATE, DELETE

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LogWriter.write("[ERRO | INSERT] Erro ao realizar o insert na tabela de Prontuários (fodeo).");
            }
        }

    }

    @Override
    public MedicalRecord findByIdAppointment(Integer idAppointment) {
        String query = """
                SELECT * FROM medical_record
                WHERE id_appointment = ?;
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idAppointment);

            ResultSet resultSet = preparedStatement.executeQuery();

            MedicalRecord medicalRecord = null;

            while (resultSet.next()) {
                medicalRecord = new MedicalRecord(
                        resultSet.getInt("id_appointment"),
                        resultSet.getString("path")
                );
            }

            if (medicalRecord == null) {
                medicalRecord = new MedicalRecord(
                        -1,
                        " "
                );
            }

            return medicalRecord;


        } catch (SQLException e) {
            LogWriter.write("[ERRO | SELECT] Erro ao procurar pela consulta na tabela de Prontuário (fodeo).");

            return null;
        }
    }

    public ArrayList<MedicalRecord> findAllByCRM(String crm) {
        String query = """
                SELECT * FROM medical_record
                JOIN appointment ON appointment.id_appointment = medical_record.id_appointment
                WHERE appointment.crm = ?
        """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);

            preparedStatement.setString(1, crm);

            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<MedicalRecord> medicalRecord = new ArrayList<>();

            while (resultSet.next()) {
                medicalRecord.add(new MedicalRecord(
                        resultSet.getInt("id_appointment"),
                        resultSet.getString("path")
                ));
            }

            return medicalRecord;
        } catch (SQLException e) {
            LogWriter.write("[ERRO | SELECT] Erro ao procurar pelo CRM na tabela de Prontuário (fodeo).");

            return null;
        }
    }
}
