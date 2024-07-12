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
import modelo.ConsultasEstudiante;
import modelo.Estudiante;
import vista.FrmPanel;

/**
 *
 * @author SERGIO
 */
public class ControladorEstudiante implements ActionListener, KeyListener, MouseListener{
    private FrmPanel vista;
    private Estudiante estudiante;
    private ConsultasEstudiante modeloEstudiante;

    public ControladorEstudiante(FrmPanel vista, Estudiante estudiante, ConsultasEstudiante modeloEstudiante) {
        this.vista = vista;
        this.estudiante = estudiante;
        this.modeloEstudiante = modeloEstudiante;
        
        //mostrar
        mostrarEstudiantes();
        
        //Indicando Listeners
        vista.btnInsEst.addActionListener(this);
        vista.btnEliEst.addActionListener(this);
        vista.btnModEst.addActionListener(this);
        vista.tblEstudiantes.addMouseListener(this);
        vista.txtBuscarEst.addKeyListener(this);

    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnInsEst) {
            
            estudiante.setDocumento(vista.txtDocumento.getText());
            estudiante.setCodigo(vista.txtCodigo.getText());
            estudiante.setNombre(vista.txtEstudiante.getText());
            estudiante.setTelefono(vista.txtTelefono.getText());
            estudiante.setNivel(vista.txtNivel.getText());
            estudiante.setGrado(vista.txtGrado.getText());
            estudiante.setSeccion(vista.txtSeccion.getText());

            if (modeloEstudiante.insertar(estudiante)) {
                JOptionPane.showMessageDialog(null, "Registro insertado correctamente");
                limpiarCampos();
                mostrarEstudiantes();
            } else {
                JOptionPane.showMessageDialog(null, "Error al insertar registro");
                limpiarCampos();
            }
        }
        if (e.getSource() == vista.btnModEst) {
            
            estudiante.setId(Integer.parseInt(vista.txtIdEst.getText()));
            estudiante.setDocumento(vista.txtDocumento.getText());
            estudiante.setCodigo(vista.txtCodigo.getText());
            estudiante.setNombre(vista.txtEstudiante.getText());
            estudiante.setTelefono(vista.txtTelefono.getText());
            estudiante.setNivel(vista.txtNivel.getText());
            estudiante.setGrado(vista.txtGrado.getText());
            estudiante.setSeccion(vista.txtSeccion.getText());
            
            if (modeloEstudiante.modificar(estudiante)) {
                JOptionPane.showMessageDialog(null, "Registro modificado correctamente");
                limpiarCampos();
                mostrarEstudiantes();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo modificar el registro");
                limpiarCampos();
            }
        }
        if(e.getSource()== vista.btnEliEst){
            estudiante.setId(Integer.parseInt(vista.txtIdEst.getText()));
            if(modeloEstudiante.eliminar(estudiante)){
                JOptionPane.showMessageDialog(null, "Registro eliminado correctamente");
                limpiarCampos();
                mostrarEstudiantes();
            }else{
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el registro");
                limpiarCampos();
            }
        }
        
        
    }
    
    private void limpiarCampos() {
        vista.txtIdEst.setText(null);
        vista.txtDocumento.setText(null);
        vista.txtCodigo.setText(null);
        vista.txtEstudiante.setText(null);
        vista.txtTelefono.setText(null);
        vista.txtNivel.setText(null);
        vista.txtGrado.setText(null);
        vista.txtSeccion.setText(null);
    }
    
    private DefaultTableModel mostrarEstudiantes() {
        DefaultTableModel modeloTabla = new DefaultTableModel();
        vista.tblEstudiantes.setModel(modeloTabla);

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Conexion con = new Conexion();
            Connection conexion = con.establecerConexion();

            ps = conexion.prepareStatement("select id, documento, codigo, nombre, telefono, nivel, grado, seccion from estudiantes");
            rs = ps.executeQuery(); //ejecutas consulta

            modeloTabla.addColumn("Id");
            modeloTabla.addColumn("Documento");
            modeloTabla.addColumn("Código");
            modeloTabla.addColumn("Nombre");
            modeloTabla.addColumn("Teléfono");
            modeloTabla.addColumn("Nivel");
            modeloTabla.addColumn("Grado");
            modeloTabla.addColumn("Sección");

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
    
    private DefaultTableModel buscarEstudiante(String buscar){
        DefaultTableModel modeloTabla = new DefaultTableModel();
        vista.tblEstudiantes.setModel(modeloTabla);
        
        //Busqueda especifica
        String where="";
        if(!"".equals(buscar)){ //si el campo no esta vacio
            
            where = "WHERE id LIKE '%"+buscar+"%' OR documento LIKE '%"+buscar+"%' OR codigo LIKE '%"+buscar+"%' OR nombre LIKE '%"+buscar+"%' OR seccion LIKE '%"+buscar+"%'";
        }
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Conexion con = new Conexion();
            Connection conexion = con.establecerConexion();
            
            ps = conexion.prepareStatement("SELECT * from estudiantes "+where);
            rs = ps.executeQuery();
            
            modeloTabla.addColumn("Id");
            modeloTabla.addColumn("Documento");
            modeloTabla.addColumn("Código");
            modeloTabla.addColumn("Nombre");
            modeloTabla.addColumn("Teléfono");
            modeloTabla.addColumn("Nivel");
            modeloTabla.addColumn("Grado");
            modeloTabla.addColumn("Sección");
            
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
        //Implementacion vacia
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //Implementacion vacia
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getSource()== vista.txtBuscarEst){
            String textoBuscar = vista.txtBuscarEst.getText();
            buscarEstudiante(textoBuscar);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Conexion con = new Conexion();
            Connection conexion = con.establecerConexion();
            
            int fila = vista.tblEstudiantes.getSelectedRow(); //identificar fila seleccionada
            String id = String.valueOf(vista.tblEstudiantes.getValueAt(fila, 0));
            
            ps = conexion.prepareStatement("SELECT * FROM estudiantes WHERE id=?");
            ps.setString(1, id);
            rs = ps.executeQuery();
            
            while(rs.next()){
                vista.txtIdEst.setText(String.valueOf(rs.getInt("id")));
                vista.txtDocumento.setText(rs.getString("documento"));
                vista.txtCodigo.setText(rs.getString("Codigo"));
                vista.txtEstudiante.setText(rs.getString("nombre"));
                vista.txtTelefono.setText(rs.getString("telefono"));
                vista.txtNivel.setText(rs.getString("nivel"));
                vista.txtGrado.setText(rs.getString("grado"));
                vista.txtSeccion.setText(rs.getString("seccion"));
            }

        } catch (Exception ex) {
            System.err.println("Error, "+ex);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Implementacion vacia
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //Implementacion vacia
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //Implementacion vacia
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //Implementacion vacia
    }
    
    
    
    
}
