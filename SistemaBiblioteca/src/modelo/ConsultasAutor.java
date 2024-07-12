package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author SERGIO
 */
public class ConsultasAutor extends Conexion{
    PreparedStatement ps; //para consultas
    
    public boolean insertar(Autor autor){
        Connection conexion = establecerConexion(); //establecer conexion a la BD
        
        try {
            ps = conexion.prepareStatement("INSERT INTO autor (autor) VALUES (?)"); // lo que se va a insertar
            
            //completando los ?
            ps.setString(1, autor.getAutor());
            
            
            int resultado = ps.executeUpdate();
            
            if (resultado > 0) {
                return true;
            } else {
                return false;
            }
            
        } catch (Exception e) {
            System.err.println("Error, " + e);
            return false; //si ocurre exception
        }
        //Agregar finally para cerrar conexion. Siempre se ejecutara
        finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }
    
    public boolean modificar(Autor autor){
        Connection conexion = establecerConexion();
        
        try {
            
            ps = conexion.prepareStatement("UPDATE autor SET autor=? WHERE id=?");
            
            ps.setString(1, autor.getAutor());
            ps.setInt(2,autor.getId());
            
            int resultado = ps.executeUpdate();
            
            if(resultado>0){
                return true;
            }else{
                return false;
            }
            
        } catch (Exception e) {
            System.err.println("Error, " + e);
            return false; //si ocurre exception
        }
        finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }
    
    public boolean eliminar(Autor autor){
        Connection conexion = establecerConexion();
        
        try {
            ps = conexion.prepareStatement("DELETE FROM autor WHERE id=?");
            ps.setInt(1, autor.getId());
            
            int resultado = ps.executeUpdate();
            
            if(resultado>0){
                return true;
            }else{
                return false;
            }
            
        } catch (Exception e) {
            System.err.println("Error, "+e);
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
