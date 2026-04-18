package com.proyecto.clases;

import com.proyecto.App;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

public class Tile {
    private int x;
    private int y;
    private int altoImagen;
    private int anchoImagen;
    private int xImagen;
    private int yImagen;
    private String indiceImagen;
    private int velocidad;
    private int tipoTile;

    public Tile(int x, int y, int anchoImagen, int altoImagen, int xImagen, int yImagen, String indiceImagen,
            int velocidad) {
        super();
        this.x = x;
        this.y = y;
        this.altoImagen = altoImagen;
        this.anchoImagen = anchoImagen;
        this.xImagen = xImagen;
        this.yImagen = yImagen;
        this.indiceImagen = indiceImagen;
        this.velocidad = velocidad;
        this.tipoTile = 0;
    }

    public Tile(int tipoTile, int x, int y, int velocidad) {
        this.tipoTile = tipoTile;
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;

        switch (tipoTile) {
            case 1:
                this.indiceImagen = "pastoo";
                this.altoImagen = 63;
                this.anchoImagen = 63;
                this.xImagen = 449;
                this.yImagen = 129;
                break;
            case 2:
                this.indiceImagen = "arbol";
                this.altoImagen = 156;
                this.anchoImagen = 382;
                this.xImagen = 287;
                this.yImagen = 26;
                break;
            case 3: // suelo piedra
                this.indiceImagen = "pastoo";
                this.altoImagen = 60;
                this.anchoImagen = 60;
                this.xImagen = 287;
                this.yImagen = 320;
                break;
            case 4:
                this.indiceImagen = "casa";
                this.altoImagen = 140;
                this.anchoImagen = 130;
                this.xImagen = 26;
                this.yImagen = 36;
                break;
            case 5: // muros abajo
                this.indiceImagen = "pastoo";
                this.altoImagen = 134;
                this.anchoImagen = 183;
                this.xImagen = 0;
                this.yImagen = 14;
                break;
            case 6: // farol
                this.indiceImagen = "arbol2";
                this.altoImagen = 48;
                this.anchoImagen = 34;
                this.xImagen = 0;
                this.yImagen = 0;
                break;
            case 7: // árbol/masa lateral
                this.indiceImagen = "arbol2";
                this.altoImagen = 80;
                this.anchoImagen = 320;
                this.xImagen = 223;
                this.yImagen = 0;
                break;
            case 8: // árbol grande
                this.indiceImagen = "pastoo";
                this.altoImagen = 130;
                this.anchoImagen = 84;
                this.xImagen = 128;
                this.yImagen = 382;
                break;
            case 9: // base grande
                this.indiceImagen = "base";
                this.altoImagen = 347;
                this.anchoImagen = 460;
                this.xImagen = 344;
                this.yImagen = 266;
                break;
            case 10: // cerca horizontal
                this.indiceImagen = "arbol2";
                this.altoImagen = 65;
                this.anchoImagen = 16;
                this.xImagen = 210;
                this.yImagen = 0;
                break;
            case 11: // cerca vertical
                this.indiceImagen = "arbol2";
                this.altoImagen = 65;
                this.anchoImagen = 60;
                this.xImagen = 163;
                this.yImagen = 48;
                break;
            case 12: // casa 2
                this.indiceImagen = "casa2";
                this.altoImagen = 112;
                this.anchoImagen = 128;
                this.xImagen = 0;
                this.yImagen = 0;
                break;
            case 13: // pozo
                this.indiceImagen = "pozo";
                this.altoImagen = 48;
                this.anchoImagen = 32;
                this.xImagen = 0;
                this.yImagen = 0;
                break;
            case 14: // cueva entrada
                this.indiceImagen = "pastoo";
                this.altoImagen = 80;
                this.anchoImagen = 65;
                this.xImagen = 447;
                this.yImagen = 354;
                break;
            case 15: // paredes cueva
                this.indiceImagen = "pastoo";
                this.altoImagen = 506;
                this.anchoImagen = 115;
                this.xImagen = 0;
                this.yImagen = 436;
                break;
            case 16: // suelo cueva
                this.indiceImagen = "pastoo";
                this.altoImagen = 60;
                this.anchoImagen = 70;
                this.xImagen = 352;
                this.yImagen = 96;
                break;
            case 17: // roca
                this.indiceImagen = "base";
                this.altoImagen = 60;
                this.anchoImagen = 70;
                this.xImagen = 0;
                this.yImagen = 428;
                break;
            case 18: // decoracion pasto
                this.indiceImagen = "pastoo";
                this.altoImagen = 30;
                this.anchoImagen = 35;
                this.xImagen = 223;
                this.yImagen = 320;
                break;
            case 19: // decoracion pasto
                this.indiceImagen = "pastoo";
                this.altoImagen = 30;
                this.anchoImagen = 35;
                this.xImagen = 253;
                this.yImagen = 320;
                break;
            case 20: // estatua
                this.indiceImagen = "pastoo";
                this.altoImagen = 67;
                this.anchoImagen = 35;
                this.xImagen = 415;
                this.yImagen = 447;
                break;
        }
    }

