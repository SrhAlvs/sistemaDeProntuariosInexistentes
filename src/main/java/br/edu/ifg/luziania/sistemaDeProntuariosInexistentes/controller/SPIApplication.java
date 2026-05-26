package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SPIApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/view/SPIInitialPage.fxml")
        );

        Scene scene = new Scene(loader.load());

        stage.setTitle("Sistema de Prontuários Inexistentes - Página Inicial");
        stage.setScene(scene);
        stage.show();
    }

    static void main()  {
        launch();
    }
}