package Logic;

public class Cliente {

    // Atributos
    private String nombreCompleto;
    private String cedula;        
    private boolean preferencial; 

    // Carrito 
    private ListaServicios carrito;

    // Constructor
    public Cliente(String nombreCompleto, String cedula, boolean preferencial) {
        this.nombreCompleto = nombreCompleto;
        this.cedula = cedula;
        this.preferencial = preferencial;
        this.carrito = new ListaServicios(); 
    }

    // Getters y Setters 
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public boolean isPreferencial() { return preferencial; }
    public void setPreferencial(boolean preferencial) { this.preferencial = preferencial; }

    public ListaServicios getCarrito() { return carrito; }

    @Override
    public String toString() {
        return nombreCompleto + " | cédula: " + cedula + (preferencial ? " | PREFERENCIAL" : "");
    }
}
