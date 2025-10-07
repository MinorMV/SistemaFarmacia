package Logic;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Farmacia {

    private ArbolServicios catalogo;          
    private LinkedList<Cliente> colaClientes; 
    private Set<String> cedulasRegistradas;   

    // Constructor

    public Farmacia() {
        this.catalogo = new ArbolServicios();
        this.colaClientes = new LinkedList<>();
        this.cedulasRegistradas = new HashSet<>();
    }

    // Catálogo 

    // Agregar tratamiento 

    public boolean agregarTratamiento(String nombre, double precio) {
        if (nombre == null || nombre.isBlank() || precio < 0) return false;
        return catalogo.insertarConIdAleatorio("Tratamiento", nombre, precio, null, null);
    }

    // Agregar medicamento 

    public boolean agregarMedicamento(String nombre, double precio, String indicacion, int stock) {
        if (nombre == null || nombre.isBlank() || precio < 0) return false;
        if (indicacion == null || indicacion.isBlank() || stock < 0) return false;
        return catalogo.insertarConIdAleatorio("Medicamento", nombre, precio, indicacion, stock);
    }

    // Mostrar por categoría 

    public void imprimirCatalogoTratamientos() { catalogo.imprimirEnOrdenPorTipo("Tratamiento"); }
    public void imprimirCatalogoMedicamentos() { catalogo.imprimirEnOrdenPorTipo("Medicamento"); }

    // Buscar  por ID
    public Integer obtenerIdPorNombre(String nombre, String tipo) {
        if (nombre == null || nombre.isBlank() || tipo == null || tipo.isBlank()) return null;
        return catalogo.buscarIdPorNombre(nombre, tipo);
    }

    //  Clientes 

    public boolean registrarCliente(String nombreCompleto, String cedula, boolean preferencial) {
        if (nombreCompleto == null || nombreCompleto.isBlank()) return false;
        if (cedula == null || cedula.isBlank()) return false;
        if (cedulasRegistradas.contains(cedula)) return false;

        Cliente nuevo = new Cliente(nombreCompleto, cedula, preferencial);
        colaClientes.addLast(nuevo);
        cedulasRegistradas.add(cedula);
        return true;
    }

    // Para armar el carrito 
    
    public Cliente obtenerUltimoClienteEnCola() { return colaClientes.peekLast(); }

    // Para agregar item al carrito del último cliente 

    public boolean agregarItemAlCarritoDelUltimo(int idServicio, int cantidad) {
        Cliente c = colaClientes.peekLast();
        if (c == null || cantidad <= 0) return false;

        Servicio s = catalogo.buscar(idServicio);
        if (s == null) return false;

        if (s instanceof Medicamento) {
            if (!catalogo.hayStockSuficienteMedicamento(idServicio, cantidad)) return false;
            if (!catalogo.rebajarStockMedicamento(idServicio, cantidad)) return false;
        }
        c.getCarrito().agregar(s, cantidad);
        return true;
    }

    // Atiende al siguiente 

    public void atenderSiguienteCliente() {
        if (colaClientes.isEmpty()) {
            System.out.println("No hay clientes en la cola.");
            return;
        }

        int idxPref = -1;
        for (int i = 0; i < colaClientes.size(); i++) {
            if (colaClientes.get(i).isPreferencial()) { idxPref = i; break; }
        }

        Cliente aAtender = (idxPref >= 0) ? colaClientes.remove(idxPref) : colaClientes.removeFirst();

        System.out.println("\n=== ATENDIENDO ===");
        System.out.println(aAtender);

        if (aAtender.getCarrito().estaVacia()) {
            System.out.println("No tiene servicios en el carrito.");
        } else {
            aAtender.getCarrito().imprimirDetalle();
            double total = aAtender.getCarrito().calcularTotal();
            System.out.printf("TOTAL: %.2f colones%n", total);
        }
        System.out.println("==================\n");

        // Descartar cédula 
        cedulasRegistradas.remove(aAtender.getCedula());
    }

    // Getters 

    public int getClientesEnCola() { return colaClientes.size(); }
    public ArbolServicios getCatalogo() { return catalogo; } 
public java.util.List<Cliente> obtenerColaActual() {
    return java.util.List.copyOf(colaClientes);
}


}
