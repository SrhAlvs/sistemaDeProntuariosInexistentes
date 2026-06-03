package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.controller;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.AlertMessenger;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.ScreenNavigator;
// import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.UserValidator;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.exceptions.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CreateAccountDoctorController {
    @FXML private TextField dcaFullNameTextField;
    @FXML private TextField dcaCRMTextField;
    @FXML private TextField dcaEmailTextField;
    @FXML private PasswordField dcaPasswordField;

    // configura botão 'Enter' (recolhe dados, passa pelo Validator e salva)
    @FXML
    private void handleCreateAccount(ActionEvent event) {
        String fullName = dcaFullNameTextField.getText().trim();
        String crm = dcaCRMTextField.getText().trim();
        String email = dcaEmailTextField.getText().trim();
        String password = dcaPasswordField.getText();

        try {
            // disparando a bateria de validações
            UserValidator.validateName(fullName);
            UserValidator.validateCrm(crm);
            UserValidator.validateEmail(email);
            UserValidator.validatePassword(password);

            // se chegou aqui, passou em todos os 'testes'
            AlertMessenger.show(Alert.AlertType.INFORMATION, "Sucesso", "Conta médica criada com sucesso!");

            /*FALTA SALVAR NO BANCO DE DADOS*/

            // após o cadastro, joga o médico de volta para a tela de login
            ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/LoginDoctorPage.fxml");

        } catch (ValidationException e) {
            // // captura o erro lógico e avisa o usuário
            AlertMessenger.show(Alert.AlertType.ERROR, "Erro no Cadastro", e.getMessage());
            // Aqui chamaremos o LoggerService.logException(...) para gravar no arquivo txt
            System.err.println("[LOG EXCEÇÃO] Falha ao tentar cadastrar médico: " + e.getMessage());
        }
    }

    // trata o clique no Hyperlink "Login", redirecionando para o login
    @FXML
    private void handleBackToLogin(ActionEvent event) {
        // registra a ação no console (aqui futuramente chamará o LoggerService de Auditoria)
        System.out.println("[AUDITORIA] Usuário cancelou o cadastro e está voltando para o login.");

        // realiza a troca da cena
        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/LoginDoctorPage.fxml");
    }
}
