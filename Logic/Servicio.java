package Logic;

public abstract class Servicio {

    // Atributos 
    protected int id;
    protected String nombre;
    protected double precio;

    // Constructor
    public Servicio(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    // Getters y Setters 

    public int getId() { 
        return id; 
    }

    public void setId(int id) { 
        this.id = id; 
    }

    public String getNombre() { 
        return nombre; 
    }

    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    public double getPrecio() { 
        return precio; 
    }

    public void setPrecio(double precio) { 
        this.precio = precio; 
    }


    public abstract String getTipo();

    // Método toString 
    
    @Override
public String toString() {
    return "[" + id + "] " + nombre + " - " + String.format("%.2f colones", precio);
}

}
