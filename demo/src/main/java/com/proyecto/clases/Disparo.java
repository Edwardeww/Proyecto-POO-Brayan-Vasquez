package com.proyecto.clases;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Disparo {
    private int x;
    private int y;
    private int velocidad;
    private int direccionX;
    private int direccionY;
    private boolean activo;

    public Disparo(int x, int y, int velocidad, int direccionX, int direccionY) {
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        this.direccionX = direccionX;
        this.direccionY = direccionY;
        this.activo = true;
    }

    public void mover() {
        x += velocidad * direccionX;
        y += velocidad * direccionY;
    }

    public boolean estaFueraDelMapa(int anchoMapa, int altoMapa) {
        return x < -20 || x > anchoMapa + 20 || y < -20 || y > altoMapa + 20;
    }

    public void pintar(GraphicsContext graficos) {
        if (!activo) {
            return;
        }

        graficos.setFill(Color.DEEPSKYBLUE);
        if (direccionY != 0) {
            graficos.fillOval(x, y, 8, 16);
        } else {
            graficos.fillOval(x, y, 16, 8);
        }
    }

    public Rectangle obtenerRectangulo() {
        if (direccionY != 0) {
            return new Rectangle(x, y, 8, 16);
        }
        return new Rectangle(x, y, 16, 8);
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
