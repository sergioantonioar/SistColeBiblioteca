package controlador;

import modelo.Conexion;
import modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author SERGIO
 */
public class UsuarioControlador {
    
    public boolean LoginUsuario(Usuario usuario){
        boolean respuesta = false;
        //Definiendo consulta con parametros 
        String query = "SELECT * FROM usuarios WHERE usuario = ? AND clave = ?";
        Conexion conectarbd = new Conexion();
        Connection connection = conectarbd.establecerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //Preparando consulta
            ps = connection.prepareStatement(query);
            //ejecutando la consulta
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getClave());
            //comprobar si hay al menos un registro
            if (rs.next()) {
                respuesta = true;
            }
            connection.close(); //para evitar fugas de datos
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error, "+e);
        }
        return respuesta;
    }
    
}
