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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuPokemon menu = new MenuPokemon();
            menu.setVisible(true);
        });
    }
}
