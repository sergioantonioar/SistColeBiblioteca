package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {
    
    private String usuario = "user";
    private String contrasena = "1234";
    private String bd = "Biblio";
    private String ip = "localhost";
    private String puerto = "1433";
    private String Conexionurl;
    Connection conectar = null;
    
    public Conexion(){
        Conexionurl = "jdbc:sqlserver://" +ip+ ":"+puerto+";databaseName=" + bd + ";user=" + usuario + ";password=" + contrasena;
    }
    
    
    public Connection establecerConexion(){
        try{
            conectar = DriverManager.getConnection(Conexionurl);
            //JOptionPane.showMessageDialog(null, "conectado a la base de datos exitosamente.");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "error no conectado: " + e.toString());
        }
        return conectar;
    }
    
    public void cerrarConexion(){
        if(conectar!=null){
            try {
                conectar.close();
                System.out.println("Conexcion cerrada");
            } catch (SQLException e) {
                System.out.println("Error al cerrar conexión:");
                e.printStackTrace(); // obtener detalles del problema
            }
        }
    }
}
