package com.example.PFC_DAM.model.enums;

public enum Tamano {
    PEQUENO("Pequeño"),
    MEDIANO("Mediano"),
    GRANDE("Grande");

    private final String label;

    Tamano(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}