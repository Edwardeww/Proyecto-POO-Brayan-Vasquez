package com.proyecto.clases;

import javafx.scene.shape.Rectangle;

public class Animacion {
    private Rectangle[] frames;
    private double duracion;

    public Animacion(String nombre, Rectangle[] frames, double duracion) {
        this.frames = frames;
        this.duracion = duracion;
    }

    public Rectangle calcularFrame(double t) {
        int frame = (int) ((t % (frames.length * duracion)) / duracion);
        return frames[frame];
    }
}
