import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VentaRegistrar {
    private final String archivoVentas;
    private int idCompra;

    public VentaRegistrar(String archivoVentas) {
        this.archivoVentas = archivoVentas;
        this.idCompra = 1;
    }

    public void registrarVenta(List<Producto> carrito, double total) {
        try (FileWriter writer = new FileWriter(archivoVentas, true)) {
            SimpleDateFormat formatoFechaHora = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String fechaHora = formatoFechaHora.format(new Date());

            writer.write("\nID Compra: " + idCompra++ + "\n");
            writer.write("Fecha y hora: " + fechaHora + "\n");
            writer.write("Productos comprados:\n");
            for (Producto p : carrito) {
                writer.write(p.descripcion + "\t" + p.precio + "\n");
            }
            writer.write("Total: " + total + "\n");
        } catch (IOException e) {
            System.out.println("Error al registrar la venta.");
        }
    }

    public void mostrarVentas() {
        System.out.println("\n--- Ventas del Día ---");
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoVentas))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de ventas.");
        }
    }
}
