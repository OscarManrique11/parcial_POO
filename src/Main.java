import operations.Operation;
import ui.MenuPokemon;
import javax.swing.SwingUtilities;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void mainConsola(String[] args) {
        Scanner leer = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero(leer, "Elige una opción: ");

            try {
                switch (opcion) {
                    case 1: {
                        String nombre = leerTexto(leer, "Nombre del entrenador: ");
                        String region = leerTexto(leer, "Región: ");
                        Operation.crearEntrenador(nombre, region);
                        break;
                    }
                    case 2:
                        Operation.listarEntrenadores();
                        break;
                    case 3: {
                        String nombre = leerTexto(leer, "Nombre del Pokémon: ");
                        String tipo = leerTexto(leer, "Tipo: ");
                        int nivel = leerEnteroEnRango(leer, "Nivel (1-100): ", 1, 100);
                        Operation.crearPokemon(nombre, tipo, nivel);
                        break;
                    }
                    case 4:
                        Operation.listarPokemons();
                        break;
                    case 5: {
                        int idEntrenador =
                                leerEnteroPositivo(leer, "ID del entrenador: ");
                        int idPokemon =
                                leerEnteroPositivo(leer, "ID del Pokémon: ");
                        Operation.asignarPokemon(idEntrenador, idPokemon);
                        break;
                    }
                    case 6:
                        int idEntrenador =
                                leerEnteroPositivo(leer, "ID del entrenador: ");
                        Operation.mostrarEquipo(idEntrenador);
                        break;
                    case 0:
                        System.out.println("Hasta luego.");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (IOException | IllegalArgumentException e) {
                System.out.println("No se pudo completar la operación: "
                        + e.getMessage());
            }
        } while (opcion != 0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuPokemon menu = new MenuPokemon();
            menu.setVisible(true);
        });
    }

    private static void mostrarMenu() {
        System.out.println("\n===== Menú Pokémon =====");
        System.out.println("1. Crear entrenador");
        System.out.println("2. Listar entrenadores");
        System.out.println("3. Crear Pokémon");
        System.out.println("4. Listar Pokémon");
        System.out.println("5. Agregar Pokémon a un entrenador");
        System.out.println("6. Ver el equipo de un entrenador");
        System.out.println("0. Salir");
    }

    private static String leerTexto(Scanner leer, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = leer.nextLine().trim();

            if (texto.isEmpty() || texto.contains(",")) {
                System.out.println(
                        "El texto no puede estar vacío ni contener comas."
                );
            } else {
                return texto;
            }
        }
    }

    private static int leerEntero(Scanner leer, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(leer.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Escribe un número entero.");
            }
        }
    }

    private static int leerEnteroPositivo(Scanner leer, String mensaje) {
        while (true) {
            int numero = leerEntero(leer, mensaje);
            if (numero > 0) {
                return numero;
            }
            System.out.println("El número debe ser mayor que cero.");
        }
    }

    private static int leerEnteroEnRango(
            Scanner leer, String mensaje, int minimo, int maximo) {
        while (true) {
            int numero = leerEntero(leer, mensaje);
            if (numero >= minimo && numero <= maximo) {
                return numero;
            }
            System.out.println("El valor debe estar entre "
                    + minimo + " y " + maximo + ".");
        }
    }
}