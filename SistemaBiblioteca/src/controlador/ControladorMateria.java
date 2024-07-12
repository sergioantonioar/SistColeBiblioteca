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
import modelo.ConsultasMateria;
import modelo.Materia;
import vista.FrmPanel;

/**
 *
 * @author SERGIO
 */
public class ControladorMateria implements ActionListener, KeyListener, MouseListener {
    private FrmPanel vista;
    private Materia materia;
    private ConsultasMateria modeloMateria;

    public ControladorMateria(FrmPanel vista, Materia materia, ConsultasMateria modeloMateria) {
        this.vista = vista;
        this.materia = materia;
        this.modeloMateria = modeloMateria;
        
        //mostrar
        mostrarMaterias();
        
        //Indicando Listener
        vista.btnInsMateria.addActionListener(this);
        vista.btnEliMateria.addActionListener(this);
        vista.btnModMateria.addActionListener(this);
        vista.tblMateria.addMouseListener(this);
        vista.txtBuscarMateria.addKeyListener(this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==vista.btnInsMateria){
            materia.setMateria(vista.txtMateria.getText());
            
            if(modeloMateria.insertar(materia)){
                JOptionPane.showMessageDialog(null, "Registro insertado correctamente");
                limpiarCampos();
                mostrarMaterias();
            }else{
                JOptionPane.showMessageDialog(null, "Error al insertar registro");
                limpiarCampos();
            }
        }
        if (e.getSource() == vista.btnModMateria) {
            materia.setId(Integer.parseInt(vista.txtIdMateria.getText()));
            materia.setMateria(vista.txtMateria.getText());
            if (modeloMateria.modificar(materia)) {
                JOptionPane.showMessageDialog(null, "Registro modificado correctamente");
                limpiarCampos();
                mostrarMaterias();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo modificar el registro");
                limpiarCampos();
            }
        }
        if(e.getSource()== vista.btnEliMateria){
            materia.setId(Integer.parseInt(vista.txtIdMateria.getText()));
            if(modeloMateria.eliminar(materia)){
                JOptionPane.showMessageDialog(null, "Registro eliminado correctamente");
                limpiarCampos();
                mostrarMaterias();
            }else{
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el registro");
                limpiarCampos();
            }
        }
    }
    
    private void limpiarCampos() {
        vista.txtIdMateria.setText(null);
        vista.txtMateria.setText(null);
    }

    private DefaultTableModel mostrarMaterias() {
        DefaultTableModel modeloTabla = new DefaultTableModel();
        vista.tblMateria.setModel(modeloTabla);

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Conexion con = new Conexion();
            Connection conexion = con.establecerConexion();

            ps = conexion.prepareStatement("select id, materia from materias");
            rs = ps.executeQuery(); //ejecutas consulta

            modeloTabla.addColumn("Id");
            modeloTabla.addColumn("Materia");

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

    }//fin metodo
    
    private DefaultTableModel buscarMateria(String buscar){
        DefaultTableModel modeloTabla = new DefaultTableModel();
        vista.tblMateria.setModel(modeloTabla);
        
        //Busqueda especifica
        String where="";
        if(!"".equals(buscar)){ //si el campo no esta vacio
            
            where = "WHERE id LIKE '%"+buscar+"%' OR materia LIKE '%"+buscar+"%'";
        }
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Conexion con = new Conexion();
            Connection conexion = con.establecerConexion();
            
            ps = conexion.prepareStatement("SELECT id,materia from materias "+where);
            rs = ps.executeQuery();
            
            modeloTabla.addColumn("Id");
            modeloTabla.addColumn("Materia");
            
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
        if(e.getSource()== vista.txtBuscarMateria){
            String textoBuscar = vista.txtBuscarMateria.getText();
            buscarMateria(textoBuscar);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Conexion con = new Conexion();
            Connection conexion = con.establecerConexion();
            
            int fila = vista.tblMateria.getSelectedRow(); //identificar fila seleccionada
            String id = String.valueOf(vista.tblMateria.getValueAt(fila, 0));
            
            ps = conexion.prepareStatement("SELECT id, materia FROM materias WHERE id=?");
            ps.setString(1, id);
            rs = ps.executeQuery();
            
            while(rs.next()){
                vista.txtIdMateria.setText(String.valueOf(rs.getInt("id")));
                vista.txtMateria.setText(rs.getString("materia"));
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
