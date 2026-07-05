package org.example.crud_electrodomesticos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField txtusuario;

    @FXML
    private PasswordField psclave;

    @FXML
    private Button btningresar;

    @FXML
    public void btningresar(ActionEvent event) {
        String usuario = txtusuario.getText().trim();
        String clave = psclave.getText().trim();

        if (usuario.isEmpty() || clave.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Vacíos", "Por favor, llene todos los campos.");
            return;
        }

        if (usuario.equals("admin") && clave.equals("admin")) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/crud_electrodomesticos/hello-view.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Panel de Administración - Electrodomésticos");
                stage.show();


                Stage currentStage = (Stage) btningresar.getScene().getWindow();
                currentStage.close();

            } catch (IOException e) {
                e.printStackTrace();
                mostrarAlerta(Alert.AlertType.ERROR, "Error de Carga", "No se pudo cargar la vista principal.");
            }
        } else {

            mostrarAlerta(Alert.AlertType.ERROR, "Acceso Denegado", "Usuario o contraseña incorrectos.");
        }
    }
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}