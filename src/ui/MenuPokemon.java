package ui;

import javax.swing.*;
import operations.Operation;
import java.io.IOException;

public class MenuPokemon extends JFrame {    private JPanel contentPane;
    private JButton crearEntrenadorButton;
    private JButton verEquipoButton2;
    private JButton registrarPokémonButton;
    private JButton agregarPokémonAlEquipoButton;
    private JButton salirButton;
    private JLabel tituloLabel;

    public MenuPokemon() {
        setContentPane(contentPane);
        setTitle("Menú Pokémon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        salirButton.addActionListener(e -> dispose());
        crearEntrenadorButton.addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog(this, "Nombre del entrenador:");
            if (nombre == null || nombre.trim().isEmpty()) {
                return;
            }

            String region = JOptionPane.showInputDialog(this, "Región del entrenador:");
            if (region == null || region.trim().isEmpty()) {
                return;
            }

            try {
                Operation.crearEntrenador(nombre.trim(), region.trim());
                JOptionPane.showMessageDialog(this, "Entrenador creado correctamente.");
            } catch (IOException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Error al crear entrenador",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
        registrarPokémonButton.addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog(this, "Nombre del Pokémon:");
            if (nombre == null || nombre.trim().isEmpty()) {
                return;
            }

            String tipo = JOptionPane.showInputDialog(this, "Tipo del Pokémon:");
            if (tipo == null || tipo.trim().isEmpty()) {
                return;
            }

            String nivelTexto = JOptionPane.showInputDialog(this, "Nivel del Pokémon:");
            if (nivelTexto == null) {
                return;
            }

            try {
                int nivel = Integer.parseInt(nivelTexto.trim());
                Operation.crearPokemon(nombre.trim(), tipo.trim(), nivel);
                JOptionPane.showMessageDialog(this, "Pokémon creado correctamente.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El nivel debe ser un número.");
            } catch (IOException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Error al crear Pokémon",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
        agregarPokémonAlEquipoButton.addActionListener(e -> {
            String entrenadorTexto = JOptionPane.showInputDialog(this, "ID del entrenador:");
            if (entrenadorTexto == null) {
                return;
            }

            String pokemonTexto = JOptionPane.showInputDialog(this, "ID del Pokémon:");
            if (pokemonTexto == null) {
                return;
            }

            try {
                int idEntrenador = Integer.parseInt(entrenadorTexto.trim());
                int idPokemon = Integer.parseInt(pokemonTexto.trim());

                boolean asignado = Operation.asignarPokemon(idEntrenador, idPokemon);

                if (asignado) {
                    JOptionPane.showMessageDialog(this, "Pokémon agregado al equipo.");
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "No se pudo agregar. Revisa los IDs y la consola de IntelliJ.",
                            "No se pudo asignar",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Los IDs deben ser números.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Error al guardar",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuPokemon menu = new MenuPokemon();
            menu.setVisible(true);
        });
    }
}
