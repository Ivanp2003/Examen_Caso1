package modelo;

public class Matricula {
    private int id;
    private int idEstudiante;
    private int idMateria;
    private String fecha;
    // Campos extra para mostrar nombres en el front
    private String nombreEstudiante;
    private String nombreMateria;

    public Matricula() {}

    // Getters y Setters...
    public int obtenerIdEstudiante() { return idEstudiante; }
    public void asignarIdEstudiante(int id) { this.idEstudiante = id; }
    public int obtenerIdMateria() { return idMateria; }
    public void asignarIdMateria(int id) { this.idMateria = id; }
    public String obtenerNombreEstudiante() { return nombreEstudiante; }
    public void asignarNombreEstudiante(String n) { this.nombreEstudiante = n; }
    public String obtenerNombreMateria() { return nombreMateria; }
    public void asignarNombreMateria(String n) { this.nombreMateria = n; }
}