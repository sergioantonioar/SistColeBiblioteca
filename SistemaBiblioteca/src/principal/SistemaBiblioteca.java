package principal;

import controlador.ControladorAutor;
import controlador.ControladorEditorial;
import controlador.ControladorEstudiante;
import controlador.ControladorMateria;
import modelo.Autor;
import modelo.ConsultasAutor;
import modelo.ConsultasEditorial;
import modelo.ConsultasEstudiante;
import modelo.ConsultasMateria;
import modelo.Editorial;
import modelo.Estudiante;
import modelo.Materia;
import vista.FrmPanel;

/**
 *
 * @author SERGIO
 */
public class SistemaBiblioteca {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FrmPanel vista = new FrmPanel();
        
        // -----EDITORIAL----
        Editorial editorial = new Editorial();
        ConsultasEditorial modeloEditorial = new ConsultasEditorial();
        ControladorEditorial controladorEditorial = new ControladorEditorial(vista, editorial, modeloEditorial);
        // ------------------
        
        // -----AUTOR----
        Autor autor = new Autor();
        ConsultasAutor modeloAutor = new ConsultasAutor();
        ControladorAutor controladorAutor = new ControladorAutor(vista, autor, modeloAutor);
        // ------------------
        
        //-----MATERIA----
        Materia materia = new Materia();
        ConsultasMateria modeloMateria = new ConsultasMateria();
        ControladorMateria controladorMateria = new ControladorMateria(vista, materia, modeloMateria);
        // ------------------
        
        //-----ESTUDIANTE----
        Estudiante estudiante = new Estudiante();
        ConsultasEstudiante modeloEstudiante = new ConsultasEstudiante();
        ControladorEstudiante controladorEstudiante = new ControladorEstudiante(vista, estudiante, modeloEstudiante);
        // ------------------
        
        vista.setVisible(true);
        
    }
    
}
