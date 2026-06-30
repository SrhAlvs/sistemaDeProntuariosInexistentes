CREATE DATABASE SistemaDeProntuariosInexistentes;

USE SistemaDeProntuariosInexistentes;

CREATE TABLE user (
    id_user INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(20) NOT NULL,
    type ENUM('DOCTOR', 'PATIENT')
);

CREATE TABLE doctor (
    crm VARCHAR(10) PRIMARY KEY,
    id_user INT UNSIGNED,
    specialty ENUM('ANESTESIOLOGIA', 'CARDIOLOGIA', 'DERMATOLOGIA', 'ENDOCRINOLOGIA', 'GASTROENTEROLOGIA', 'GERIATRIA', 'GINECOLOGIA_E_OBSTETRICIA', 'HEMATOLOGIA', 'INFECTOLOGIA', 'MASTOLOGIA', 'MEDICINA_DO_TRABALHO', 'NEFROLOGIA', 'NEUROLOGIA', 'OFTALMOLOGIA', 'ONCOLOGIA', 'ORTOPEDIA_E_TRAUMATOLOGIA', 'OTORRINOLARINGOLOGIA', 'PEDIATRIA', 'PNEUMOLOGIA', 'PSICOLOGIA', 'PSIQUIATRIA', 'RADIOLOGIA', 'REUMATOLOGIA', 'UROLOGIA'),
    CONSTRAINT FK_doctor_id_user FOREIGN KEY (id_user) REFERENCES user(id_user)
);

CREATE TABLE patient (
    cpf VARCHAR(14) PRIMARY KEY,
    id_user INT UNSIGNED,
    CONSTRAINT FK_patient_id_user FOREIGN KEY (id_user) REFERENCES user(id_user)
);

CREATE TABLE appointment (
    id_appointment INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    crm VARCHAR(10) NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    date DATE,
    time ENUM('09:00', '09:30', '10:00', '10:30', '11:00', '11:30', '12:00', '12:30', '13:00', '13:30', '14:00', '14:30', '15:00', '15:30', '16:00', '16:30', '17:00', '17:30', '18:00', '18:30'),
    CONSTRAINT FK_appointment_crm FOREIGN KEY (crm) REFERENCES doctor(crm),
    CONSTRAINT FK_appointment_cpf FOREIGN KEY (cpf) REFERENCES patient(cpf)
);

DROP DATABASE SistemaDeProntuariosInexistentes;

USE SistemaDeProntuariosInexistentes;

-- =====================================
-- USUÁRIOS MÉDICOS
-- =====================================

INSERT INTO user (name, email, password, type) VALUES
                                                   ('João Silva', 'joao.silva@hospital.com', 'Medico@01', 'DOCTOR'),
                                                   ('Maria Oliveira', 'maria.oliveira@hospital.com', 'Medico@02', 'DOCTOR'),
                                                   ('Carlos Souza', 'carlos.souza@hospital.com', 'Medico@03', 'DOCTOR'),
                                                   ('Ana Pereira', 'ana.pereira@hospital.com', 'Medico@04', 'DOCTOR'),
                                                   ('Fernanda Lima', 'fernanda.lima@hospital.com', 'Medico@05', 'DOCTOR'),
                                                   ('Ricardo Gomes', 'ricardo.gomes@hospital.com', 'Medico@06', 'DOCTOR'),
                                                   ('Patricia Alves', 'patricia.alves@hospital.com', 'Medico@07', 'DOCTOR'),
                                                   ('Bruno Costa', 'bruno.costa@hospital.com', 'Medico@08', 'DOCTOR'),
                                                   ('Juliana Martins', 'juliana.martins@hospital.com', 'Medico@09', 'DOCTOR'),
                                                   ('Rodrigo Rocha', 'rodrigo.rocha@hospital.com', 'Medico@10', 'DOCTOR'),
                                                   ('Aline Santos', 'aline.santos@hospital.com', 'Medico@11', 'DOCTOR'),
                                                   ('Paulo Mendes', 'paulo.mendes@hospital.com', 'Medico@12', 'DOCTOR'),
                                                   ('Vanessa Ribeiro', 'vanessa.ribeiro@hospital.com', 'Medico@13', 'DOCTOR'),
                                                   ('Eduardo Nunes', 'eduardo.nunes@hospital.com', 'Medico@14', 'DOCTOR'),
                                                   ('Mariana Castro', 'mariana.castro@hospital.com', 'Medico@15', 'DOCTOR'),
                                                   ('Felipe Barbosa', 'felipe.barbosa@hospital.com', 'Medico@16', 'DOCTOR'),
                                                   ('Renata Lima', 'renata.lima@hospital.com', 'Medico@17', 'DOCTOR'),
                                                   ('Gustavo Freitas', 'gustavo.freitas@hospital.com', 'Medico@18', 'DOCTOR'),
                                                   ('Tatiane Moura', 'tatiane.moura@hospital.com', 'Medico@19', 'DOCTOR'),
                                                   ('Leonardo Dias', 'leonardo.dias@hospital.com', 'Medico@20', 'DOCTOR'),
                                                   ('Carla Teixeira', 'carla.teixeira@hospital.com', 'Medico@21', 'DOCTOR'),
                                                   ('Roberto Lopes', 'roberto.lopes@hospital.com', 'Medico@22', 'DOCTOR'),
                                                   ('Camila Fernandes', 'camila.fernandes@hospital.com', 'Medico@23', 'DOCTOR'),
                                                   ('Thiago Melo', 'thiago.melo@hospital.com', 'Medico@24', 'DOCTOR');

