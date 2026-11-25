package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Equipo {
    private static final int MAX_POKEMONS = 6;
    private final List<Pokemon> pokemons = new ArrayList<>();

    public boolean agregarPokemon(Pokemon pokemon) {
        if (pokemon == null || pokemons.size() >= MAX_POKEMONS) {
            return false;
        }

        for (Pokemon actual : pokemons) {
            if (actual.getId() == pokemon.getId()) {
                return false;
            }
        }

        pokemons.add(pokemon);
        return true;
    }

    public boolean quitarPokemon(int idPokemon) {
        return pokemons.removeIf(pokemon -> pokemon.getId() == idPokemon);
    }

    public List<Pokemon> getPokemons() {
        return Collections.unmodifiableList(pokemons);
    }
}