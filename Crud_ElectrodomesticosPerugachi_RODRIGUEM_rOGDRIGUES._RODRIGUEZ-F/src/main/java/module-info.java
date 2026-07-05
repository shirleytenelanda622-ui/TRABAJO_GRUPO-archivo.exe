module org.example.crud_electrodomesticos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // Obligatorio para conectar a MySQL / JDBC


    // Permite a JavaFX mapear los campos FXML con tus clases controladoras
    opens org.example.crud_electrodomesticos to javafx.fxml, javafx.base;

    // Exporta el paquete para que sea accesible externamente
    exports org.example.crud_electrodomesticos;
}