import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		MaquinaExpendedora maquina = new MaquinaExpendedora();
		Scanner scanner = new Scanner(System.in);
		int opcion;

		do {
			System.out.println("\n--- Máquina Expendedora ---");
			System.out.println("1. Mostrar productos disponibles");
			System.out.println("2. Agregar productos al inventario");
			System.out.println("3. Agregar productos al carrito");
			System.out.println("4. Finalizar compra");
			System.out.println("5. Mostrar ventas del día");
			System.out.println("6. Salir");
			System.out.print("Seleccione una opción: ");
			opcion = scanner.nextInt();

			switch (opcion) {
				case 1 -> maquina.mostrarProductos(); // Mostrar productos disponibles
				case 2 -> {
					System.out.print("Ingrese el código del producto: ");
					String codigo = scanner.next();
					System.out.print("Ingrese la cantidad a agregar: ");
					int cantidad = scanner.nextInt();
					maquina.agregarProducto(codigo, cantidad); // Agregar al inventario
				}
				case 3 -> {
					System.out.print("Ingrese el código del producto a agregar al carrito: ");
					String codigoProducto = scanner.next();
					maquina.agregarProductoAlCarrito(codigoProducto); // Agregar al carrito
				}
				case 4 -> maquina.finalizarCompra(); // Finalizar la compra
				case 5 -> maquina.mostrarVentasDelDia(); // Mostrar ventas del día
				case 6 -> System.out.println("Saliendo...");
				default -> System.out.println("Opción no válida.");
			}
		} while (opcion != 6);

		scanner.close();
	}
}
