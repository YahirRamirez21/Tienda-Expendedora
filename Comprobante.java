import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Comprobante {
    public static void generarComprobante(List<Producto> carrito, double total) {
        try {
            FileWriter writer = new FileWriter("comprobante.txt");
            writer.write("\nComprobante de compra:\n");
            writer.write("Productos comprados:\n");
            for (Producto p : carrito) {
                writer.write(p.descripcion + "\t" + p.precio + "\n");
            }
            writer.write("Total: " + total + "\n");
            writer.close();
            System.out.println("Se ha generado el comprobante.");
        } catch (IOException e) {
            System.out.println("Error al generar el comprobante.");
        }
    }
}