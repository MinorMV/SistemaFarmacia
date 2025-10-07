package Logic;

public class Medicamento extends Servicio {

    // Atributos 
    private String indicacion; 
    private int cantidad;      

    // Constructor
    public Medicamento(int id, String nombre, double precio, String indicacion, int cantidad) {
        super(id, nombre, precio);
        this.indicacion = indicacion;
        this.cantidad = cantidad;
    }

    //  Getters y Setters 
    public String getIndicacion() { 
        return indicacion; 
    }

    public void setIndicacion(String indicacion) { 
        this.indicacion = indicacion; 
    }

    public int getCantidad() { 
        return cantidad; 
    }

    public void setCantidad(int cantidad) { 
        this.cantidad = cantidad; 
    }

    //  Métodos heredados 
    @Override
    public String getTipo() {
        return "Medicamento";
    }


    @Override
    public String toString() {
        return super.toString() + 
               " | Indicaciones: " + indicacion +
               " | Stock: " + cantidad;
    }
}
