package br.edu.ifg.luziania.projetopiii.sistemaDeProntuariosInexistentes.repository;

import br.edu.ifg.luziania.projetopiii.sistemaDeProntuariosInexistentes.util.LogWriter;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DataBase {
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Properties properties = loadProperties();
                String url = properties.getProperty("dburl");
                connection = DriverManager.getConnection(url, properties);
            } catch (SQLException | NullPointerException e) {
                LogWriter.write("[ERRO | BANCO DE DADOS] Erro ao conectar com o banco de dados.");
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

    public static Properties loadProperties() {
        try (FileInputStream fis = new FileInputStream("db.properties")) {
            Properties properties = new Properties();
            properties.load(fis);
            return properties;
        } catch (IOException e) {
            LogWriter.write("[ERRO | BANCO DE DADOS] Erro ao carregar as propriedades do banco de dados.");
            return null;
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LogWriter.write("[ERRO | BANCO DE DADOS] Erro ao encerrar a conexão com o banco de dados.");
                throw new RuntimeException();
            }
        }
    }

    public static void closeStatement(Statement statement, String local) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LogWriter.write("[ERRO | BANCO DE DADOS] Erro ao encerrar um 'statement' (" + local + ").");
                throw new RuntimeException();
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet, String local) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LogWriter.write("[ERRO | BANCO DE DADOS] Erro ao encerrar um 'result set' (" + local + ").");
                throw new RuntimeException();
            }
        }
    }
}
