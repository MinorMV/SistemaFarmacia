package Logic;

public class Tratamiento extends Servicio {

    // Constructor
    
    public Tratamiento(int id, String nombre, double precio) {
        super(id, nombre, precio);
    }

    @Override
    public String getTipo() {
        return "Tratamiento";
    }
}
