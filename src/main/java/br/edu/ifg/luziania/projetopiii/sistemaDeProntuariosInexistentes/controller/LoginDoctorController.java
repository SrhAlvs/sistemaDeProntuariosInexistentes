package br.edu.ifg.luziania.projetopiii.sistemaDeProntuariosInexistentes.controller;

import br.edu.ifg.luziania.projetopiii.sistemaDeProntuariosInexistentes.util.AlertMessenger;
import br.edu.ifg.luziania.projetopiii.sistemaDeProntuariosInexistentes.util.ScreenNavigator;
import br.edu.ifg.luziania.projetopiii.sistemaDeProntuariosInexistentes.util.UserValidator;
import br.edu.ifg.luziania.projetopiii.sistemaDeProntuariosInexistentes.util.exceptions.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginDoctorController {
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
                UserValidator.validateEmail(inputLogin);
                System.out.println("[AUDITORIA] Tentativa de login via E-mail: " + inputLogin);
            } else {
                UserValidator.validateCrm(inputLogin);
                System.out.println("[AUDITORIA] Tentativa de login via CRM: " + inputLogin);
            }

        } catch (ValidationException e) {
            // captura o erro lógico e avisa o usuário
            AlertMessenger.show(Alert.AlertType.ERROR, "Erro de Autenticação", e.getMessage());
            // Aqui chamaremos o LoggerService.logException(...) para gravar no arquivo txt
            System.err.println("[LOG EXCEÇÃO] Falha no login do médico: " + e.getMessage());

        }

    }

    // trata o clique no link de criação de conta, redirecionando para o cadastro
    @FXML
    private void handleCreateAccount(ActionEvent event) {
        // registra a ação no console (aqui futuramente chamará o LoggerService de Auditoria)
        System.out.println("[AUDITORIA] Usuário navegando para a tela de Cadastro de Médico.");

        // realiza a troca da cena
        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/projetopiii/view/CreateAccountDoctorPage.fxml");
    }

}
