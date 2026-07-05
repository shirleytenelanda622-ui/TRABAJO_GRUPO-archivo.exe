package org.example.crud_electrodomesticos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.*;

public class ElectrodomesticosController {

    @FXML public TextField nombre;
    @FXML public TextField stock;
    @FXML public TextField precio;

    @FXML public Button crear;
    @FXML public Button leer;
    @FXML public Button actualizar;
    @FXML public Button eliminar;

    @FXML public TableView<Electrodomesticos> tabla;
    @FXML public TableColumn<Electrodomesticos, Integer> colid;
    @FXML public TableColumn<Electrodomesticos, String> colnombre;
    @FXML public TableColumn<Electrodomesticos, Integer> colcantidad;
    @FXML public TableColumn<Electrodomesticos, Double> colprecio;

    @FXML
    public void initialize() {
        // Listener para capturar el elemento seleccionado de la tabla y ponerlo en los inputs
        tabla.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                nombre.setText(newValue.getNombre());
                stock.setText(String.valueOf(newValue.getStock()));
                precio.setText(String.valueOf(newValue.getPrecio()));
            }
        });

        // Carga inicial de datos
        mostrar();
    }

    public boolean validaciones() {
        if (nombre.getText().trim().isEmpty()) {
            nombre.requestFocus();
            mostrarAlerta("Validación", "El nombre del producto no puede estar vacío.", Alert.AlertType.WARNING);
            return false;
        }
        if (stock.getText().trim().isEmpty()) {
            stock.requestFocus();
            mostrarAlerta("Validación", "La cantidad no puede estar vacía.", Alert.AlertType.WARNING);
            return false;
        }
        if (precio.getText().trim().isEmpty()) {
            precio.requestFocus();
            mostrarAlerta("Validación", "El precio no puede estar vacío.", Alert.AlertType.WARNING);
            return false;
        }

        if (nombre.getText().matches(".*\\d.*")) {
            nombre.requestFocus();
            mostrarAlerta("Validación", "En el nombre del producto no debe haber números.", Alert.AlertType.WARNING);
            return false;
        }
        if (!stock.getText().matches("\\d+")) {
            stock.requestFocus();
            mostrarAlerta("Validación", "En cantidad solo debe ingresar números enteros.", Alert.AlertType.WARNING);
            return false;
        }

        try {
            double precioValor = Double.parseDouble(precio.getText());
            if (precioValor <= 0) {
                precio.requestFocus();
                mostrarAlerta("Validación", "El precio debe ser mayor a cero.", Alert.AlertType.WARNING);
                return false;
            }
        } catch (NumberFormatException e) {
            precio.requestFocus();
            mostrarAlerta("Validación", "El precio debe ser un número válido.", Alert.AlertType.WARNING);
            return false;
        }

        return true;
    }

    @FXML
    public void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    public void crear() { // <--- Vinculado al botón Crear
        if (!validaciones()) {
            return;
        }

        String sql = "INSERT INTO electrodomesticos(nombre, stock, precio) VALUES (?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre.getText().trim());
            ps.setInt(2, Integer.parseInt(stock.getText().trim()));
            ps.setDouble(3, Double.parseDouble(precio.getText().trim()));

            ps.executeUpdate();
            mostrarAlerta("Éxito", "Producto registrado correctamente.", Alert.AlertType.INFORMATION);
            mostrar();
            limpiar();
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudo registrar el producto: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void editar() { // <--- Vinculado al botón Actualizar
        Electrodomesticos seleccion = tabla.getSelectionModel().getSelectedItem();
        if (seleccion == null) {
            mostrarAlerta("Error", "Seleccione un registro de la tabla para editar.", Alert.AlertType.ERROR);
            return;
        }
        if (!validaciones()) {
            return;
        }

        String sql = "UPDATE electrodomesticos SET nombre=?, stock=?, precio=? WHERE id=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre.getText().trim());
            ps.setInt(2, Integer.parseInt(stock.getText().trim()));
            ps.setDouble(3, Double.parseDouble(precio.getText().trim()));
            ps.setInt(4, seleccion.getId());

            int fila = ps.executeUpdate();
            if (fila == 0) {
                mostrarAlerta("Error", "No existe el registro en la base de datos.", Alert.AlertType.ERROR);
            } else {
                mostrarAlerta("Información", "Registro actualizado con éxito.", Alert.AlertType.INFORMATION);
            }
            mostrar();
            limpiar();

        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudo editar el registro: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void eliminar() { // <--- Vinculado al botón Eliminar (Cambiado a public para evitar restricciones FXML)
        Electrodomesticos producto = tabla.getSelectionModel().getSelectedItem();

        if (producto == null) {
            mostrarAlerta("Aviso", "Seleccione un producto para eliminar.", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Eliminar");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Está seguro de eliminar el producto: " + producto.getNombre() + "?");

        if (confirmacion.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            String sql = "DELETE FROM electrodomesticos WHERE id = ?";

            try (Connection conn = Conexion.getConexion();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, producto.getId());
                ps.executeUpdate();

                mostrarAlerta("Éxito", "Producto eliminado correctamente.", Alert.AlertType.INFORMATION);
                mostrar();
                limpiar();
            } catch (SQLException e) {
                mostrarAlerta("Error", "No se pudo eliminar el producto: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    public void mostrar() {
        ObservableList<Electrodomesticos> electrodomesticos = FXCollections.observableArrayList();
        String sql = "SELECT * FROM electrodomesticos";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                electrodomesticos.add(new Electrodomesticos(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("stock"),
                        rs.getDouble("precio")
                ));
            }

            colid.setCellValueFactory(new PropertyValueFactory<>("id"));
            colnombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colcantidad.setCellValueFactory(new PropertyValueFactory<>("stock"));
            colprecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

            tabla.setItems(electrodomesticos);

        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudo mostrar el registro: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void limpiar() {
        nombre.clear();
        stock.clear();
        precio.clear();
        tabla.getSelectionModel().clearSelection();
    }
}