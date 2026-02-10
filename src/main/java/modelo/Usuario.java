package modelo;

public class Usuario {
    private int id;
    private String nombre;
    private String correo;
    private String clave;

    // Constructor vacío
    public Usuario() {
    }

    // Getters y Setters
    public int obtenerId() {
        return id;
    }

    public void asignarId(int id) {
        this.id = id;
    }

    public String obtenerNombre() {
        return nombre;
    }

    public void asignarNombre(String nombre) {
        this.nombre = nombre;
    }

    public String obtenerCorreo() {
        return correo;
    }

    public void asignarCorreo(String correo) {
        this.correo = correo;
    }

    public String obtenerClave() {
        return clave;
    }

    public void asignarClave(String clave) {
        this.clave = clave;
    }
}