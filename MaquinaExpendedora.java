import java.io.File;
import java.io.IOException;
import java.util.*;

public class MaquinaExpendedora implements Carrito {
    ArrayList<Producto> productos;
    private ArrayList<Producto> carrito = new ArrayList<>();
    private double total = 0;
    private VentaRegistrar ventaRegistrar;

    public MaquinaExpendedora() {
        this.productos = inicializarProductos();
        this.ventaRegistrar = new VentaRegistrar("ventas.txt");
        inicializarArchivoVentas();
    }

    private ArrayList<Producto> inicializarProductos() {
        ArrayList<Producto> productosIniciales = new ArrayList<>();
        productosIniciales.add(new Alimento("1", "Gansitos", 10.5, 10));
        productosIniciales.add(new Alimento("2", "Ruffles", 8.75, 10));
        productosIniciales.add(new Bebida("3", "Coca-Cola", 15, 20));
        productosIniciales.add(new Bebida("4", "Pepsi", 14, 20));
        return productosIniciales;
    }

    private void inicializarArchivoVentas() {
        try {
            File file = new File("ventas.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error al inicializar el archivo de ventas.");
        }
    }

    public void mostrarProductos() {
        System.out.println("\nProductos disponibles:");
        System.out.println("Código\tDescripción\tPrecio\tTipo\tInventario");
        for (Producto p : productos) {
            System.out.println(p.codigo + "\t" + p.descripcion + "\t" + p.precio + "\t" + p.tipo() + "\t" + p.inventario);
        }
    }

    public void agregarProducto(String codigo, int cantidad) {
        for (Producto p : productos) {
            if (p.codigo.equals(codigo)) {
                int capacidadMaxima = p instanceof Alimento ? 10 : 20;
                if (p.inventario + cantidad > capacidadMaxima) {
                    System.out.println("El contenedor de " + p.tipo() + "s está lleno.");
                    return;
                }
                p.inventario += cantidad;
                System.out.println("Se han agregado " + cantidad + " unidades de " + p.descripcion + " al inventario.");
                return;
            }
        }
        System.out.println("Producto no encontrado.");
    }

    public void agregarProducto(Producto producto) {
        if (producto.inventario > 0) {
            total += producto.precio;
            producto.inventario--;
            carrito.add(producto);
            System.out.println("Se ha agregado " + producto.descripcion + " al carrito.");
        } else {
            System.out.println("No hay inventario disponible para " + producto.descripcion);
        }
    }

    public void finalizarCompra() {
        if (carrito.isEmpty()) {
            System.out.println("No hay productos en el carrito.");
            return;
        }

        System.out.println("\nTotal a pagar: " + total);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la cantidad con la que paga: ");
        double pago = scanner.nextDouble();

        if (pago < total) {
            System.out.println("El pago es insuficiente.");
        } else {
            double cambio = pago - total;
            System.out.println("Cambio a devolver: " + cambio);
            Comprobante.generarComprobante(carrito, total);
            ventaRegistrar.registrarVenta(carrito, total);
            carrito.clear();
            total = 0;
        }
    }

    public void mostrarVentasDelDia() {
        try (Scanner scanner = new Scanner(new File("ventas.txt"))) {
            System.out.println("\nVentas del día:");
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de ventas.");
        }
    }

    public static void main(String[] args) {
        MaquinaExpendedora maquina = new MaquinaExpendedora();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- Menú ---");
            System.out.println("1. Mostrar productos disponibles");
            System.out.println("2. Agregar productos al inventario");
            System.out.println("3. Agregar productos al carrito");
            System.out.println("4. Finalizar compra");
            System.out.println("5. Mostrar ventas del día");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> maquina.mostrarProductos();
                case 2 -> {
                    System.out.print("Ingrese el código del producto: ");
                    String codigo = scanner.next();
                    System.out.print("Ingrese la cantidad a agregar: ");
                    int cantidad = scanner.nextInt();
                    maquina.agregarProducto(codigo, cantidad);
                }
                case 3 -> {
                    System.out.print("Ingrese el código del producto: ");
                    String codigo = scanner.next();
                    Producto producto = maquina.productos.stream()
                            .filter(p -> p.codigo.equals(codigo))
                            .findFirst()
                            .orElse(null);
                    if (producto != null) {
                        maquina.agregarProducto(producto);
                    } else {
                        System.out.println("Producto no encontrado.");
                    }
                }
                case 4 -> maquina.finalizarCompra();
                case 5 -> maquina.mostrarVentasDelDia();
                case 6 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 6);
    }
}
