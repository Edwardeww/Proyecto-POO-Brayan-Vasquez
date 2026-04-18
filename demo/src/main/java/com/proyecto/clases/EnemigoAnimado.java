package com.proyecto.clases;

import java.util.ArrayList;
import java.util.HashMap;

import com.proyecto.App;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

public class EnemigoAnimado {
    private int dibujo_ancho = 46;
    private int dibujo_alto = 58;

    private int x;
    private int y;
    private int velocidad;
    private String indiceImagen;
    private HashMap<String, Animacion> animaciones;
    private String animacionActual;

    private int xImagen;
    private int yImagen;
    private int anchoImagen;
    private int altoImagen;
    private int frameAncho;
    private int frameAlto;
    private int filasAnimacion;

    private boolean activo;
    private double rangoDeteccion;
    private int vida;

    public EnemigoAnimado(int x, int y, int velocidad, String indiceImagen, String animacionActual, double rangoDeteccion) {
        this(x, y, velocidad, indiceImagen, animacionActual, rangoDeteccion, 1);
    }

    public EnemigoAnimado(int x, int y, int velocidad, String indiceImagen, String animacionActual,
            double rangoDeteccion, int vida) {
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        this.indiceImagen = indiceImagen;
        this.animacionActual = animacionActual;
        this.rangoDeteccion = rangoDeteccion;
        this.vida = vida;
        this.activo = true;
        configurarSpritesheet();

        inicializarAnimaciones();

        this.xImagen = 0;
        this.yImagen = 0;
        this.anchoImagen = frameAncho;
        this.altoImagen = frameAlto;
    }

    private void configurarSpritesheet() {
        if ("goomba".equals(this.indiceImagen)) {
            this.frameAncho = 58;
            this.frameAlto = 74;
            this.filasAnimacion = 3;
            return;
        }

        double anchoImagenCompleta = App.imagenes.get(this.indiceImagen).getWidth();
        double altoImagenCompleta = App.imagenes.get(this.indiceImagen).getHeight();

        this.frameAncho = Math.max(1, (int) Math.round(anchoImagenCompleta / 3.0));
        this.filasAnimacion = 2;
        this.frameAlto = Math.max(1, (int) Math.round(altoImagenCompleta / this.filasAnimacion));
    }

    public void actualizarAnimacion(double t) {
        Rectangle coordenadasActuales = this.animaciones.get(animacionActual).calcularFrame(t);
        this.xImagen = (int) coordenadasActuales.getX();
        this.yImagen = (int) coordenadasActuales.getY();
        this.anchoImagen = (int) coordenadasActuales.getWidth();
        this.altoImagen = (int) coordenadasActuales.getHeight();
    }

    public void moverHaciaJugador(int jugadorX, int jugadorY, ArrayList<Rectangle> zonasColision) {
        if (!activo) {
            return;
        }

        double dx = jugadorX - this.x;
        double dy = jugadorY - this.y;
        double distancia = Math.sqrt(dx * dx + dy * dy);

        if (distancia > rangoDeteccion) {
            animacionActual = "quieto";
            return;
        }

        int nuevoX = this.x;
        int nuevoY = this.y;

        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0) {
                nuevoX += velocidad;
                animacionActual = "derecha";
            } else {
                nuevoX -= velocidad;
                animacionActual = "izquierda";
            }
        } else {
            if (dy > 0) {
                nuevoY += velocidad;
                animacionActual = "abajo";
            } else {
                nuevoY -= velocidad;
                animacionActual = "arriba";
            }
        }

        Rectangle rectX = new Rectangle(nuevoX + 18, this.y + 44, 16, 10);
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

        Rectangle rectY = new Rectangle(this.x + 18, nuevoY + 44, 16, 10);
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
        if (!activo) {
            return;
        }

        graficos.drawImage(
            App.imagenes.get(this.indiceImagen),
            this.xImagen, this.yImagen,
            this.anchoImagen, this.altoImagen,
            this.x, this.y,
            dibujo_ancho, dibujo_alto
        );
    }

    public Rectangle obtenerRectangulo() {
        return new Rectangle(this.x + 6, this.y + 28, 34, 28);
    }

    public boolean recibirDisparo() {
        if (!activo) {
            return false;
        }

        vida--;
        if (vida <= 0) {
            activo = false;
            return true;
        }

        return false;
    }

    public void inicializarAnimaciones() {
        animaciones = new HashMap<>();

        Rectangle quieto[] = {
            new Rectangle(0, 0, frameAncho, frameAlto)
        };
        animaciones.put("quieto", new Animacion("quieto", quieto, 0.2));

        Rectangle abajo[] = {
            new Rectangle(0, 0, frameAncho, frameAlto),
            new Rectangle(frameAncho, 0, frameAncho, frameAlto),
            new Rectangle(frameAncho * 2, 0, frameAncho, frameAlto)
        };
        animaciones.put("abajo", new Animacion("abajo", abajo, 0.15));

        int filaArriba = 1;
        int filaLateral = filasAnimacion == 3 ? 2 : 1;

        Rectangle izquierda[] = {
            new Rectangle(0, frameAlto * filaLateral, frameAncho, frameAlto),
            new Rectangle(frameAncho, frameAlto * filaLateral, frameAncho, frameAlto),
            new Rectangle(frameAncho * 2, frameAlto * filaLateral, frameAncho, frameAlto)
        };
        animaciones.put("izquierda", new Animacion("izquierda", izquierda, 0.15));

        Rectangle arriba[] = {
            new Rectangle(0, frameAlto * filaArriba, frameAncho, frameAlto),
            new Rectangle(frameAncho, frameAlto * filaArriba, frameAncho, frameAlto),
            new Rectangle(frameAncho * 2, frameAlto * filaArriba, frameAncho, frameAlto)
        };
        animaciones.put("arriba", new Animacion("arriba", arriba, 0.15));

        Rectangle derecha[] = {
            new Rectangle(0, frameAlto * filaLateral, frameAncho, frameAlto),
            new Rectangle(frameAncho, frameAlto * filaLateral, frameAncho, frameAlto),
            new Rectangle(frameAncho * 2, frameAlto * filaLateral, frameAncho, frameAlto)
        };
        animaciones.put("derecha", new Animacion("derecha", derecha, 0.15));
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
