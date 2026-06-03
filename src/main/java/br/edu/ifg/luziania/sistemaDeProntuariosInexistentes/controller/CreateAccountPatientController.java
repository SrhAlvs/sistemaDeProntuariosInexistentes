package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.controller;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.AlertMessenger;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.ScreenNavigator;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.UserValidator;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.exceptions.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CreateAccountPatientController {
    @FXML
    private TextField pcaFullNameTextField;
    @FXML private TextField pcaCPFTextField;
    @FXML private TextField pcaEmailTextField;
    @FXML private PasswordField pcaPasswordField;

    // configura botão 'Enter' (recolhe dados, passa pelo Validator e salva)
    @FXML
    private void handleCreateAccount(ActionEvent event) {
        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/LoginPatientPage.fxml");
//        String fullName = pcaFullNameTextField.getText().trim();
//        String cpf = pcaCPFTextField.getText().trim();
//        String email = pcaEmailTextField.getText().trim();
//        String password = pcaPasswordField.getText();
//
//        try {
//            // disparando a bateria de validações
//            UserValidator.validateName(fullName);
//            UserValidator.validateCpf(cpf);
//            UserValidator.validateEmail(email);
//            UserValidator.validatePassword(password);
//
//            // se chegou aqui, passou em todos os 'testes'
//            AlertMessenger.show(Alert.AlertType.INFORMATION, "Sucesso", "Conta de paciente criada com sucesso!");
//
//            // após o cadastro, joga o paciente de volta para a tela de login
//            ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/LoginPatientPage.fxml");
//
//        } catch (ValidationException e) {
//            // // captura o erro lógico e avisa o usuário
//            AlertMessenger.show(Alert.AlertType.ERROR, "Erro no Cadastro", e.getMessage());
//            // Aqui chamaremos o LoggerService.logException(...) para gravar no arquivo txt
//            System.err.println("[LOG EXCEÇÃO] Falha ao tentar cadastrar paciente: " + e.getMessage());
//        }
    }

    // trata o clique no Hyperlink "Login", redirecionando para o login
    @FXML
    private void handleBackToLogin(ActionEvent event) {
        // registra a ação no console (aqui futuramente chamará o LoggerService de Auditoria)
        System.out.println("[AUDITORIA] Usuário cancelou o cadastro e está voltando para o login.");

        // realiza a troca da cena
        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/LoginPatientPage.fxml");
    }
}
