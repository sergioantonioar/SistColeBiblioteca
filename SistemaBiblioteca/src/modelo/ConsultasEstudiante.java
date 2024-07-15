package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author SERGIO
 */
public class ConsultasEstudiante extends Conexion {

    PreparedStatement ps; //para consultas

    public boolean insertar(Estudiante estudiante) {
        Connection conexion = establecerConexion(); //establecer conexion a la BD

        try {
            ps = conexion.prepareStatement("INSERT INTO estudiantes (documento,codigo,nombre,telefono,nivel,grado,seccion) VALUES (?,?,?,?,?,?,?)"); // lo que se va a insertar

            //completando los ?
            ps.setString(1, estudiante.getDocumento());
            ps.setString(2, estudiante.getCodigo());
            ps.setString(3, estudiante.getNombre());
            ps.setString(4, estudiante.getTelefono());
            ps.setString(5, estudiante.getNivel());
            ps.setString(6, estudiante.getGrado());
            ps.setString(7, estudiante.getSeccion());

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

    public boolean modificar(Estudiante estudiante) {
        Connection conexion = establecerConexion();

        try {

            ps = conexion.prepareStatement("UPDATE estudiantes SET documento=?,codigo=?,nombre=?,telefono=?,nivel=?,grado=?,seccion=? WHERE id=?");

            ps.setString(1, estudiante.getDocumento());
            ps.setString(2, estudiante.getCodigo());
            ps.setString(3, estudiante.getNombre());
            ps.setString(4, estudiante.getTelefono());
            ps.setString(5, estudiante.getNivel());
            ps.setString(6, estudiante.getGrado());
            ps.setString(7, estudiante.getSeccion());
            ps.setInt(8, estudiante.getId());

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

    public boolean eliminar(Estudiante estudiante) {
        Connection conexion = establecerConexion();

        try {
            ps = conexion.prepareStatement("delete FROM estudiantes WHERE id=?");
            ps.setInt(1, estudiante.getId());

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