    public int getTipoTile() {
        return tipoTile;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getAltoImagen() {
        return altoImagen;
    }

    public void setAltoImagen(int altoImagen) {
        this.altoImagen = altoImagen;
    }

    public int getAnchoImagen() {
        return anchoImagen;
    }

    public void setAnchoImagen(int anchoImagen) {
        this.anchoImagen = anchoImagen;
    }

    public int getxImagen() {
        return xImagen;
    }

    public void setxImagen(int xImagen) {
        this.xImagen = xImagen;
    }

    public int getyImagen() {
        return yImagen;
    }

    public void setyImagen(int yImagen) {
        this.yImagen = yImagen;
    }

    public String getIndiceImagen() {
        return indiceImagen;
    }

    public void setIndiceImagen(String indiceImagen) {
        this.indiceImagen = indiceImagen;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public boolean esColisionable() {
    switch (tipoTile) {
        case 2:  // árbol grande
        case 4:  // casa
        case 5:  // muro abajo
        case 6:  // farol
        case 7:  // árboles laterales
        case 8:  // árbol grande individual
        case 10: // cerca horizontal
        case 11: // cerca vertical
        case 12: // casa2
        case 13: // pozo
        case 14: // cueva entrada
        case 15: // pared cueva
        case 17: // roca
        case 20: // estatua
            return true;
        default:
            return false;
    }
}

    public Rectangle obtenerRectanguloColision() {
    switch (tipoTile) {
        case 2: // árbol grande de arboles.png
            return new Rectangle(this.x + 160, this.y + 110, 60, 38);

        case 4: // casa
            return new Rectangle(this.x + 15, this.y + 95, 100, 42);

        case 5: // muro abajo
            return new Rectangle(this.x, this.y + 10, this.anchoImagen, 24);

        case 6: // farol
            return new Rectangle(this.x + 6, this.y + 18, 22, 28);

        case 7: // masa lateral de árboles
            return new Rectangle(this.x + 130, this.y + 35, 60, 38);

        case 8: // árbol grande individual
            return new Rectangle(this.x, this.y, this.anchoImagen, this.altoImagen);

        case 10: // cerca horizontal
            return new Rectangle(this.x + 2, this.y + 12, 12, 45);

        case 11: // cerca vertical
            return new Rectangle(this.x + 5, this.y + 40, 50, 20);

        case 12: // casa2
            return new Rectangle(this.x + 10, this.y + 78, 108, 30);

        case 13: // pozo
            return new Rectangle(this.x + 4, this.y + 8, 24, 32);

        case 14: // entrada cueva
            return new Rectangle(this.x + 8, this.y + 20, 48, 24);

        case 15: // pared cueva
            return new Rectangle(this.x + 10, this.y + 140, 95, 340);

        case 17: // roca
            return new Rectangle(this.x + 6, this.y + 22, 58, 32);

        case 20: // estatua
            return new Rectangle(this.x + 2, this.y + 35, 31, 28);

        default:
            return new Rectangle(this.x, this.y, this.anchoImagen, this.altoImagen);
    }
}

    public void pintar(GraphicsContext graficos) {
        graficos.drawImage(
            App.imagenes.get(this.indiceImagen),
            this.xImagen, this.yImagen,
            this.anchoImagen, this.altoImagen,
            this.x, this.y,
            this.anchoImagen, this.altoImagen
        );
    }
}   
