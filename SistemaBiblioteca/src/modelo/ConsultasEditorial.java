package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author SERGIO
 */
public class ConsultasEditorial extends Conexion{
    PreparedStatement ps; //para consultas
    
    public boolean insertar(Editorial editorial){
        Connection conexion = establecerConexion(); //establecer conexion a la BD
        
        try {
            ps = conexion.prepareStatement("insert into editorial (editorial) values (?)"); // lo que se va a insertar
            
            //completando los ?
            ps.setString(1, editorial.getEditorial());
            
            
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
    
    public boolean modificar(Editorial editorial){
        Connection conexion = establecerConexion();
        
        try {
            
            ps = conexion.prepareStatement("UPDATE editorial SET editorial=? WHERE id=?");
            
            ps.setString(1, editorial.getEditorial());
            ps.setInt(2,editorial.getId());
            
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
    
    public boolean eliminar(Editorial editorial){
        Connection conexion = establecerConexion();
        
        try {
            ps = conexion.prepareStatement("delete FROM editorial WHERE id=?");
            ps.setInt(1, editorial.getId());
            
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
