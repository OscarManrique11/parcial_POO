package models;

public class Pokemon {
    private final int id;
    private final String nombre;
    private final String tipo;
    private final int nivel;

    public Pokemon(int id, String nombre, String tipo, int nivel) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.nivel = nivel;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public int getNivel() {
        return nivel;
    }
}