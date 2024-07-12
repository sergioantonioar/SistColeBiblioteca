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
import modelo.Autor;
import modelo.Conexion;
import modelo.ConsultasAutor;
import vista.FrmPanel;

/**
 *
 * @author SERGIO
 */
public class ControladorAutor implements ActionListener, KeyListener, MouseListener{
    private FrmPanel vista;
    private Autor autor;
    private ConsultasAutor modeloAutor;

    public ControladorAutor(FrmPanel vista, Autor autor, ConsultasAutor modeloAutor) {
        this.vista = vista;
        this.autor = autor;
        this.modeloAutor = modeloAutor;
        
        //mostrar
        mostrarAutores();
        
        //Indicando Listener
        vista.btnInsAutor.addActionListener(this);
        vista.btnEliAutor.addActionListener(this);
        vista.btnModAutor.addActionListener(this);
        vista.tblAutor.addMouseListener(this);
        vista.txtBuscarAutor.addKeyListener(this);
        
        
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==vista.btnInsAutor){
            autor.setAutor(vista.txtAutor.getText());
            
            if(modeloAutor.insertar(autor)){
                JOptionPane.showMessageDialog(null, "Registro insertado correctamente");
                limpiarCampos();
                mostrarAutores();
            }else{
                JOptionPane.showMessageDialog(null, "Error al insertar registro");
                limpiarCampos();
            }
        }
        if (e.getSource() == vista.btnModAutor) {
            autor.setId(Integer.parseInt(vista.txtIdAutor.getText()));
            autor.setAutor(vista.txtAutor.getText());
            if (modeloAutor.modificar(autor)) {
                JOptionPane.showMessageDialog(null, "Registro modificado correctamente");
                limpiarCampos();
                mostrarAutores();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo modificar el registro");
                limpiarCampos();
            }
        }
        if(e.getSource()== vista.btnEliAutor){
            autor.setId(Integer.parseInt(vista.txtIdAutor.getText()));
            if(modeloAutor.eliminar(autor)){
                JOptionPane.showMessageDialog(null, "Registro eliminado correctamente");
                limpiarCampos();
                mostrarAutores();
            }else{
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el registro");
                limpiarCampos();
            }
        }
        
    }

    private void limpiarCampos() {
        vista.txtIdAutor.setText(null);
        vista.txtAutor.setText(null);
    }
    
    private DefaultTableModel mostrarAutores() {
        DefaultTableModel modeloTabla = new DefaultTableModel();
        vista.tblAutor.setModel(modeloTabla);

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Conexion con = new Conexion();
            Connection conexion = con.establecerConexion();

            ps = conexion.prepareStatement("select id, autor from autor");
            rs = ps.executeQuery(); //ejecutas consulta

            modeloTabla.addColumn("Id");
            modeloTabla.addColumn("Autor");

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
    
    private DefaultTableModel buscarAutor(String buscar){
        DefaultTableModel modeloTabla = new DefaultTableModel();
        vista.tblAutor.setModel(modeloTabla);
        
        //Busqueda especifica
        String where="";
        if(!"".equals(buscar)){ //si el campo no esta vacio
            
            where = "WHERE id LIKE '%"+buscar+"%' OR autor LIKE '%"+buscar+"%'";
        }
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Conexion con = new Conexion();
            Connection conexion = con.establecerConexion();
            
            ps = conexion.prepareStatement("SELECT id,autor from autor "+where);
            rs = ps.executeQuery();
            
            modeloTabla.addColumn("Id");
            modeloTabla.addColumn("Autor");
            
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
        //implementación vacia
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //implementación vacia
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getSource()== vista.txtBuscarAutor){
            String textoBuscar = vista.txtBuscarAutor.getText();
            buscarAutor(textoBuscar);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Conexion con = new Conexion();
            Connection conexion = con.establecerConexion();
            
            int fila = vista.tblAutor.getSelectedRow(); //identificar fila seleccionada
            String id = String.valueOf(vista.tblAutor.getValueAt(fila, 0));
            
            ps = conexion.prepareStatement("SELECT id, autor FROM autor WHERE id=?");
            ps.setString(1, id);
            rs = ps.executeQuery();
            
            while(rs.next()){
                vista.txtIdAutor.setText(String.valueOf(rs.getInt("id")));
                vista.txtAutor.setText(rs.getString("autor"));
            }

        } catch (Exception ex) {
            System.err.println("Error, "+ex);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //implementación vacia
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //implementación vacia
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //implementación vacia
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //implementación vacia
    }
    
    
    
    
    
}