-- =====================================
-- USUÁRIOS PACIENTES
-- =====================================

INSERT INTO user (name, email, password, type) VALUES
                                                   ('Lucas Almeida', 'lucas@gmail.com', 'Paciente@01', 'PATIENT'),
                                                   ('Juliana Costa', 'juliana@gmail.com', 'Paciente@02', 'PATIENT'),
                                                   ('Rafael Santos', 'rafael@gmail.com', 'Paciente@03', 'PATIENT'),
                                                   ('Camila Rocha', 'camila@gmail.com', 'Paciente@04', 'PATIENT'),
                                                   ('Pedro Martins', 'pedro@gmail.com', 'Paciente@05', 'PATIENT');

-- =====================================
-- MÉDICOS
-- =====================================

INSERT INTO doctor (crm, id_user, specialty) VALUES
                                                 ('000001-DF', 1, 'ANESTESIOLOGIA'),
                                                 ('000002-DF', 2, 'CARDIOLOGIA'),
                                                 ('000003-DF', 3, 'DERMATOLOGIA'),
                                                 ('000004-DF', 4, 'ENDOCRINOLOGIA'),
                                                 ('000005-DF', 5, 'GASTROENTEROLOGIA'),
                                                 ('000006-DF', 6, 'GERIATRIA'),
                                                 ('000007-DF', 7, 'GINECOLOGIA_E_OBSTETRICIA'),
                                                 ('000008-DF', 8, 'HEMATOLOGIA'),
                                                 ('000009-DF', 9, 'INFECTOLOGIA'),
                                                 ('000010-DF', 10, 'MASTOLOGIA'),
                                                 ('000011-DF', 11, 'MEDICINA_DO_TRABALHO'),
                                                 ('000012-DF', 12, 'NEFROLOGIA'),
                                                 ('000014-DF', 14, 'OFTALMOLOGIA'),
                                                 ('000015-DF', 15, 'ONCOLOGIA'),
                                                 ('000016-DF', 16, 'ORTOPEDIA_E_TRAUMATOLOGIA'),
                                                 ('000017-DF', 17, 'OTORRINOLARINGOLOGIA'),
                                                 ('000018-DF', 18, 'PEDIATRIA'),
                                                 ('000019-DF', 19, 'PNEUMOLOGIA'),
                                                 ('000020-DF', 20, 'PSICOLOGIA'),
                                                 ('000021-DF', 21, 'PSIQUIATRIA'),
                                                 ('000022-DF', 22, 'RADIOLOGIA'),
                                                 ('000023-DF', 23, 'REUMATOLOGIA'),
                                                 ('000024-DF', 24, 'UROLOGIA');

-- =====================================
-- PACIENTES
-- =====================================

INSERT INTO patient (cpf, id_user) VALUES
                                       ('111.111.111-11', 25),
                                       ('222.222.222-22', 26),
                                       ('333.333.333-33', 27),
                                       ('444.444.444-44', 28),
                                       ('555.555.555-55', 29);

-- =====================================
-- CONSULTAS DE TESTE
-- =====================================

INSERT INTO appointment (crm, cpf, date, time) VALUES
                                                   ('000002-DF', '111.111.111-11', '2026-07-01', '09:00'),
                                                   ('000002-DF', '222.222.222-22', '2026-07-01', '09:30'),
                                                   ('000002-DF', '333.333.333-33', '2026-07-01', '10:00'),

                                                   ('000003-DF', '444.444.444-44', '2026-07-02', '13:00'),
                                                   ('000003-DF', '555.555.555-55', '2026-07-02', '14:30'),

                                                   ('000018-DF', '111.111.111-11', '2026-07-03', '15:00'),
                                                   ('000018-DF', '222.222.222-22', '2026-07-03', '15:30'),

                                                   ('000021-DF', '333.333.333-33', '2026-07-04', '17:00'),
                                                   ('000021-DF', '444.444.444-44', '2026-07-04', '17:30'),

                                                   ('000024-DF', '555.555.555-55', '2026-07-05', '18:00');