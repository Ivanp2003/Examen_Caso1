package modelo;

public class Estudiante {
    private int id;
    private String cedula;
    private String nombre;
    private String apellido;
    private String correo;

    public Estudiante() {}

    // Getters y Setters
    public int obtenerId() { return id; }
    public void asignarId(int id) { this.id = id; }
    public String obtenerCedula() { return cedula; }
    public void asignarCedula(String cedula) { this.cedula = cedula; }
    public String obtenerNombre() { return nombre; }
    public void asignarNombre(String nombre) { this.nombre = nombre; }
    public String obtenerApellido() { return apellido; }
    public void asignarApellido(String apellido) { this.apellido = apellido; }
    public String obtenerCorreo() { return correo; }
    public void asignarCorreo(String correo) { this.correo = correo; }
}