package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author SERGIO
 */
public class ConsultasMateria extends Conexion{
    PreparedStatement ps; //para consultas
    
    public boolean insertar(Materia materia){
        Connection conexion = establecerConexion(); //establecer conexion a la BD
        
        try {
            ps = conexion.prepareStatement("INSERT INTO materias (materia) VALUES (?)"); // lo que se va a insertar
            
            //completando los ?
            ps.setString(1, materia.getMateria());
            
            
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
    
    public boolean modificar(Materia materia){
        Connection conexion = establecerConexion();
        
        try {
            
            ps = conexion.prepareStatement("UPDATE materias SET materia=? WHERE id=?");
            
            ps.setString(1, materia.getMateria());
            ps.setInt(2,materia.getId());
            
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
    
    public boolean eliminar(Materia materia){
        Connection conexion = establecerConexion();
        
        try {
            ps = conexion.prepareStatement("DELETE FROM materias WHERE id=?");
            ps.setInt(1, materia.getId());
            
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
