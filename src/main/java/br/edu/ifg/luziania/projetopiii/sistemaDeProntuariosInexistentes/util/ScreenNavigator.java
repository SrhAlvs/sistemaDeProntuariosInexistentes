package br.edu.ifg.luziania.projetopiii.sistemaDeProntuariosInexistentes.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;

public class ScreenNavigator {

    /**
     * Altera a cena atual para um novo arquivo FXML.
     * @param event O ActionEvent disparado pelo componente (botão, hyperlink, etc.)
     * @param fxmlPath O caminho do arquivo FXML (ex: "/br/edu/ifg/luziania/projetopiii/view/LoginDoctorPage.fxml")
     */
    public static void changeScene(ActionEvent event, String fxmlPath) {
        try {
            // busca o recurso do FXML
            URL fxmlLocation = ScreenNavigator.class.getResource(fxmlPath);

            // validação lógica antes de enviar para o método auxiliar
            if (fxmlLocation == null) {
                throw new IOException("Arquivo FXML não encontrado no caminho: " + fxmlPath);
            }

            // transforma a janela atual com a nova cena e a exibe
            Stage stage = prepareStage(event, fxmlLocation);
            stage.show();

        } catch (IOException e) {
            // log de exceções em arquivos caso ocorra falha ao carregar a página
            System.err.println("[ERRO] Falha crítica ao carregar o arquivo FXML: " + fxmlPath);
            System.err.println("Descrição da exceção: " + e.getMessage());

            // Aqui futuramente chamaremos o seu LoggerService para salvar no arquivo físico!
        }
    }

    // método auxiliar para carregar o FXML e configurar a Scene no Stage atual
    @NotNull
    private static Stage prepareStage(ActionEvent event, @NotNull URL fxmlLocation) throws IOException {
        // carrega o arquivo de visualização correspondente
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();

        /*
            Obtém o Stage (janela) atual a partir do evento do botão clicado.
            Captura o botão exato que disparou a ação,
            a partir dele, é possível acessar a janela atual (Stage)
            para trocar o seu conteúdo interno (Scene) sem abrir uma janela nova.
        */
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // define a nova cena na janela principal utilizando instanciação direta
        stage.setScene(new Scene(root));

        return stage;
    }
}