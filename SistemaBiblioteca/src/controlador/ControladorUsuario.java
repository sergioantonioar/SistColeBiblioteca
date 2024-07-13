package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import modelo.Conexion;
import modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConsultasUsuario;
import vista.FrmPanel;

/**
 *
 * @author SERGIO
 */
public class ControladorUsuario implements ActionListener, KeyListener, MouseListener{
    private FrmPanel vista;
    private Usuario usuario;
    private ConsultasUsuario modeloUsuario;

    public ControladorUsuario(FrmPanel vista, Usuario usuario, ConsultasUsuario modeloUsuario) {
        this.vista = vista;
        this.usuario = usuario;
        this.modeloUsuario = modeloUsuario;
        
        //mostrar
        mostrarUsuarios();
        
        //Indicando Listener
        vista.btnInsUsuario.addActionListener(this);
        vista.btnModUser.addActionListener(this);
        vista.btnEliUser.addActionListener(this);
        vista.tblUsuarios.addMouseListener(this);
        vista.txtBuscarUser.addKeyListener(this);
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnInsUsuario) {
            
            usuario.setUsuario(vista.txtUsuario.getText());
            usuario.setNombre(vista.txtNombre.getText());
            usuario.setCorreo(vista.txtCorreo.getText());
            usuario.setClave(vista.txtClave.getText());

            if (modeloUsuario.insertar(usuario)) {
                JOptionPane.showMessageDialog(null, "Registro insertado correctamente");
                limpiarCampos();
                mostrarUsuarios();
            } else {
                JOptionPane.showMessageDialog(null, "Error al insertar registro");
                limpiarCampos();
            }
        }
        if (e.getSource() == vista.btnModUser) {
            
            usuario.setId(Integer.parseInt(vista.txtIdUser.getText()));
            usuario.setUsuario(vista.txtUsuario.getText());
            usuario.setNombre(vista.txtNombre.getText());
            usuario.setCorreo(vista.txtCorreo.getText());
            usuario.setClave(vista.txtClave.getText());
            
            if (modeloUsuario.modificar(usuario)) {
                JOptionPane.showMessageDialog(null, "Registro modificado correctamente");
                limpiarCampos();
                mostrarUsuarios();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo modificar el registro");
                limpiarCampos();
            }
        }
        if(e.getSource()== vista.btnEliUser){
            usuario.setId(Integer.parseInt(vista.txtIdUser.getText()));
            if(modeloUsuario.eliminar(usuario)){
                JOptionPane.showMessageDialog(null, "Registro eliminado correctamente");
                limpiarCampos();
                mostrarUsuarios();
            }else{
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el registro");
                limpiarCampos();
            }
        }
    }
    
    private void limpiarCampos() {
        vista.txtIdUser.setText(null);
        vista.txtUsuario.setText(null);
        vista.txtNombre.setText(null);
        vista.txtCorreo.setText(null);
        vista.txtClave.setText(null);
        
    }
    
    private DefaultTableModel mostrarUsuarios() {
        DefaultTableModel modeloTabla = new DefaultTableModel();
        vista.tblUsuarios.setModel(modeloTabla);

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Conexion con = new Conexion();
            Connection conexion = con.establecerConexion();

            ps = conexion.prepareStatement("select id, usuario, nombre, correo from usuarios");
            rs = ps.executeQuery(); //ejecutas consulta

            modeloTabla.addColumn("Id");
            modeloTabla.addColumn("Usuario");
            modeloTabla.addColumn("Nombre");
            modeloTabla.addColumn("Correo");

            ResultSetMetaData rsMD = rs.getMetaData(); //obteniendo datos consulta
            int cantidadColumna = rsMD.getColumnCount(); // obtener columnas

            while (rs.next()) { //mientras hay registros
                Object fila[] = new Object[cantidadColumna];
                for (int i = 0; i < cantidadColumna; i++) {
                    fila[i] = rs.getObject(i + 1); //devuelve desde el id
                }

                modeloTabla.addRow(fila); //agregando a tabla
            }

        } catch (SQLException e) {
            System.err.println("Error, " + e);
        }
        return modeloTabla;

    }//fin 
    
    private DefaultTableModel buscarUsuario(String buscar){
        DefaultTableModel modeloTabla = new DefaultTableModel();
        vista.tblUsuarios.setModel(modeloTabla);
        
        //Busqueda especifica
        String where="";
        if(!"".equals(buscar)){ //si el campo no esta vacio
            
            where = "WHERE id LIKE '%"+buscar+"%' OR usuario LIKE '%"+buscar+"%' OR nombre LIKE '%"+buscar+"%'";
        }
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Conexion con = new Conexion();
            Connection conexion = con.establecerConexion();
            
            ps = conexion.prepareStatement("SELECT id,usuario,nombre,correo from usuarios "+where);
            rs = ps.executeQuery();
            
            modeloTabla.addColumn("Id");
            modeloTabla.addColumn("Usuario");
            modeloTabla.addColumn("Nombre");
            modeloTabla.addColumn("Correo");
            
            ResultSetMetaData rsMD = rs.getMetaData(); //obteniendo datos consulta
            int cantidadColumna = rsMD.getColumnCount(); // obtener columnas
            
            while (rs.next()) { //mientras hay registros
                Object fila[] = new Object[cantidadColumna];
                for (int i = 0; i < cantidadColumna; i++) {
                    fila[i] = rs.getObject(i + 1); //devuelve desde el id
                }

                modeloTabla.addRow(fila); //agregando a tabla
            }
            
        } catch (Exception e) {
            System.err.println("Error, " + e);
        }
        return modeloTabla;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //implementacion vacia
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //implementacion vacia
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getSource()== vista.txtBuscarUser){
            String textoBuscar = vista.txtBuscarUser.getText();
            buscarUsuario(textoBuscar);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Conexion con = new Conexion();
            Connection conexion = con.establecerConexion();
            
            int fila = vista.tblUsuarios.getSelectedRow(); //identificar fila seleccionada
            String id = String.valueOf(vista.tblUsuarios.getValueAt(fila, 0));
            
            ps = conexion.prepareStatement("SELECT id,usuario,nombre,correo,clave FROM usuarios WHERE id=?");
            ps.setString(1, id);
            rs = ps.executeQuery();
            
            while(rs.next()){
                vista.txtIdUser.setText(String.valueOf(rs.getInt("id")));
                vista.txtUsuario.setText(rs.getString("usuario"));
                vista.txtNombre.setText(rs.getString("nombre"));
                vista.txtCorreo.setText(rs.getString("correo"));
                vista.txtClave.setText(rs.getString("clave"));
                
            }

        } catch (Exception ex) {
            System.err.println("Error, "+ex);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //implementacion vacia
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //implementacion vacia
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //implementacion vacia
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //implementacion vacia
    }
    
    
    
}
