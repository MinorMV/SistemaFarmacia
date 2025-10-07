package Logic;

public class ListaServicios {

    // Nodos de la lista 
    private static class Nodo {
        Servicio servicio;  
        int cantidad;       
        Nodo sig;           

        // Constructor del nodo
        Nodo(Servicio servicio, int cantidad) {
            this.servicio = servicio;
            this.cantidad = cantidad;
        }
    }

    // Apuntar al inicio de la lista

    private Nodo cabeza;

    // Agregar un nuevo servicio a la lista

    public void agregar(Servicio servicio, int cantidad) {
        if (servicio == null || cantidad <= 0)
            return; 

        Nodo nuevo = new Nodo(servicio, cantidad);

        
        if (cabeza == null) {
            cabeza = nuevo;
            return;
        }

     
        Nodo aux = cabeza;
        while (aux.sig != null)
            aux = aux.sig;
        aux.sig = nuevo;
    }

   
    public boolean estaVacia() {
        return cabeza == null;
    }

    
    public double calcularTotal() {
        double total = 0.0;
        Nodo aux = cabeza;
        while (aux != null) {
            total += aux.servicio.getPrecio() * aux.cantidad;
            aux = aux.sig;
        }
        return total;
    }

    // PAra imprimir el detalle del carrito tipo factura 
    public void imprimirDetalle() {
        Nodo aux = cabeza;
        while (aux != null) {
            double unitario = aux.servicio.getPrecio();
            double subtotal = unitario * aux.cantidad;
            System.out.printf("- %s x%d (%.2f colones c/u) -> %.2f colones%n",
                    aux.servicio.getNombre(),
                    aux.cantidad,
                    unitario,
                    subtotal);
            aux = aux.sig;
        }
    }
}
