package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Conexion;
import modelo.ConsultasEditorial;
import modelo.Editorial;
import vista.FrmPanel;

/**
 *
 * @author SERGIO
 */

public class ControladorEditorial implements ActionListener, KeyListener, MouseListener {

    private FrmPanel vistaEditorial;
    private Editorial editorial;
    private ConsultasEditorial modeloEditorial;

    public ControladorEditorial(FrmPanel vistaEditorial, Editorial editorial, ConsultasEditorial modeloEditorial) {
        this.vistaEditorial = vistaEditorial;
        this.editorial = editorial;
        this.modeloEditorial = modeloEditorial;

        //mostrar
        mostrarEditoriales();

        //Indicando Listener
        vistaEditorial.btnInsertarEditorial.addActionListener(this);
        vistaEditorial.btnEliEditorial.addActionListener(this);
        vistaEditorial.btnModEditorial.addActionListener(this);
        vistaEditorial.tblEditorial.addMouseListener(this);
        vistaEditorial.txtBuscarEditorial.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaEditorial.btnInsertarEditorial) {
            
            editorial.setEditorial(vistaEditorial.txtEditorial.getText());

            if (modeloEditorial.insertar(editorial)) {
                JOptionPane.showMessageDialog(null, "Registro insertado correctamente");
                limpiarCampos();
                mostrarEditoriales();
            } else {
                JOptionPane.showMessageDialog(null, "Error al insertar registro");
                limpiarCampos();
            }
        }
        if (e.getSource() == vistaEditorial.btnModEditorial) {
            editorial.setId(Integer.parseInt(vistaEditorial.txtIdEditorial.getText()));
            editorial.setEditorial(vistaEditorial.txtEditorial.getText());
            if (modeloEditorial.modificar(editorial)) {
                JOptionPane.showMessageDialog(null, "Registro modificado correctamente");
                limpiarCampos();
                mostrarEditoriales();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo modificar el registro");
                limpiarCampos();
            }
        }
        if(e.getSource()== vistaEditorial.btnEliEditorial){
            editorial.setId(Integer.parseInt(vistaEditorial.txtIdEditorial.getText()));
            if(modeloEditorial.eliminar(editorial)){
                JOptionPane.showMessageDialog(null, "Registro eliminado correctamente");
                limpiarCampos();
                mostrarEditoriales();
            }else{
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el registro");
                limpiarCampos();
            }
        }
    }

    private void limpiarCampos() {
        vistaEditorial.txtIdEditorial.setText(null);
        vistaEditorial.txtEditorial.setText(null);
    }

    private DefaultTableModel mostrarEditoriales() {
        DefaultTableModel modeloTabla = new DefaultTableModel();
        vistaEditorial.tblEditorial.setModel(modeloTabla);

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Conexion con = new Conexion();
            Connection conexion = con.establecerConexion();

            ps = conexion.prepareStatement("select id, editorial from editorial");
            rs = ps.executeQuery(); //ejecutas consulta

            modeloTabla.addColumn("Id");
            modeloTabla.addColumn("Editorial");

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
    
    private DefaultTableModel buscarEditorial(String buscar){
        DefaultTableModel modeloTabla = new DefaultTableModel();
        vistaEditorial.tblEditorial.setModel(modeloTabla);
        
        //Busqueda especifica
        String where="";
        if(!"".equals(buscar)){ //si el campo no esta vacio
            
            where = "WHERE id LIKE '%"+buscar+"%' OR editorial LIKE '%"+buscar+"%'";
        }
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Conexion con = new Conexion();
            Connection conexion = con.establecerConexion();
            
            ps = conexion.prepareStatement("SELECT id,editorial from editorial "+where);
            rs = ps.executeQuery();
            
            modeloTabla.addColumn("Id");
            modeloTabla.addColumn("Editorial");
            
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
        //implementacion
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //implementacion
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Conexion con = new Conexion();
            Connection conexion = con.establecerConexion();
            
            int fila = vistaEditorial.tblEditorial.getSelectedRow(); //identificar fila seleccionada
            String id = String.valueOf(vistaEditorial.tblEditorial.getValueAt(fila, 0));
            
            ps = conexion.prepareStatement("SELECT id, editorial FROM editorial WHERE id=?");
            ps.setString(1, id);
            rs = ps.executeQuery();
            
            while(rs.next()){
                vistaEditorial.txtIdEditorial.setText(String.valueOf(rs.getInt("id")));
                vistaEditorial.txtEditorial.setText(rs.getString("editorial"));
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

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getSource()== vistaEditorial.txtBuscarEditorial){
            String textoBuscar = vistaEditorial.txtBuscarEditorial.getText();
            buscarEditorial(textoBuscar);
        }
    }

}
