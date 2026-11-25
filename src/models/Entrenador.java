package models;

public class Entrenador {
    private final int id;
    private final String nombre;
    private final String region;
    private final Equipo equipo;

    public Entrenador(int id, String nombre, String region) {
        this.id = id;
        this.nombre = nombre;
        this.region = region;
        this.equipo = new Equipo();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRegion() {
        return region;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    @Override
    public String toString() {
        return "ID " + id + " - " + nombre + " (" + region + ")";
    }
}
