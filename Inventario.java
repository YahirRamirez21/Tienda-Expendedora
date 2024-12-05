import java.util.ArrayList;
import java.util.Optional;

public class Inventario {
    private final ArrayList<Producto> productos;

    public Inventario(ArrayList<Producto> productosIniciales) {
        this.productos = productosIniciales;
    }

    public void mostrarProductos() {
        System.out.println("\nProductos disponibles:");
        System.out.println("Código\tDescripción\tPrecio\tTipo\tInventario");
        for (Producto p : productos) {
            System.out.println(p.codigo + "\t" + p.descripcion + "\t" + p.precio + "\t" + p.tipo() + "\t" + p.inventario);
        }
    }

    public Optional<Producto> buscarProducto(String codigo) {
        return productos.stream().filter(p -> p.codigo.equals(codigo)).findFirst();
    }

    public void agregarStock(String codigo, int cantidad) {
        buscarProducto(codigo).ifPresentOrElse(producto -> {
            int capacidadMaxima = producto instanceof Alimento ? 10 : 20;
            if (producto.inventario + cantidad > capacidadMaxima) {
                System.out.println("El contenedor de " + producto.tipo() + "s está lleno.");
            } else {
                producto.inventario += cantidad;
                System.out.println("Se han agregado " + cantidad + " unidades de " + producto.descripcion + " al inventario.");
            }
        }, () -> System.out.println("Producto no encontrado."));
    }
}
