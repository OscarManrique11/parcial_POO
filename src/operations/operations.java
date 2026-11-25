package operations;

import models.Entrenador;
import models.Pokemon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Operation {

    public static Entrenador crearEntrenador(String nombre, String region)
            throws IOException {
        validarTexto(nombre);
        validarTexto(region);

        int id = siguienteId("entrenadores.csv");
        Entrenador entrenador = new Entrenador(id, nombre, region);

        agregarLinea("entrenadores.csv", id + "," + nombre + "," + region);
        return entrenador;
    }

    public static Pokemon crearPokemon(String nombre, String tipo, int nivel)
            throws IOException {
        validarTexto(nombre);
        validarTexto(tipo);

        if (nivel < 1 || nivel > 100) {
            throw new IllegalArgumentException("El nivel debe estar entre 1 y 100.");
        }

        int id = siguienteId("pokemons.csv");
        Pokemon pokemon = new Pokemon(id, nombre, tipo, nivel);

        agregarLinea("pokemons.csv",
                id + "," + nombre + "," + tipo + "," + nivel);
        return pokemon;
    }

    public static void listarEntrenadores() throws IOException {
        List<String> filas = leerLineas("entrenadores.csv");

        if (filas.isEmpty()) {
            System.out.println("Todavía no hay entrenadores.");
            return;
        }

        for (String fila : filas) {
            String[] datos = fila.split(",", -1);
            if (datos.length == 3) {
                System.out.println("ID " + datos[0] + " | " +
                        datos[1] + " | Región: " + datos[2]);
            }
        }
    }

    public static void listarPokemons() throws IOException {
        List<String> filas = leerLineas("pokemons.csv");

        if (filas.isEmpty()) {
            System.out.println("Todavía no hay Pokémon.");
            return;
        }

        for (String fila : filas) {
            String[] datos = fila.split(",", -1);
            if (datos.length == 4) {
                System.out.println("ID " + datos[0] + " | " +
                        datos[1] + " | Tipo: " + datos[2] +
                        " | Nivel: " + datos[3]);
            }
        }
    }

    private static int siguienteId(String archivo) throws IOException {
        int siguiente = 1;

        for (String fila : leerLineas(archivo)) {
            String[] datos = fila.split(",", -1);
            try {
                siguiente = Math.max(
                        siguiente,
                        Integer.parseInt(datos[0].trim()) + 1
                );
            } catch (NumberFormatException e) {
                // Ignora filas cuyo primer campo no sea un ID.
            }
        }

        return siguiente;
    }

    private static void agregarLinea(String archivo, String fila)
            throws IOException {
        try (FileWriter writer = new FileWriter(archivo, true)) {
            writer.write(fila + System.lineSeparator());
        }
    }

    private static List<String> leerLineas(String archivo) throws IOException {
        List<String> filas = new ArrayList<>();
        File file = new File(archivo);

        if (!file.exists()) {
            return filas;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {
            String fila;
            while ((fila = reader.readLine()) != null) {
                if (!fila.trim().isEmpty()) {
                    filas.add(fila);
                }
            }
        }

        return filas;
    }

    private static void validarTexto(String texto) {
        if (texto == null || texto.trim().isEmpty() || texto.contains(",")) {
            throw new IllegalArgumentException(
                    "El valor no puede estar vacío ni contener comas."
            );
        }
    }
}