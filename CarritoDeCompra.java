import java.util.ArrayList;

public class CarritoDeCompra {
    private final ArrayList<Producto> carrito;
    private double total;

    public CarritoDeCompra() {
        this.carrito = new ArrayList<>();
        this.total = 0;
    }

    public void agregarProducto(Producto producto) {
        if (producto.inventario > 0) {
            carrito.add(producto);
            total += producto.precio;
            producto.inventario--;
            System.out.println("Se ha agregado " + producto.descripcion + " al carrito.");
        } else {
            System.out.println("No hay inventario disponible para " + producto.descripcion);
        }
    }

    public boolean estaVacio() {
        return carrito.isEmpty();
    }

    public double getTotal() {
        return total;
    }

    public ArrayList<Producto> getCarrito() {
        return carrito;
    }

    public void vaciarCarrito() {
        carrito.clear();
        total = 0;
    }
}
