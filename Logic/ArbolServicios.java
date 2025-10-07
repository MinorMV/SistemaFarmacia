package Logic;

import java.util.Random;

public class ArbolServicios {

    // Nodo

    private static class Nodo {
        Servicio dato;
        Nodo izq, der;
        Nodo(Servicio s) { this.dato = s; }
    }

    private Nodo raiz;
    private final Random rnd = new Random();

    //Insertar un nuevo servicio creando su id aleatorio [1..100].
    
    public boolean insertarConIdAleatorio(String tipo, String nombre, double precio, String indicacion, Integer stock) {
        for (int intentos = 0; intentos < 150; intentos++) { // intentos - rango de ids
            int id = 1 + rnd.nextInt(100);
            if (buscar(id) != null) continue; 

            // Crea el objeto 
            Servicio s;
            if ("Medicamento".equalsIgnoreCase(tipo)) {
                int st = (stock == null ? 0 : stock);
                String ind = (indicacion == null ? "" : indicacion);
                s = new Medicamento(id, nombre, precio, ind, st);
            } else if ("Tratamiento".equalsIgnoreCase(tipo)) {
                s = new Tratamiento(id, nombre, precio);
            } else {
                return false; 
            }

            insertar(s); 
            return true;
        }
        return false; // no se consiguió id libre en el rango
    }

    // Inserta un servicio ya construido 
    public void insertar(Servicio s) {
        raiz = insertarRec(raiz, s);
    }

    // Inserción 
    private Nodo insertarRec(Nodo actual, Servicio s) {
        if (actual == null) return new Nodo(s);
        if (s.getId() < actual.dato.getId()) {
            actual.izq = insertarRec(actual.izq, s);
        } else if (s.getId() > actual.dato.getId()) {
            actual.der = insertarRec(actual.der, s);
        } else {
            throw new IllegalArgumentException("ID duplicado en el catálogo: " + s.getId());
        }
        return actual;
    }

    // Buscar por id 
    public Servicio buscar(int id) {
        Nodo a = raiz;
        while (a != null) {
            if (id == a.dato.getId()) return a.dato;
            a = (id < a.dato.getId()) ? a.izq : a.der;
        }
        return null;
    }


    public void imprimirEnOrdenPorTipo(String tipo) {
        imprimirEnOrdenPorTipoRec(raiz, tipo);
    }

    private void imprimirEnOrdenPorTipoRec(Nodo n, String tipo) {
        if (n == null) return;
        imprimirEnOrdenPorTipoRec(n.izq, tipo);
        if (n.dato.getTipo().equalsIgnoreCase(tipo)) {
            System.out.println(n.dato.toString());
        }
        imprimirEnOrdenPorTipoRec(n.der, tipo);
    }

    // Consulta de stock suficiente para medicamento

    public boolean hayStockSuficienteMedicamento(int id, int cantidadSolicitada) {
        Servicio s = buscar(id);
        if (s instanceof Medicamento m) {
            return cantidadSolicitada > 0 && m.getCantidad() >= cantidadSolicitada;
        }
        return false; 
    }

    // Rebaja de stock para medicamento

    public boolean rebajarStockMedicamento(int id, int cantidad) {
        Servicio s = buscar(id);
        if (s instanceof Medicamento m) {
            if (cantidad > 0 && m.getCantidad() >= cantidad) {
                m.setCantidad(m.getCantidad() - cantidad);
                return true;
            }
        }
        return false;
    }

    //Demo 

    public Integer buscarIdPorNombre(String nombre, String tipo) {
        return buscarIdPorNombreRec(raiz, nombre, tipo);
    }

    private Integer buscarIdPorNombreRec(Nodo n, String nombre, String tipo) {
        if (n == null) return null;
        Integer izq = buscarIdPorNombreRec(n.izq, nombre, tipo);
        if (izq != null) return izq;
        if (n.dato.getNombre().equalsIgnoreCase(nombre)
                && n.dato.getTipo().equalsIgnoreCase(tipo)) {
            return n.dato.getId();
        }
        return buscarIdPorNombreRec(n.der, nombre, tipo);
    }
}
