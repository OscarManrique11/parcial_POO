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
    public static boolean asignarPokemon(int idEntrenador, int idPokemon)
            throws IOException {
        if (buscarFilaPorId("entrenadores.csv", idEntrenador) == null) {
            System.out.println("No existe ese entrenador.");
            return false;
        }

        if (buscarFilaPorId("pokemons.csv", idPokemon) == null) {
            System.out.println("No existe ese Pokémon.");
            return false;
        }

        int cantidadPokemon = 0;

        for (String fila : leerLineas("equipo_pokemon.csv")) {
            String[] datos = fila.split(",", -1);
            if (datos.length != 2) {
                continue;
            }

            int entrenadorActual = parseId(datos[0]);
            int pokemonActual = parseId(datos[1]);

            if (pokemonActual == idPokemon) {
                System.out.println("Ese Pokémon ya está asignado a un entrenador.");
                return false;
            }

            if (entrenadorActual == idEntrenador) {
                cantidadPokemon++;
            }
        }

        if (cantidadPokemon >= 6) {
            System.out.println("El equipo ya tiene seis Pokémon.");
            return false;
        }

        agregarLinea("equipo_pokemon.csv", idEntrenador + "," + idPokemon);
        System.out.println("Pokémon agregado al equipo.");
        return true;
    }

    public static void mostrarEquipo(int idEntrenador) throws IOException {
        String filaEntrenador =
                buscarFilaPorId("entrenadores.csv", idEntrenador);

        if (filaEntrenador == null) {
            System.out.println("No existe ese entrenador.");
            return;
        }

        String[] datosEntrenador = filaEntrenador.split(",", -1);
        Entrenador entrenador = new Entrenador(
                Integer.parseInt(datosEntrenador[0]),
                datosEntrenador[1],
                datosEntrenador[2]
        );

        for (String fila : leerLineas("equipo_pokemon.csv")) {
            String[] asignacion = fila.split(",", -1);
            if (asignacion.length != 2 ||
                    parseId(asignacion[0]) != idEntrenador) {
                continue;
            }

            int idPokemon = parseId(asignacion[1]);
            String filaPokemon = buscarFilaPorId("pokemons.csv", idPokemon);

            if (filaPokemon == null) {
                continue;
            }

            String[] datosPokemon = filaPokemon.split(",", -1);
            if (datosPokemon.length == 4) {
                Pokemon pokemon = new Pokemon(
                        Integer.parseInt(datosPokemon[0]),
                        datosPokemon[1],
                        datosPokemon[2],
                        Integer.parseInt(datosPokemon[3])
                );
                entrenador.getEquipo().agregarPokemon(pokemon);
            }
        }

        System.out.println("Equipo de " + entrenador.getNombre() + ":");
        if (entrenador.getEquipo().getPokemons().isEmpty()) {
            System.out.println("(vacío)");
            return;
        }

        for (Pokemon pokemon : entrenador.getEquipo().getPokemons()) {
            System.out.println("ID " + pokemon.getId() + " | " +
                    pokemon.getNombre() + " | " + pokemon.getTipo() +
                    " | nivel " + pokemon.getNivel());
        }
    }

    private static String buscarFilaPorId(String archivo, int id)
            throws IOException {
        for (String fila : leerLineas(archivo)) {
            String[] datos = fila.split(",", -1);
            if (datos.length > 0 && parseId(datos[0]) == id) {
                return fila;
            }
        }
        return null;
    }

    private static int parseId(String texto) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static List<Entrenador> obtenerEntrenadores() throws IOException {
        List<Entrenador> entrenadores = new ArrayList<>();
        for (String fila : leerLineas("entrenadores.csv")) {
            String[] datos = fila.split(",", -1);
            if (datos.length == 3) {
                try {
                    entrenadores.add(new Entrenador(
                            Integer.parseInt(datos[0].trim()),
                            datos[1],
                            datos[2]
                    ));
                } catch (NumberFormatException ignored) {
                    // Omite filas con un ID inválido.
                }
            }
        }
        return entrenadores;
    }

    public static List<Pokemon> obtenerPokemons() throws IOException {
        List<Pokemon> pokemons = new ArrayList<>();
        for (String fila : leerLineas("pokemons.csv")) {
            String[] datos = fila.split(",", -1);
            if (datos.length == 4) {
                try {
                    pokemons.add(new Pokemon(
                            Integer.parseInt(datos[0].trim()),
                            datos[1],
                            datos[2],
                            Integer.parseInt(datos[3].trim())
                    ));
                } catch (NumberFormatException ignored) {
                    // Omite filas con ID o nivel inválido.
                }
            }
        }
        return pokemons;
    }

    public static List<Pokemon> obtenerEquipo(int idEntrenador)
            throws IOException {
        List<Pokemon> equipo = new ArrayList<>();
        for (String fila : leerLineas("equipo_pokemon.csv")) {
            String[] asignacion = fila.split(",", -1);
            if (asignacion.length != 2 ||
                    parseId(asignacion[0]) != idEntrenador) {
                continue;
            }

            String filaPokemon = buscarFilaPorId(
                    "pokemons.csv", parseId(asignacion[1])
            );
            if (filaPokemon == null) {
                continue;
            }

            String[] datos = filaPokemon.split(",", -1);
            if (datos.length == 4) {
                try {
                    equipo.add(new Pokemon(
                            Integer.parseInt(datos[0].trim()),
                            datos[1],
                            datos[2],
                            Integer.parseInt(datos[3].trim())
                    ));
                } catch (NumberFormatException ignored) {
                    // Omite filas con ID o nivel inválido.
                }
            }
        }
        return equipo;
    }
}
