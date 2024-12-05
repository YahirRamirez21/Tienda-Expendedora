import java.util.ArrayList;
import java.util.Scanner;

public class MaquinaExpendedora {
    private final Inventario inventario;
    private final CarritoDeCompra carrito;
    private final VentaRegistrar ventaRegistrar;

    public MaquinaExpendedora() {
        this.inventario = new Inventario(inicializarProductos());
        this.carrito = new CarritoDeCompra();
        this.ventaRegistrar = new VentaRegistrar("ventas.txt");
    }

    private ArrayList<Producto> inicializarProductos() {
        ArrayList<Producto> productosIniciales = new ArrayList<>();
        productosIniciales.add(new Alimento("1", "Gansitos", 10.5, 10));
        productosIniciales.add(new Alimento("2", "Ruffles", 8.75, 10));
        productosIniciales.add(new Bebida("3", "Coca-Cola", 15, 20));
        productosIniciales.add(new Bebida("4", "Pepsi", 14, 20));
        return productosIniciales;
    }

    public void mostrarProductos() {
        inventario.mostrarProductos();
    }

    public void agregarProducto(String codigo, int cantidad) {
        inventario.agregarStock(codigo, cantidad);
    }

    public void agregarProductoAlCarrito(String codigoProducto) {
        inventario.buscarProducto(codigoProducto).ifPresentOrElse(carrito::agregarProducto,
                () -> System.out.println("Producto no encontrado."));
    }

    public void finalizarCompra() {
        if (carrito.estaVacio()) {
            System.out.println("No hay productos en el carrito.");
            return;
        }

        System.out.println("\nTotal a pagar: " + carrito.getTotal());
        System.out.print("Ingrese la cantidad con la que paga: ");
        Scanner scanner = new Scanner(System.in);
        double pago = scanner.nextDouble();

        if (pago < carrito.getTotal()) {
            System.out.println("El pago es insuficiente.");
        } else {
            double cambio = pago - carrito.getTotal();
            System.out.println("Cambio a devolver: " + cambio);
            Comprobante.generarComprobante(carrito.getCarrito(), carrito.getTotal());
            ventaRegistrar.registrarVenta(carrito.getCarrito(), carrito.getTotal());
            carrito.vaciarCarrito();
        }
    }

    public void mostrarVentasDelDia() {
        ventaRegistrar.mostrarVentas();

    }
}
