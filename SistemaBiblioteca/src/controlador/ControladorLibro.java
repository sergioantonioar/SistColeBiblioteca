package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Autor; //prueba
import modelo.Conexion;
import modelo.ConsultasLibro;
import modelo.Libro;
import vista.FrmPanel;
import modelo.ConsultasLibro;

/**
 *
 * @author SERGIO
 */
public class ControladorLibro implements ActionListener, KeyListener, MouseListener, ItemListener {

    private FrmPanel vista;
    private Libro libro;
    private ConsultasLibro modeloLibro;

    public ControladorLibro(FrmPanel vista, Libro libro, ConsultasLibro modeloLibro) {
        this.vista = vista;
        this.libro = libro;
        this.modeloLibro = modeloLibro;

        //mostrar
        mostrarLibros();
        //modeloLibro.ObtenerAutor(vista.cbxAutor);
        //modeloLibro.ObtenerEditorial(vista.cbxEditorial);
        //modeloLibro.ObtenerMateria(vista.cbxMateria);

        //Indicando Listener
        vista.tblLibros.addMouseListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnInsLibro) {

            libro.setTitulo(vista.txtTitulo.getText());
            libro.setEditorial(Integer.parseInt(String.valueOf(vista.cbxEditorial.getSelectedItem())));
            libro.setAutor(Integer.parseInt(String.valueOf(vista.cbxAutor.getSelectedItem())));
            libro.setMateria(Integer.parseInt(String.valueOf(vista.cbxMateria.getSelectedItem())));
            libro.setCantidad(Integer.parseInt(vista.txtCantLibro.getText()));
            libro.setNum_pag(Integer.parseInt(vista.txtNumLibro.getText()));
            libro.setAnio_edicion(Integer.parseInt(vista.txtAnioEdicion.getText()));

            if (modeloLibro.insertar(libro)) {
                JOptionPane.showMessageDialog(null, "Registro insertado correctamente");
                limpiarCampos();
                //mostrarLibros();
            } else {
                JOptionPane.showMessageDialog(null, "Error al insertar registro");
                limpiarCampos();
            }
        }

    }

    private void limpiarCampos() {
        vista.txtIdLibro.setText(null);
        vista.txtIdLMat.setText(null);
        vista.txtIdLAutor.setText(null);
        vista.txtIdLEdi.setText(null);
        vista.txtTitulo.setText(null);
        vista.cbxEditorial.setSelectedItem(0);
        vista.cbxAutor.setSelectedItem(0);
        vista.cbxMateria.setSelectedItem(0);
        vista.txtCantLibro.setText(null);
        vista.txtNumLibro.setText(null);
        vista.txtAnioEdicion.setText(null);
    }

    private DefaultTableModel mostrarLibros() {
        DefaultTableModel modeloTabla = new DefaultTableModel();
        vista.tblLibros.setModel(modeloTabla);

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Conexion con = new Conexion();
            Connection conexion = con.establecerConexion();

            ps = conexion.prepareStatement("SELECT li.id, li.titulo, li.cantidad, li.num_pag, li.anio_edicion, e.editorial, a.autor, m.materia\n"
                    + "FROM libros li\n"
                    + "JOIN editorial e ON li.id_editorial = e.id\n"
                    + "JOIN autor a ON li.id_autor = a.id\n"
                    + "JOIN materias m ON li.id_materia = m.id");
            rs = ps.executeQuery(); //ejecutas consulta

            modeloTabla.addColumn("Id");
            modeloTabla.addColumn("Titulo");
            modeloTabla.addColumn("Cantidad");
            modeloTabla.addColumn("Num Página");
            modeloTabla.addColumn("Año Edición");
            modeloTabla.addColumn("Editorial");
            modeloTabla.addColumn("Autor");
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

    }//fin 

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
        //implementacion vacia
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = vista.tblLibros.rowAtPoint(e.getPoint());
        vista.txtIdLibro.setText(vista.tblLibros.getValueAt(fila, 0).toString());
        vista.txtTitulo.setText(vista.tblLibros.getValueAt(fila, 1).toString());
        vista.txtCantLibro.setText(vista.tblLibros.getValueAt(fila, 2).toString());
        vista.txtNumLibro.setText(vista.tblLibros.getValueAt(fila, 3).toString());
        vista.txtAnioEdicion.setText(vista.tblLibros.getValueAt(fila, 4).toString());
        vista.cbxEditorial.setSelectedItem(vista.tblLibros.getValueAt(fila, 5).toString());
        vista.cbxAutor.setSelectedItem(vista.tblLibros.getValueAt(fila, 6).toString());
        vista.cbxMateria.setSelectedItem(vista.tblLibros.getValueAt(fila, 7).toString());
        
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
    public void itemStateChanged(ItemEvent e) {
        //implementacion vacia
    }

}
