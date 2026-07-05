package org.example.crud_electrodomesticos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion  {
    public static Connection getConexion() throws SQLException {
    String url = "jdbc:mysql://hayabusa.proxy.rlwy.net:11369/railway";
    String usuario = "root";
    String password = "ARsllqWUauGLlllobQIsVfBKxbUjeDZO";

    Connection con = DriverManager.getConnection(url, usuario, password);
            Connection conexion = null;
            try {
             conexion= DriverManager.getConnection(url ,usuario,password);
             System.out.println("Conectado la database");

            } catch (SQLException e) {
                System.out.printf("Error al conectar la database: %s\n",e.getMessage());
            }

            return conexion;
        }

}

