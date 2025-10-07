package ControlMain;

import Logic.Farmacia;
import Logic.Cliente;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final Farmacia farmacia = new Farmacia();

    public static void main(String[] args) {
        precargarDemo(); 

        int opcion;
        do {
            imprimirMenu();
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> opcionAgregarServicio();
                case 2 -> opcionAgregarClienteYCarrito();
                case 3 -> farmacia.atenderSiguienteCliente();
                case 4 -> opcionMostrarCatalogo();
                case 5 -> opcionVerCola();       
                case 6 -> System.out.println("Saliendo... ¡Gracias!"); 
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 6);
    }

    // Menú para Interacción 

    private static void imprimirMenu() {
        System.out.println("""
                
                ================== FARMACIA ==================
                1) Agregar Servicio al catálogo
                2) Agregar Cliente a la cola (y armar su carrito)
                3) Atender siguiente cliente
                4) Mostrar catálogo (Tratamientos / Medicamentos)
                5) Ver cola actual
                6) Salir
                ==============================================
                """);
    }

    // 1 Agregar servicio 

    private static void opcionAgregarServicio() {
        System.out.println("Tipo de servicio: 1) Tratamiento  2) Medicamento");
        int tipo = leerEntero("Opción: ");

        String nombre = leerTexto("Nombre: ");
        double precio = leerDouble("Precio (colones): ");

        if (tipo == 1) {
            boolean ok = farmacia.agregarTratamiento(nombre, precio);
            System.out.println(ok ? "Tratamiento agregado al catálogo." : "No se pudo agregar (IDs aleatorios agotados).");
        } else if (tipo == 2) {
            String indic = leerTexto("Indicaciones de uso: ");
            int stock = leerEntero("Stock inicial: ");
            boolean ok = farmacia.agregarMedicamento(nombre, precio, indic, stock);
            System.out.println(ok ? "Medicamento agregado al catálogo." : "No se pudo agregar (IDs aleatorios agotados o datos inválidos).");
        } else {
            System.out.println("Tipo inválido.");
        }
    }

    // 2 Agregar cliente y carrito 

    private static void opcionAgregarClienteYCarrito() {
        String nombre = leerTexto("Nombre completo: ");
        String cedula = leerTexto("Cédula: ");
        int p = leerEntero("¿Es preferencial? 1=Sí, 0=No: ");
        boolean prefer = (p == 1);

        boolean registrado = farmacia.registrarCliente(nombre, cedula, prefer);
        if (!registrado) {
            System.out.println("No se pudo registrar (cédula vacía/duplicada o datos inválidos).");
            return;
        }
        Cliente c = farmacia.obtenerUltimoClienteEnCola();
        System.out.println("Cliente agregado: " + c);

        opcionMostrarCatalogo();

        // Para armar el carrito
        while (true) {
            int id = leerEntero("ID de servicio a agregar (0 para terminar): ");
            if (id == 0) break;
            int cantidad = leerEntero("Cantidad: ");
            boolean ok = farmacia.agregarItemAlCarritoDelUltimo(id, cantidad);
            System.out.println(ok ? "-> Agregado al carrito." : "-> No se pudo (ID inválido o stock insuficiente).");
        }
    }

    // 4 Mostrar catálogo 

    private static void opcionMostrarCatalogo() {
        System.out.println("\n=== CATÁLOGO: TRATAMIENTOS ===");
        farmacia.imprimirCatalogoTratamientos();

        System.out.println("\n=== CATÁLOGO: MEDICAMENTOS ===");
        farmacia.imprimirCatalogoMedicamentos();
        System.out.println();
    }

    // 5 Ver cola actual 
    
    private static void opcionVerCola() {
        List<Cliente> cola = farmacia.obtenerColaActual(); 
        if (cola.isEmpty()) {
            System.out.println("Cola vacía.");
            return;
        }
        System.out.println("\n--- COLA ACTUAL (frente -> fondo) ---");
        for (int i = 0; i < cola.size(); i++) {
            Cliente c = cola.get(i);
            System.out.printf("%d) %s%n", (i + 1), c.toString());
        }
        System.out.println("-------------------------------------\n");
    }

    private static int leerEntero(String msg) {
        System.out.print(msg);
        while (!sc.hasNextInt()) {
            System.out.print("Ingrese un número válido: ");
            sc.next(); 
        }
        int v = sc.nextInt();
        sc.nextLine(); 
        return v;
    }

    private static double leerDouble(String msg) {
        System.out.print(msg);
        while (!sc.hasNextDouble()) {
            System.out.print("Ingrese un número válido (use punto para decimales): ");
            sc.next(); 
        }
        double v = sc.nextDouble();
        sc.nextLine(); 
        return v;
    }

    private static String leerTexto(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }

    // Demo de Prueba de SIstema de Farmacia

    private static void precargarDemo() {

        //  Catálogo

        farmacia.agregarTratamiento("Toma de presión", 2500);
        farmacia.agregarTratamiento("Inyección IM", 3500);
        farmacia.agregarMedicamento("Paracetamol 500mg", 120, "1 tableta cada 8h", 50);
        farmacia.agregarMedicamento("Antibiótico en Crema", 1800, "Aplicar 2 veces al día", 20);

        //  Obtener IDs aleatorios asignados por el BST

        Integer idTP   = farmacia.obtenerIdPorNombre("Toma de presión", "Tratamiento");
        Integer idIny  = farmacia.obtenerIdPorNombre("Inyección IM", "Tratamiento");
        Integer idPara = farmacia.obtenerIdPorNombre("Paracetamol 500mg", "Medicamento");
        Integer idUngu = farmacia.obtenerIdPorNombre("Antibiótico en Crema", "Medicamento");

        if (idTP == null || idIny == null || idPara == null || idUngu == null) {
            System.out.println("No se pudieron obtener IDs de demo. Verifique nombres/tipos.");
            return;
        }

        // Clientes en cola y carritos

        farmacia.registrarCliente("Ana Solís",     "101010101", false);
        farmacia.agregarItemAlCarritoDelUltimo(idTP,   1);  
        farmacia.agregarItemAlCarritoDelUltimo(idPara, 2);  

        farmacia.registrarCliente("Luis Mora",     "202020202", true); // preferencial
        farmacia.agregarItemAlCarritoDelUltimo(idUngu, 1);
        farmacia.agregarItemAlCarritoDelUltimo(idIny,  1);

        farmacia.registrarCliente("María Chacón",  "303030303", false);
        farmacia.agregarItemAlCarritoDelUltimo(idPara, 1);

        System.out.println("Demo listo: catálogo + 3 clientes (con 1 preferencial).");
        System.out.println("Use opción 5 para ver la cola; al atender, pasa primero el preferencial.");
    }
}
