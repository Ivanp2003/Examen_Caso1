package modelo;

public class Materia {
    private int id;
    private String codigo;
    private String nombre;
    private int creditos;

    public Materia() {}

    // Getters y Setters
    public int obtenerId() { return id; }
    public void asignarId(int id) { this.id = id; }
    public String obtenerCodigo() { return codigo; }
    public void asignarCodigo(String codigo) { this.codigo = codigo; }
    public String obtenerNombre() { return nombre; }
    public void asignarNombre(String nombre) { this.nombre = nombre; }
    public int obtenerCreditos() { return creditos; }
    public void asignarCreditos(int creditos) { this.creditos = creditos; }
}