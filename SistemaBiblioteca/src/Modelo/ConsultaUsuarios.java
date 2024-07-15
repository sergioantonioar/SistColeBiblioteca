package Modelo;

import Util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ConsultaUsuarios {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;

    public boolean registrar(Usuarios us) {
        String sql = "INSERT INTO usuarios (usuario, nombre, correo, clave) VALUES (?,?,?,?)";
        con = cn.getConnection();
        
        //para comprobar errores de conexion
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, us.getUsuario());
            ps.setString(2, us.getNombre());
            ps.setString(3, us.getCorreo());
            ps.setString(4, us.getClave());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean actualizar(Usuarios reg) {
        boolean res;
        String sql = "UPDATE usuarios SET usuario=?, nombre=?, correo=? WHERE id = ?";
        con = cn.getConnection();
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, reg.getUsuario());
            ps.setString(2, reg.getNombre());
            ps.setString(3, reg.getCorreo());
            ps.setInt(4, reg.getId());
            ps.execute();
            res = true;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            res = false;
        }
        return res;
    }

    public List Listar(String valor) {
        List<Usuarios> lista = new ArrayList();
        try {
            con = cn.getConnection();
            if ("".equalsIgnoreCase(valor)) {
                String sql = "SELECT * FROM usuarios ORDER BY id DESC";
                ps = con.prepareStatement(sql);
            } else {
                String sql = "SELECT * FROM usuarios WHERE nombre LIKE '%" + valor + "%' OR usuario LIKE '%" + valor + "%' OR correo LIKE '%" + valor + "%'";
                ps = con.prepareStatement(sql);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                Usuarios user = new Usuarios();
                user.setId(rs.getInt("id"));
                user.setUsuario(rs.getString("usuario"));
                user.setNombre(rs.getString("nombre"));
                user.setCorreo(rs.getString("correo"));
                lista.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return lista;
    }

    public boolean eliminar(int id) {

        String sql = "DELETE FROM usuarios WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.toString());
            }
        }
    }

    public Usuarios login(String user, String pass) {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND clave = ?";
        Usuarios l = new Usuarios();
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getRow() > 0) {
                    l.setId(rs.getInt("id"));
                    l.setUsuario(rs.getString("usuario"));
                    l.setNombre(rs.getString("nombre"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return l;
    }
}
