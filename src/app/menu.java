package app;

import productos.Producto;
import pedidos.Pedido;
import pedidos.LineaPedido;
import excepciones.StockInsuficienteException;

import java.util.*;

public class menu {
    static Scanner sc = new Scanner(System.in);
    static List<Producto> productos = new ArrayList<>();
    static List<Pedido> pedidos = new ArrayList<>();

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Selecciona una opcion: ");

            switch (opcion) {
                case 1 -> agregarProducto();
                case 2 -> listarProductos();
                case 3 -> buscarActualizarProducto();
                case 4 -> eliminarProducto();
                case 5 -> crearPedido();
                case 6 -> listarPedidos();
                case 7 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 7);
    }

    static void mostrarMenu() {
        System.out.println("""
            \n--- MENU PRINCIPAL ---
            1. Agregar Producto
            2. Listar Productos
            3. Buscar/Actualizar Producto
            4. Eliminar Producto
            5. Crear Pedido
            6. Listar Pedidos
            7. Salir
        """);
    }

    static void agregarProducto() {
        System.out.print("Nombre del producto: ");
        String nombre = sc.nextLine();
        double precio = leerDouble("Precio: ");
        int stock = leerEntero("Cantidad en stock: ");
        productos.add(new Producto(nombre, precio, stock));
        System.out.println("Producto agregado con exito.");
    }

    static void listarProductos() {
        System.out.println("\n--- PRODUCTOS ---");
        for (Producto p : productos) {
            System.out.println(p);
        }
    }

    static void buscarActualizarProducto() {
        int id = leerEntero("Ingrese ID del producto: ");
        Producto prod = buscarProductoPorId(id);
        if (prod != null) {
            System.out.println(prod);
            System.out.print("¿Actualizar precio? (s/n): ");
            if (sc.nextLine().equalsIgnoreCase("s")) {
                double nuevoPrecio = leerDouble("Nuevo precio: ");
                prod.setPrecio(nuevoPrecio);
            }
            System.out.print("¿Actualizar stock? (s/n): ");
            if (sc.nextLine().equalsIgnoreCase("s")) {
                int nuevoStock = leerEntero("Nuevo stock: ");
                prod.setStock(nuevoStock);
            }
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    static void eliminarProducto() {
        int id = leerEntero("ID del producto a eliminar: ");
        Producto p = buscarProductoPorId(id);
        if (p != null) {
            productos.remove(p);
            System.out.println("Producto eliminado.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    static void crearPedido() {
        Pedido pedido = new Pedido();
        int n = leerEntero("¿Cuantos productos desea agregar al pedido? ");
        for (int i = 0; i < n; i++) {
            int id = leerEntero("ID del producto: ");
            Producto p = buscarProductoPorId(id);
            if (p == null) {
                System.out.println("Producto no encontrado.");
                i--;
                continue;
            }
            int cantidad = leerEntero("Cantidad: ");
            try {
                if (cantidad > p.getStock()) {
                    throw new StockInsuficienteException("Stock insuficiente para " + p.getNombre());
                }
                p.setStock(p.getStock() - cantidad);
                pedido.agregarLinea(new LineaPedido(p, cantidad));
            } catch (StockInsuficienteException e) {
                System.out.println(e.getMessage());
                i--;
            }
        }
        pedidos.add(pedido);
        System.out.println("Pedido creado:\n" + pedido);
    }

    static void listarPedidos() {
        System.out.println("\n--- PEDIDOS ---");
        for (Pedido p : pedidos) {
            System.out.println(p + "\n");
        }
    }

    static Producto buscarProductoPorId(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero entero.");
            }
        }
    }

    static double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero valido.");
            }
        }
    }
}
