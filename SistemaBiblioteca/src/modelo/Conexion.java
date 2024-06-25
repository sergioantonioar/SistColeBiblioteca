package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class Conexion {
    String usuario = "user";
    String contrasena = "1234";
    String bd = "Biblio";
    String ip = "localhost";
    String puerto = "1433"; 
    String cadena = "jdbc:sqlserver://" +ip+ ":"+puerto+";databaseName=" + bd + ";user=" + usuario + ";password=" + contrasena;
    Connection conectar = null;

    //8.4.1.jre8
    
    public Connection establecerConexion(){
        try{
            conectar = DriverManager.getConnection(cadena);
            JOptionPane.showMessageDialog(null, "conectado exitosamente");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "error no conectado" + e.toString());
        }
        return conectar;
    }
}
