package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author SERGIO
 */
public class ConsultasUsuario extends Conexion {

    PreparedStatement ps; //para consultas

    public boolean insertar(Usuario usuario) {
        Connection conexion = establecerConexion(); //establecer conexion a la BD

        try {
            ps = conexion.prepareStatement("INSERT INTO usuarios (usuario,nombre,correo,clave) VALUES (?,?,?,?)"); // lo que se va a insertar

            //completando los ?
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getClave());

            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.err.println("Error, " + e);
            return false; //si ocurre exception
        } //Agregar finally para cerrar conexion. Siempre se ejecutara
        finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean modificar(Usuario usuario) {
        Connection conexion = establecerConexion();

        try {

            ps = conexion.prepareStatement("UPDATE usuarios SET usuario=?,nombre=?,correo=?,clave=? WHERE id=?");

            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getClave());
            ps.setInt(5, usuario.getId());

            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.err.println("Error, " + e);
            return false; //si ocurre exception
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean eliminar(Usuario usuario) {
        Connection conexion = establecerConexion();

        try {
            ps = conexion.prepareStatement("delete FROM usuarios WHERE id=?");
            ps.setInt(1, usuario.getId());

            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.err.println("Error, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

}
