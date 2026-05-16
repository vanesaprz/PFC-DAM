package com.example.PFC_DAM.model.enums;

public enum Especie {
    PERRO("Perro"),
    GATO("Gato"),
    OTROS("Otros");

    private final String label;

    Especie(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}