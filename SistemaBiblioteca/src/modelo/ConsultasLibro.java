package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JComboBox;

/**
 *
 * @author SERGIO
 */
public class ConsultasLibro extends Conexion {

    PreparedStatement ps; //para consultas
    ResultSet rs;

    public boolean insertar(Libro libro) {
        Connection conexion = establecerConexion(); //establecer conexion a la BD

        try {
            ps = conexion.prepareStatement("INSERT INTO libros (titulo, id_editorial, id_autor, id_materia, cantidad, num_pag, anio_edicion) VALUES (?,?,?,?,?,?,?)"); // lo que se va a insertar

            //completando los ?
            ps.setString(1, libro.getTitulo());
            ps.setInt(2, libro.getEditorial());
            ps.setInt(3, libro.getAutor());
            ps.setInt(4, libro.getMateria());
            ps.setInt(5, libro.getCantidad());
            ps.setInt(6, libro.getNum_pag());
            ps.setInt(7, libro.getAnio_edicion());

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

    public boolean modificar(Libro libro) {
        Connection conexion = establecerConexion();

        try {

            ps = conexion.prepareStatement("UPDATE libros SET titulo=?,id_editorial=?,id_autor=?,id_materia=?,cantidad=?,num_pag=?,anio_edicion=? WHERE id=?");

            ps.setString(1, libro.getTitulo());
            ps.setInt(2, libro.getEditorial());
            ps.setInt(3, libro.getAutor());
            ps.setInt(4, libro.getMateria());
            ps.setInt(5, libro.getCantidad());
            ps.setInt(6, libro.getNum_pag());
            ps.setInt(7, libro.getAnio_edicion());
            ps.setInt(8, libro.getId());

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

    public boolean eliminar(Libro libro) {
        Connection conexion = establecerConexion();

        try {
            ps = conexion.prepareStatement("DELETE FROM libros WHERE id=?");
            ps.setInt(1, libro.getId());

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

    public void ObtenerAutor(JComboBox comboAutor) {
        try {
            Connection conexion = establecerConexion();
            PreparedStatement ps = conexion.prepareStatement("SELECT id, autor FROM autor");
            ResultSet rs = ps.executeQuery();

            // Añadir un item inicial al JComboBox
            comboAutor.addItem(new Autor(0, "Seleccione autor"));

            while (rs.next()) {
                comboAutor.addItem(new Autor(rs.getInt("id"), rs.getString("autor")));
            }

        } catch (Exception e) {
            System.err.println("Error, " + e);
        }
    }
    
    public void ObtenerEditorial(JComboBox comboEditorial) {
        try {
            Connection conexion = establecerConexion();
            PreparedStatement ps = conexion.prepareStatement("SELECT id, editorial FROM editorial");
            ResultSet rs = ps.executeQuery();

            // Añadir un item inicial al JComboBox
            comboEditorial.addItem(new Editorial(0, "Seleccione editorial"));

            while (rs.next()) {
                comboEditorial.addItem(new Editorial(rs.getInt("id"), rs.getString("editorial")));
            }

        } catch (Exception e) {
            System.err.println("Error, " + e);
        }
    }
    
    public void ObtenerMateria(JComboBox comboMateria) {
        try {
            Connection conexion = establecerConexion();
            PreparedStatement ps = conexion.prepareStatement("SELECT id, materia FROM materias");
            ResultSet rs = ps.executeQuery();

            // Añadir un item inicial al JComboBox
            comboMateria.addItem(new Autor(0, "Seleccione autor"));

            while (rs.next()) {
                comboMateria.addItem(new Autor(rs.getInt("id"), rs.getString("materia")));
            }

        } catch (Exception e) {
            System.err.println("Error, " + e);
        }
    }
    
    
    
    
    
    
    
    
    
    
    //METODOS RESPALDO

//    public void ObtenerEditorial(JComboBox editorial) {
//        Connection conexion = establecerConexion();
//        try {
//            ps = conexion.prepareStatement("SELECT editorial FROM editorial");
//            rs = ps.executeQuery();
//
//            while (rs.next()) {
//                editorial.addItem(rs.getString("editorial"));
//            }
//
//        } catch (Exception e) {
//            System.err.println("Error, " + e);
//        }
//    }
//
//    public void ObtenerMateria(JComboBox materia) {
//        Connection conexion = establecerConexion();
//        try {
//            ps = conexion.prepareStatement("SELECT materia FROM materias");
//            rs = ps.executeQuery();
//
//            while (rs.next()) {
//                materia.addItem(rs.getString("materia"));
//            }
//
//        } catch (Exception e) {
//            System.err.println("Error, " + e);
//        }
//    }

}
