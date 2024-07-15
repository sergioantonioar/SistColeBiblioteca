
package Util;

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
    Connection con = null;
    
    public Conexion(){
        Conexionurl = "jdbc:sqlserver://" +ip+ ":"+puerto+";databaseName=" + bd + ";user=" + usuario + ";password=" + contrasena;
    }
    
    public Connection getConnection() {
        try {
            
            con = DriverManager.getConnection(Conexionurl);
            return con;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la conexion. " + e.getMessage());
        }
        return null;
    }
}
