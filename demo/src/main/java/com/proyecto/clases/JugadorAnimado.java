package com.proyecto.clases;

import java.util.ArrayList;
import java.util.HashMap;

import com.proyecto.App;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

public class JugadorAnimado {
    private int frame_ancho = 120;
    private int frame_alto = 130;
    private int dibujo_ancho = 48;
    private int dibujo_alto = 58;

    private int x;
    private int y;
    private String indiceImagen;
    private int velocidad;
    private HashMap<String, Animacion> animaciones;
    private String animacionActual;
    private String ultimaDireccion;

    private int xImagen;
    private int yImagen;
    private int anchoImagen;
    private int altoImagen;

    public JugadorAnimado(int x, int y, String indiceImagen, int velocidad, String animacionActual) {
        super();
        this.x = x;
        this.y = y;
        this.indiceImagen = indiceImagen;
        this.velocidad = velocidad;
        this.animacionActual = animacionActual;
        this.ultimaDireccion = "derecha";
        inicializarAnimaciones();

        this.xImagen = 0;
        this.yImagen = 0;
        this.anchoImagen = frame_ancho;
        this.altoImagen = frame_alto;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void actualizarAnimacion(double t) {
        Rectangle coordenadasActuales = this.animaciones.get(animacionActual).calcularFrame(t);
        this.xImagen = (int) coordenadasActuales.getX();
        this.yImagen = (int) coordenadasActuales.getY();
        this.anchoImagen = (int) coordenadasActuales.getWidth();
        this.altoImagen = (int) coordenadasActuales.getHeight();
    }

    public void mover(ArrayList<Rectangle> zonasColision) {
    int nuevoX = this.x;
    int nuevoY = this.y;

    if (App.derecha) {
        nuevoX += velocidad;
        animacionActual = "derecha";
        ultimaDireccion = "derecha";
    } else if (App.izquierda) {
        nuevoX -= velocidad;
        animacionActual = "izquierda";
        ultimaDireccion = "izquierda";
    } else if (App.arriba) {
        nuevoY -= velocidad;
        animacionActual = "arriba";
        ultimaDireccion = "arriba";
    } else if (App.abajo) {
        nuevoY += velocidad;
        animacionActual = "abajo";
        ultimaDireccion = "abajo";
    } else {
        animacionActual = "quieto";
    }

    Rectangle rectX = new Rectangle(nuevoX + 20, this.y + 46, 16, 10);
    boolean colisionX = false;

    for (Rectangle r : zonasColision) {
        if (rectX.intersects(r.getBoundsInLocal())) {
            colisionX = true;
            break;
        }
    }

    if (!colisionX) {
        this.x = nuevoX;
    }

    Rectangle rectY = new Rectangle(this.x + 20, nuevoY + 46, 16, 10);
    boolean colisionY = false;

    for (Rectangle r : zonasColision) {
        if (rectY.intersects(r.getBoundsInLocal())) {
            colisionY = true;
            break;
        }
    }

    if (!colisionY) {
        this.y = nuevoY;
    }
}

   public void pintar(GraphicsContext graficos) {
    graficos.drawImage(
        App.imagenes.get(this.indiceImagen),
        this.xImagen, this.yImagen,
        this.anchoImagen, this.altoImagen,
        this.x, this.y,
            dibujo_ancho, dibujo_alto
    );
}

   public Rectangle obtenerRectangulo() {
    return new Rectangle(this.x + 20, this.y + 46, 16, 10);
}

   public String getUltimaDireccion() {
    return ultimaDireccion;
}

   public void inicializarAnimaciones() {
    animaciones = new HashMap<>();

    int ancho = frame_ancho;
    int alto = frame_alto;

    Rectangle quieto[] = {
        new Rectangle(0, 0, ancho, alto)
    };
    animaciones.put("quieto", new Animacion("quieto", quieto, 0.2));

    Rectangle abajo[] = {
        new Rectangle(0, 0, ancho, alto),
        new Rectangle(120, 0, ancho, alto),
        new Rectangle(240, 0, ancho, alto)
    };
    animaciones.put("abajo", new Animacion("abajo", abajo, 0.15));

    Rectangle izquierda[] = {
        new Rectangle(0, 130, ancho, alto),
        new Rectangle(120, 130, ancho, alto),
        new Rectangle(240, 130, ancho, alto)
    };
    animaciones.put("izquierda", new Animacion("izquierda", izquierda, 0.15));

    Rectangle arriba[] = {
        new Rectangle(0, 260, ancho, alto),
        new Rectangle(0, 260, ancho, alto),
        new Rectangle(0, 260, ancho, alto)
    };
    animaciones.put("arriba", new Animacion("arriba", arriba, 0.15));

    Rectangle derecha[] = {
        new Rectangle(0, 390, ancho, alto),
        new Rectangle(120, 390, ancho, alto),
        new Rectangle(240, 390, ancho, alto)
    };
    animaciones.put("derecha", new Animacion("derecha", derecha, 0.15));
}

}
