package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.controller;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.AlertMessenger;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.ScreenNavigator;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.UserValidator;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util.exceptions.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginPatientController {
    @FXML private TextField plEmailCPFTextField;
    @FXML private PasswordField plPasswordField;

    //configura o botão 'Enter' (realiza a validação e tenta autenticar o paciente)
    @FXML
    private void handleLogin(ActionEvent event) {
        String inputLogin = plEmailCPFTextField.getText().trim();
        String password = plPasswordField.getText();

        try {
            // validação lógica de formatos
            UserValidator.validatePassword(password);

            // verifica se o usuário tentou logar por e-mail ou por CPF
            if (inputLogin.contains("@")) {
                UserValidator.validateEmail(inputLogin);
                System.out.println("[AUDITORIA] Tentativa de login via E-mail: " + inputLogin);
            } else {
                UserValidator.validateCpf(inputLogin);
                System.out.println("[AUDITORIA] Tentativa de login via CPF: " + inputLogin);
            }

        } catch (ValidationException e) {
            // captura o erro lógico e avisa o usuário
            AlertMessenger.show(Alert.AlertType.ERROR, "Erro de Autenticação", e.getMessage());
            // Aqui chamaremos o LoggerService.logException(...) para gravar no arquivo txt
            System.err.println("[LOG EXCEÇÃO] Falha no login do paciente: " + e.getMessage());

        }

    }

    // trata o clique no link de criação de conta, redirecionando para o cadastro
    @FXML
    private void handleCreateAccount(ActionEvent event) {
        // registra a ação no console (aqui futuramente chamará o LoggerService de Auditoria)
        System.out.println("[AUDITORIA] Usuário navegando para a tela de Cadastro de Paciente.");

        // realiza a troca da cena
        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/CreateAccountPatientPage.fxml");
    }

    // trata o clique no botão de voltar, redirecionando para a tela de escolha 'MÉDICO/PACIENTE'
    @FXML
    private void handleBackButton(ActionEvent event) {
        // registra a ação no console (aqui futuramente chamará o LoggerService de Auditoria)
        System.out.println("[AUDITORIA] Usuário navegando para a tela de Escolha da Forma de Login.");

        // realiza a troca da cena
        ScreenNavigator.changeScene(event, "/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/SPIInitialPage.fxml");
    }
}
