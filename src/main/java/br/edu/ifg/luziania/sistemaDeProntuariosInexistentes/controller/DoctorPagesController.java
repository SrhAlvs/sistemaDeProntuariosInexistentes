package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.controller;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DAO.DoctorDAO;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.Doctor;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.DoctorSpecialty;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.User;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.AlertMessenger;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.LogWriter;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.ScreenNavigator;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.UserValidator;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.exceptions.ValidationException;
import com.mysql.cj.log.Log;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DoctorPagesController implements Initializable {

    // --------------- AUTENTICAÇÃO CADASTRO ---------------
    @FXML private TextField dcaFullNameTextField;
    @FXML private TextField dcaCRMTextField;
    @FXML private TextField dcaEmailTextField;
    @FXML private PasswordField dcaPasswordField;
    @FXML private MenuButton dcaSpecialtyMenuButton;

    DoctorDAO doctor = new DoctorDAO();
    // variável para guardar a especialidade do Médico
    private DoctorSpecialty selectedSpecialty;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (dcaSpecialtyMenuButton != null) {
            synchronizeSpecialties();
        }
    }
    private void synchronizeSpecialties() {
        // limpa limpa limpa
        dcaSpecialtyMenuButton.getItems().clear();

        for (DoctorSpecialty specialty : DoctorSpecialty.values()) {
            // cria o item usando o nome bonito da especialidade
            MenuItem item = new MenuItem(specialty.getSpecialtyName());

            item.setOnAction(event -> {
                selectedSpecialty = specialty;

                // muda o texto do botão para o médico ver o que escolheu
                dcaSpecialtyMenuButton.setText(specialty.getSpecialtyName());
            });

            // adiciona o item ao MenuButton
            dcaSpecialtyMenuButton.getItems().add(item);
        }
    }

    // configura botão 'Enter' (recolhe dados, passa pelo Validator e salva)
    @FXML
    private void handleCreateAccount(ActionEvent event) {
        String fullName = dcaFullNameTextField.getText();
        String fullNameTrimmed = fullName.trim();
        String crm = dcaCRMTextField.getText().trim();
        String email = dcaEmailTextField.getText().trim();
        String password = dcaPasswordField.getText();

        try {
            // disparando a bateria de validações
            UserValidator.validateName(fullNameTrimmed);
            UserValidator.validateCrm(crm);
            UserValidator.validateEmail(email);
            UserValidator.validatePassword(password);
            UserValidator.validateSpecialty(selectedSpecialty);

            try {
                doctor.insert(new Doctor(fullName, email, password, "DOCTOR", crm, selectedSpecialty));
            } catch (SQLException e) {
                LogWriter.write("[ERRO | BANCO DE DADOS] Falha ao inserir usuário (médico).");
                throw new ValidationException("Falha ao tentar cadastrar conta de médico.");
            }

            // se chegou aqui, passou em todos os 'testes'
            AlertMessenger.show(Alert.AlertType.INFORMATION, "Sucesso", "Conta médica criada com sucesso!");
            LogWriter.write("[CONTA] Conta de médico criada com sucesso!");

            // após o cadastro, joga o médico de volta para a tela de login
            ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/LoginDoctorPage.fxml");

        } catch (ValidationException e) {
            AlertMessenger.show(Alert.AlertType.ERROR, "Erro no Cadastro", e.getMessage());
            LogWriter.write("[CONTA] Falha ao tentar cadastrar conta de médico.");
        }
    }

    // --------------- AUTENTICAÇÃO LOGIN ---------------
    @FXML private TextField dlEmailCRMTextField;
    @FXML private PasswordField dlPasswordField;

    //configura o botão 'Enter' (passa pelo Validator e tenta autenticar o médico)
    @FXML
    private void handleLogin(ActionEvent event) {
        String inputLogin = dlEmailCRMTextField.getText().trim();
        String password = dlPasswordField.getText();

        try {
            // validação lógica de formatos
            UserValidator.validatePassword(password);

            // verifica se o usuário tentou logar por e-mail ou por CRM
            if (inputLogin.contains("@")) {
                LogWriter.write("[LOGIN] Tentativa de login de médico via E-mail: " + inputLogin);

                UserValidator.validateEmail(inputLogin);

                Doctor doctor = this.doctor.findByEmail(inputLogin);

                if (doctor != null) {
                    if (doctor.getPassword().equals(password) && doctor.getEmail().equals(inputLogin) && doctor.getType().equals("DOCTOR")) {
                        LogWriter.write("[LOGIN] Sucesso no login de médico via E-mail.");
                    } else {
                        throw new ValidationException("E-mail ou senha inválidos.");
                    }
                } else {
                    throw new ValidationException("E-mail não cadastrado.");
                }

            } else {
                LogWriter.write("[LOGIN] Tentativa de login de médico via CRM: " + inputLogin);

                UserValidator.validateCrm(inputLogin);

                Doctor doctor = this.doctor.findByCrm(inputLogin);

                if (doctor != null) {
                    if (doctor.getPassword().equals(password) && doctor.getCrm().equals(inputLogin) && doctor.getType().equals("DOCTOR")) {
                        LogWriter.write("[LOGIN] Sucesso no login de médico via CRM.");
                    } else {
                        throw new ValidationException("CRM ou senha inválidos.");
                    }
                } else {
                    throw new ValidationException("CRM não cadastrado.");
                }
            }

            ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/HomeDoctorPage.fxml");
        } catch (ValidationException e) {
            AlertMessenger.show(Alert.AlertType.ERROR, "Erro de Autenticação", e.getMessage());
            LogWriter.write("[LOGIN] Falha no login do médico: " + e.getMessage());

        }
    }

    // --------------- NAVEGAÇÃO ---------------

    // trata o clique no botão 'Voltar', redirecionando para a tela de escolha, 'SPIInitialPage'
    @FXML
    private void handleBackToChoiceButton(ActionEvent event) {
        LogWriter.write("[NAVEGAÇÃO] Usuário navegando para a tela de Escolha da Forma de Login.");

        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/SPIInitialPage.fxml");
    }

    // trata o clique no link 'Create account', redirecionando para a tela 'CreateAccountDoctorPage'
    @FXML
    private void handleCreateAccountHyperLink(ActionEvent event) {
        LogWriter.write("[NAVEGAÇÃO] Usuário navegando para a tela de Cadastro de Médico.");

        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/CreateAccountDoctorPage.fxml");
    }

    // trata o clique no link "Login", redirecionando para a tela 'LoginDoctorPage'
    @FXML
    private void handleBackToLoginHyperLink(ActionEvent event) {
        LogWriter.write("[NAVEGAÇÃO] Usuário cancelou o cadastro e está voltando para a tela de Login de Médico.");

        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/LoginDoctorPage.fxml");
    }

    // trata o clique no botão 'Voltar', redirecionando para a tela 'LoginDoctorPage'
    @FXML
    private void handleBackLoginButton(ActionEvent event) {
        LogWriter.write("[NAVEGAÇÃO] Usuário (médico) navegando para a tela de Login de Médico.");

        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/LoginDoctorPage.fxml");
    }

    // trata o clique no botão de voltar, redirecionando para a tela 'HomeDoctorPage'
    @FXML
    private void handleBackHomeButton(ActionEvent event) {
        // registra a ação no Log
        LogWriter.write("[NAVEGAÇÃO] Usuário (médico) navegando para a Tela Inicial (Home).");

        // realiza a troca da cena
        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/HomeDoctorPage.fxml");
    }

    // trata o clique no botão 'Prontuários', redirecionando para a tela 'MedicalRecordsDoctorPage'
    @FXML
    private void handleMedicalRecordsButton(ActionEvent event) {
        LogWriter.write("[NAVEGAÇÃO] Usuário (médico) navegando para a tela de Prontuários.");

        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/MedicalRecordsDoctorPage.fxml");
    }

    // trata o clique no botão 'Meus dados', redirecionando para a tela 'MyDetailsDoctorPage'
    @FXML
    private void handleMyDetailsButton(ActionEvent event) {
        LogWriter.write("[NAVEGAÇÃO] Usuário (médico) navegando para a tela de visualização dos Próprios Dados.");

        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/MyDetailsDoctorPage.fxml");
    }
}
