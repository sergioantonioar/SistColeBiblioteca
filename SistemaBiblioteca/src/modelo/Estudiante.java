
package Modelo;

public class Estudiante {
    private int id;
    private String documento;
    private String codigo;
    private String nombre;
    private String telefono;
    private String nivel;
    private String grado;
    private String seccion;

    public Estudiante() {
    }

    public Estudiante(int id, String documento, String codigo, String nombre, String telefono, String nivel, String grado, String seccion) {
        this.id = id;
        this.documento = documento;
        this.codigo = codigo;
        this.nombre = nombre;
        this.telefono = telefono;
        this.nivel = nivel;
        this.grado = grado;
        this.seccion = seccion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    
}
