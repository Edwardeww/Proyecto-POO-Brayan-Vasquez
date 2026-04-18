package com.proyecto;

import com.proyecto.clases.Disparo;
import com.proyecto.clases.EnemigoAnimado;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import com.proyecto.clases.Item;
import com.proyecto.clases.JugadorAnimado;
import com.proyecto.clases.Tile;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class App extends Application {

    private static class Puntaje {
        private String nombre;
        private int puntaje;

        Puntaje(String nombre, int puntaje) {
            this.nombre = nombre;
            this.puntaje = puntaje;
        }
    }

    private int anchoventana = 1000;
    private int altoventana = 500;
    private int tilesize = 60;
    private boolean mostrarcolisiones = false;
    private int vidasiniciales = 3;
    private long invulnerabilidad = 1000;
    private int puntosporenemigo = 100;
    private int puntosporrupia = 100;
    private int cantidadenemigos = 35;
    private int jugadoriniciox = 75;
    private int jugadorinicioy = 1565;
    private int itemsize = 28;

    private Scene escena;
    private Group root;
    private Canvas canvas;
    private GraphicsContext graficos;
    private Stage ventanaPrincipal;
    private Image imginicio;
    private Image imgcomojugar;
    private Image imgfinal;
    private int pantalla = 0;
    private int puntuacion = 0;
    private int vidas = vidasiniciales;
    private long ultimodisparo = 0;
    private long ultimodano = -invulnerabilidad;
    private boolean juegoTerminado = false;
    private boolean gano = false;
    private boolean puntajeGuardado = false;
    private int totalenemigos = 0;
    private int enemigosmuertos = 0;
    private String nombrejugador = "";
    private List<Puntaje> top = new ArrayList<>();
    private Rectangle botonreiniciar = new Rectangle((anchoventana - 210) / 2.0, 425, 210, 40);
    private double camarax = 0;
    private double camaray = 0;
    private int anchomapa;
    private int altomapa;

    private JugadorAnimado jugador;

    public static boolean derecha = false;
    public static boolean izquierda = false;
    public static boolean arriba = false;
    public static boolean abajo = false;

    public static HashMap<String, Image> imagenes;

    private ArrayList<Item> items;
    private ArrayList<Tile> tilesfondo;
    private ArrayList<Disparo> disparos;
    private ArrayList<EnemigoAnimado> enemigos;
    private ArrayList<Tile> tilessuelo;
    private ArrayList<Tile> tilesobjetos;
    private ArrayList<Rectangle> zonascolision;

    private int[][] mapa = {
        {8, 0, 18, 19, 0, 8, 0, 3, 3, 3, 0, 0, 8, 0, 18, 19, 8},
        {8, 0, 13, 0, 0, 0, 0, 3, 20, 3, 0, 17, 0, 0, 13, 0, 8},
        {8, 0, 0, 4, 0, 18, 19, 3, 0, 3, 18, 19, 0, 4, 0, 0, 8},
        {8, 18, 19, 0, 0, 0, 17, 3, 0, 3, 17, 0, 0, 0, 18, 19, 8},
        {8, 0, 8, 0, 17, 0, 0, 3, 3, 3, 0, 0, 17, 0, 8, 0, 8},
        {8, 0, 0, 18, 19, 0, 0, 0, 0, 0, 0, 0, 18, 19, 0, 0, 8},
        {8, 0, 13, 0, 0, 0, 18, 3, 3, 3, 19, 0, 0, 0, 20, 0, 8},
        {8, 17, 0, 0, 8, 0, 0, 3, 0, 3, 0, 0, 8, 0, 0, 17, 8},
        {8, 18, 19, 0, 0, 0, 17, 3, 0, 3, 17, 0, 0, 0, 18, 19, 8},
        {8, 0, 4, 0, 18, 19, 0, 3, 0, 3, 0, 18, 19, 0, 4, 0, 8},
        {8, 0, 0, 0, 0, 17, 0, 3, 3, 3, 0, 17, 0, 0, 0, 0, 8},
        {8, 18, 19, 17, 0, 0, 0, 0, 3, 0, 0, 0, 0, 17, 18, 19, 8},
        {8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 8},
        {8, 3, 4, 0, 8, 4, 0, 4, 0, 8, 4, 0, 8, 4, 0, 3, 8},
        {8, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 8},
        {8, 3, 19, 2, 18, 19, 0, 13, 18, 19, 18, 18, 19, 2, 18, 3, 8},
        {8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 8},
        {8, 17, 17, 17, 17, 17, 17, 17, 17, 17, 3, 18, 19, 17, 17, 0, 8},
        {2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 6, 0, 17, 15, 14, 8},
        {8, 3, 4, 0, 4, 2, 2, 18, 17, 19, 3, 3, 3, 3, 3, 16, 7},
        {2, 3, 0, 0, 0, 0, 2, 18, 19, 0, 3, 20, 0, 19, 18, 0, 7},
        {8, 3, 3, 3, 3, 3, 10, 12, 0, 0, 3, 3, 13, 19, 2, 8, 7},
        {2, 4, 0, 2, 2, 3, 10, 3, 3, 3, 3, 19, 18, 2, 7, 7, 8},
        {8, 0, 0, 0, 0, 3, 10, 0, 18, 19, 3, 0, 18, 19, 0, 0, 8},
        {2, 3, 3, 3, 3, 3, 10, 18, 18, 19, 3, 3, 3, 3, 9, 0, 8},
        {8, 3, 0, 18, 19, 3, 10, 0, 0, 0, 3, 18, 19, 0, 0, 0, 8},
        {8, 3, 6, 11, 11, 11, 17, 19, 18, 19, 18, 0, 0, 7, 2, 2, 8},
        {0, 5, 0, 0, 5, 0, 0, 5, 0, 0, 5, 0, 0, 5, 0, 0, 0}
    };

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage ventana) {
        ventanaPrincipal = ventana;
        inicializarComponentes();

        graficos = canvas.getGraphicsContext2D();
        graficos.setImageSmoothing(false);

        root.getChildren().add(canvas);
        ventana.setScene(escena);
        ventana.setTitle("The Legend of Puma");
        ventana.setResizable(false);
        gestionareventos();
        ventana.show();
        ciclojuego();
    }

    public void inicializarComponentes() {
        jugador = new JugadorAnimado(jugadoriniciox, jugadorinicioy, "zelda", 2, "quieto");

        root = new Group();
        escena = new Scene(root, anchoventana, altoventana);
        canvas = new Canvas(anchoventana, altoventana);
        imagenes = new HashMap<>();

        items = new ArrayList<>();
        disparos = new ArrayList<>();
        enemigos = new ArrayList<>();
        anchomapa = mapa[0].length * tilesize;
        altomapa = mapa.length * tilesize;

        cargarimagenes();
        cargartiles();
        cargarzonascolision();
        cargaritems();
        cargarenemigos();
        totalenemigos = enemigos.size();
        top = leertoppuntajes();
        actualizarcamara();
    }

    public void cargarimagenes() {
        imagenes.put("arbol", new Image(getClass().getResource("arboles.png").toExternalForm()));
        imagenes.put("dpasto", new Image(getClass().getResource("dpasto.png").toExternalForm()));
        imagenes.put("pastoo", new Image(getClass().getResource("pasto3.png").toExternalForm()));
        imagenes.put("casa", new Image(getClass().getResource("casas.png").toExternalForm()));
        imagenes.put("arbol2", new Image(getClass().getResource("arbol2.png").toExternalForm()));
        imagenes.put("casa2", new Image(getClass().getResource("casa2.png").toExternalForm()));
        imagenes.put("base", new Image(getClass().getResource("base.png").toExternalForm()));
        imagenes.put("pozo", new Image(getClass().getResource("pozo.png").toExternalForm()));
        imagenes.put("zelda", new Image(getClass().getResource("zelda.png").toExternalForm()));
        imagenes.put("goomba", new Image(getClass().getResource("goomba.png").toExternalForm()));
        imagenes.put("vida", new Image(getClass().getResource("vidas.png").toExternalForm()));
        imagenes.put("rupia", new Image(getClass().getResource("rupias.png").toExternalForm()));
        imagenes.put("interfaz", new Image(getClass().getResource("interfaz.png").toExternalForm()));
        imagenes.put("enemigo2", new Image(getClass().getResource("enemigo2.png").toExternalForm()));
        imagenes.put("enemigo3", new Image(getClass().getResource("enemigo3.png").toExternalForm()));
        imginicio = new Image(getClass().getResource("inicio.png").toExternalForm());
        imgcomojugar = new Image(getClass().getResource("comojugar.png").toExternalForm());
        imgfinal = new Image(getClass().getResource("final.png").toExternalForm());

    }

    public void pintar() {
        if (pantalla == 0) {
            pintarpantallainicio();
            return;
        }

        if (pantalla == 1) {
            pintarpantallacomojugar();
            return;
        }

        if (juegoTerminado) {
            pintarpantallafinal();
            return;
        }

        graficos.setFill(Color.web("#4f7f2f"));
        graficos.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        graficos.save();
        graficos.translate(-camarax, -camaray);

        for (Tile t : tilesfondo) {
            t.pintar(graficos);
        }

        for (Tile t : tilessuelo) {
            t.pintar(graficos);
        }

        for (Tile t : tilesobjetos) {
            t.pintar(graficos);
        }

        for (Item itemMapa : items) {
            itemMapa.pintar(graficos);
        }

        jugador.pintar(graficos);

        for (Disparo disparo : disparos) {
            disparo.pintar(graficos);
        }

        for (EnemigoAnimado enemigo : enemigos) {
            enemigo.pintar(graficos);
        }

        if (mostrarcolisiones) {
            graficos.setStroke(Color.RED);
            for (Rectangle r : zonascolision) {
                graficos.strokeRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
            }
        }

        graficos.restore();

        pintarhud();
    }

    private void pintarpantallainicio() {
        graficos.setFill(Color.BLACK);
        graficos.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        pintarimagencubriendo(imginicio, 0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void pintarpantallacomojugar() {
        graficos.setFill(Color.BLACK);
        graficos.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        pintarimagencompleta(imgcomojugar, 0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void pintarimagencubriendo(Image imagen, double x, double y, double ancho, double alto) {
        double escala = Math.max(ancho / imagen.getWidth(), alto / imagen.getHeight());
        double anchoOrigen = ancho / escala;
        double altoOrigen = alto / escala;
        double origenX = (imagen.getWidth() - anchoOrigen) / 2.0;
        double origenY = (imagen.getHeight() - altoOrigen) / 2.0;

        graficos.drawImage(imagen, origenX, origenY, anchoOrigen, altoOrigen, x, y, ancho, alto);
    }

    private void pintarimagencompleta(Image imagen, double x, double y, double ancho, double alto) {
        double escala = Math.min(ancho / imagen.getWidth(), alto / imagen.getHeight());
        double anchoDestino = imagen.getWidth() * escala;
        double altoDestino = imagen.getHeight() * escala;
        double destinoX = x + (ancho - anchoDestino) / 2.0;
        double destinoY = y + (alto - altoDestino) / 2.0;

        graficos.drawImage(imagen, destinoX, destinoY, anchoDestino, altoDestino);
    }

    private void pintarhud() {
        Image interfaz = imagenes.get("interfaz");
        double destinoX = -48;
        double destinoY = -49;
        double destinoAncho = 260;
        double destinoAlto = 173;

        graficos.drawImage(interfaz, destinoX, destinoY, destinoAncho, destinoAlto);

        graficos.setFont(Font.font("Georgia", FontWeight.BOLD, 12));
        graficos.setTextAlign(TextAlignment.RIGHT);

        pintarnumerohud(String.valueOf(vidas), destinoX + 205, destinoY + 63);
        pintarnumerohud(String.valueOf(puntuacion), destinoX + 205, destinoY + 81);
        pintarnumerohud(enemigosmuertos + " / " + totalenemigos, destinoX + 211, destinoY + 103);

        graficos.setTextAlign(TextAlignment.LEFT);
    }

    private void pintarnumerohud(String texto, double x, double y) {
        graficos.setFill(Color.color(0, 0, 0, 0.8));
        graficos.fillText(texto, x + 2, y + 2);
        graficos.setFill(Color.web("#fff2b8"));
        graficos.fillText(texto, x, y);
    }

    private void pintarpantallafinal() {
        graficos.setFill(Color.BLACK);
        graficos.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        pintarimagencubriendo(imgfinal, 0, 0, canvas.getWidth(), canvas.getHeight());

        graficos.setFill(Color.color(0, 0, 0, 0.62));
        graficos.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        double panelX = 300;
        double panelY = 125;
        double panelAncho = 400;
        double panelAlto = 245;
        graficos.setFill(Color.color(0.05, 0.04, 0.03, 0.78));
        graficos.fillRoundRect(panelX, panelY, panelAncho, panelAlto, 14, 14);
        graficos.setStroke(Color.web("#e7d29b"));
        graficos.setLineWidth(2);
        graficos.strokeRoundRect(panelX, panelY, panelAncho, panelAlto, 14, 14);

        graficos.setTextAlign(TextAlignment.CENTER);
        graficos.setFill(Color.WHITE);
        graficos.setFont(Font.font("Georgia", FontWeight.BOLD, 32));
        graficos.fillText(gano ? "GANASTE" : "GAME OVER", canvas.getWidth() / 2.0, 170);

        graficos.setFont(Font.font("Georgia", FontWeight.BOLD, 22));
        graficos.fillText("Puntaje: " + puntuacion, canvas.getWidth() / 2.0, 220);
        graficos.fillText("Enemigos derrotados: " + enemigosmuertos + " / " + totalenemigos,
                canvas.getWidth() / 2.0, 255);

        graficos.setFont(Font.font("Georgia", FontWeight.NORMAL, 16));
        graficos.fillText("Mejor puntaje: " + mejorpuntaje(), canvas.getWidth() / 2.0, 292);
        graficos.fillText("Presione R para volver a jugar", canvas.getWidth() / 2.0, 330);
        graficos.fillText("Presione ESC para salir", canvas.getWidth() / 2.0, 355);

        graficos.setTextAlign(TextAlignment.LEFT);
    }

    public void cargartiles() {
        tilesfondo = new ArrayList<>();
        tilessuelo = new ArrayList<>();
        tilesobjetos = new ArrayList<>();
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                tilesfondo.add(new Tile(1, j * tilesize, i * tilesize, 0));
                if (mapa[i][j] == 3) {
                    tilessuelo.add(new Tile(3, j * tilesize, i * tilesize, 0));
                } else if (mapa[i][j] != 0) {
                    tilesobjetos.add(new Tile(mapa[i][j], j * tilesize, i * tilesize, 0));
                }
            }
        }
    }

    public void cargarzonascolision() {
        zonascolision = new ArrayList<>();

        double ancho = anchomapa;
        double alto = altomapa;
        double borde = 8;

        zonascolision.add(new Rectangle(0, 0, ancho, borde));
        zonascolision.add(new Rectangle(0, 0, borde, alto));
        zonascolision.add(new Rectangle(ancho - borde, 0, borde, alto));
        zonascolision.add(new Rectangle(0, alto - borde, ancho, borde));

        for (Tile tile : tilesobjetos) {
            if (tile.esColisionable()) {
                zonascolision.add(tile.obtenerRectanguloColision());
            }
        }

        bloquearcentro();
    }

    private void bloquearcentro() {
        zonascolision.add(new Rectangle(2 * tilesize, 13 * tilesize, 13 * tilesize, 3 * tilesize));
    }

    public void gestionareventos() {
        escena.setOnKeyPressed((KeyEvent evento) -> {
            if (pantalla < 2) {
                if ("ESCAPE".equals(evento.getCode().toString())) {
                    cerrarventana();
                } else {
                    if (pantalla == 1) {
                        nombrejugador = pedirnombreinicio();
                    }
                    pantalla++;
                }
                return;
            }

            if (juegoTerminado) {
                if ("R".equals(evento.getCode().toString())) {
                    reiniciarjuego();
                } else if ("ESCAPE".equals(evento.getCode().toString())) {
                    cerrarventana();
                }
                return;
            }

            switch (evento.getCode().toString()) {
                case "ESCAPE":
                    cerrarventana();
                    break;
                case "RIGHT":
                    derecha = true;
                    break;
                case "LEFT":
                    izquierda = true;
                    break;
                case "UP":
                    arriba = true;
                    break;
                case "DOWN":
                    abajo = true;
                    break;
                case "SPACE":
                    long ahora = System.currentTimeMillis();
                    if (ahora - ultimodisparo >= 200) {
                        int direccionX = 0;
                        int direccionY = 0;
                        int disparoX = jugador.getX() + 20;
                        int disparoY = jugador.getY() + 22;

                        switch (jugador.getUltimaDireccion()) {
                            case "izquierda":
                                direccionX = -1;
                                disparoX = jugador.getX() - 6;
                                disparoY = jugador.getY() + 28;
                                break;
                            case "derecha":
                                direccionX = 1;
                                disparoX = jugador.getX() + 38;
                                disparoY = jugador.getY() + 28;
                                break;
                            case "arriba":
                                direccionY = -1;
                                disparoX = jugador.getX() + 20;
                                disparoY = jugador.getY() + 2;
                                break;
                            case "abajo":
                                direccionY = 1;
                                disparoX = jugador.getX() + 20;
                                disparoY = jugador.getY() + 42;
                                break;
                            default:
                                direccionX = 1;
                                disparoX = jugador.getX() + 38;
                                disparoY = jugador.getY() + 28;
                                break;
                        }

                        disparos.add(new Disparo(disparoX, disparoY, 6, direccionX, direccionY));
                        ultimodisparo = ahora;
                    }
                    break;
                default:
                    break;
            }
        });

        escena.setOnKeyReleased((KeyEvent evento) -> {
            switch (evento.getCode().toString()) {
                case "RIGHT":
                    derecha = false;
                    break;
                case "LEFT":
                    izquierda = false;
                    break;
                case "UP":
                    arriba = false;
                    break;
                case "DOWN":
                    abajo = false;
                    break;
                default:
                    break;
            }
        });

        escena.setOnMousePressed(evento -> {
            if (!juegoTerminado) {
                return;
            }

            if (botonreiniciar.contains(evento.getX(), evento.getY())) {
                reiniciarjuego();
            }
        });
    }

    private void cerrarventana() {
        if (ventanaPrincipal != null) {
            ventanaPrincipal.close();
        }
    }

    public void ciclojuego() {
        long tiempoInicial = System.nanoTime();

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long tiempoActualNanoSegundos) {
                double t = (tiempoActualNanoSegundos - tiempoInicial) / 1000000000.0;
                pintar();
                actualizar(t);
            }
        };

        animationTimer.start();
    }

    public void actualizar(double t) {
        if (pantalla < 2) {
            return;
        }

        if (juegoTerminado) {
            return;
        }

        jugador.actualizarAnimacion(t);
        jugador.mover(zonascolision);
        verificaritems();
        actualizarcamara();

        for (Disparo disparo : disparos) {
            disparo.mover();
            if (disparo.estaFueraDelMapa(anchomapa, altomapa) || disparochoca(disparo)) {
                disparo.setActivo(false);
            }
        }

        for (EnemigoAnimado enemigo : enemigos) {
            enemigo.actualizarAnimacion(t);
            enemigo.moverHaciaJugador(jugador.getX(), jugador.getY(), zonascolision);
        }

        for (Disparo disparo : disparos) {
            for (EnemigoAnimado enemigo : enemigos) {
                if (disparo.isActivo() && enemigo.isActivo() && disparogolpea(disparo, enemigo)) {
                    disparo.setActivo(false);
                    if (enemigo.recibirDisparo()) {
                        enemigosmuertos++;
                        puntuacion += puntosporenemigo;
                    }
                }
            }
        }

        for (EnemigoAnimado enemigo : enemigos) {
            if (enemigo.isActivo()
                    && jugador.obtenerRectangulo().intersects(enemigo.obtenerRectangulo().getBoundsInLocal())) {
                daniojugador();
            }
        }

        disparos.removeIf(d -> !d.isActivo());
        enemigos.removeIf(e -> !e.isActivo());

        if (enemigos.isEmpty()) {
            finalizarjuego(true);
        }
    }

    private void daniojugador() {
        if (esinvulnerable()) {
            return;
        }

        vidas--;
        ultimodano = System.currentTimeMillis();

        if (vidas <= 0) {
            vidas = 0;
            finalizarjuego(false);
        }
    }

    private boolean esinvulnerable() {
        return System.currentTimeMillis() - ultimodano < invulnerabilidad;
    }

    private void finalizarjuego(boolean ganoPartida) {
        if (juegoTerminado) {
            return;
        }

        juegoTerminado = true;
        gano = ganoPartida;
        derecha = false;
        izquierda = false;
        arriba = false;
        abajo = false;
        guardarpuntajefinal();
    }

    private void reiniciarjuego() {
        puntuacion = 0;
        vidas = vidasiniciales;
        enemigosmuertos = 0;
        ultimodisparo = 0;
        ultimodano = -invulnerabilidad;
        juegoTerminado = false;
        gano = false;
        puntajeGuardado = false;

        derecha = false;
        izquierda = false;
        arriba = false;
        abajo = false;

        jugador = new JugadorAnimado(jugadoriniciox, jugadorinicioy, "zelda", 2, "quieto");
        items.clear();
        cargaritems();
        disparos.clear();
        enemigos.clear();
        cargarenemigos();
        totalenemigos = enemigos.size();
        top = leertoppuntajes();
        actualizarcamara();
    }

    private void cargaritems() {
        int[][] rupias = {
            {250, 175}, {610, 175}, {730, 175}, {250, 295}, {850, 295},
            {280, 535}, {575, 455}, {720, 455}, {650, 610}, {790, 610},
            {875, 535}, {190, 665}, {910, 665}, {250, 1025}, {430, 1025},
            {730, 1025}, {910, 1085}, {610, 1145}, {850, 1145}, {310, 1205},
            {490, 1265}, {790, 1325}, {910, 1385}, {550, 1445}, {730, 1505},
            {430, 1565}, {850, 1565}, {370, 115}, {670, 115}, {910, 115},
            {130, 235}, {550, 235}, {790, 235}, {130, 475}, {370, 475},
            {910, 475}, {310, 595}, {490, 595}, {910, 595}, {370, 715},
            {550, 715}, {730, 715}, {910, 775}, {130, 895}, {370, 895},
            {610, 895}, {910, 955}, {130, 1085}, {370, 1145}, {490, 1145},
            {130, 1265}, {310, 1325}, {550, 1325}, {730, 1385}, {130, 1445},
            {310, 1505}, {610, 1505}, {910, 1565}
        };

        int[][] vidasMapa = {
            {350, 330}, {910, 420}, {190, 785}, {670, 1210},
            {550, 1385}, {910, 1505}, {130, 595}, {850, 715},
            {430, 895}, {130, 1145}, {730, 1265}, {310, 1445},
            {610, 235}, {730, 535}, {310, 665}, {910, 895},
            {430, 1085}, {850, 1205}, {130, 1385}, {670, 1505}
        };

        for (int[] posicion : rupias) {
            agregaritem(posicion[0], posicion[1], "rupia", Item.tipopuntos);
        }

        for (int[] posicion : vidasMapa) {
            agregaritem(posicion[0], posicion[1], "vida", Item.tipovida);
        }
    }

    private void agregaritem(int x, int y, String indiceImagen, String tipo) {
        if (puedeponeritem(x, y)) {
            items.add(new Item(x, y, itemsize, itemsize, indiceImagen, tipo));
        }
    }

    private boolean puedeponeritem(int x, int y) {
        Rectangle rectItem = new Rectangle(x, y, itemsize, itemsize);
        Rectangle rectItemConMargen = new Rectangle(x - 12, y - 12, itemsize + 24, itemsize + 24);

        if (enzonacentro(x, y)) {
            return false;
        }

        if (rectItemConMargen.getX() < 0 || rectItemConMargen.getY() < 0
                || rectItemConMargen.getX() + rectItemConMargen.getWidth() >= anchomapa
                || rectItemConMargen.getY() + rectItemConMargen.getHeight() >= altomapa) {
            return false;
        }

        for (Rectangle zona : zonascolision) {
            if (rectItemConMargen.intersects(zona.getBoundsInLocal())) {
                return false;
            }
        }

        for (Item itemMapa : items) {
            if (rectItem.intersects(itemMapa.obtenerRectangulo().getBoundsInLocal())) {
                return false;
            }
        }

        return true;
    }

    private void verificaritems() {
        Rectangle rectJugador = jugador.obtenerRectangulo();

        for (Item itemMapa : items) {
            if (!itemMapa.isCapturado()
                    && rectJugador.intersects(itemMapa.obtenerRectangulo().getBoundsInLocal())) {
                itemMapa.setCapturado(true);
                efectoitem(itemMapa);
            }
        }
    }

    private void efectoitem(Item itemMapa) {
        if (Item.tipovida.equals(itemMapa.getTipo())) {
            vidas++;
        } else if (Item.tipopuntos.equals(itemMapa.getTipo())) {
            puntuacion += puntosporrupia;
        }
    }

    private boolean disparochoca(Disparo disparo) {
        Rectangle rectDisparo = disparo.obtenerRectangulo();

        for (Rectangle zona : zonascolision) {
            if (rectDisparo.intersects(zona.getBoundsInLocal())) {
                return true;
            }
        }

        return false;
    }

    private void cargarenemigos() {
        enemigos.clear();

        int[][] posiciones = {
            {430, 60}, {550, 60}, {490, 150}, {310, 180}, {730, 180},
            {490, 300}, {190, 360}, {790, 360}, {430, 480}, {550, 480},
            {190, 600}, {790, 600}, {310, 660}, {670, 660},
            {490, 780}, {190, 900}, {790, 900}, {610, 1020}, {190, 1080},
            {610, 1080}, {430, 1140}, {610, 1140}, {850, 1140},
            {550, 1200}, {730, 1200}, {910, 1200}, {490, 1260},
            {670, 1260}, {850, 1260}, {430, 1320}, {610, 1320},
            {790, 1320}, {430, 1380}, {670, 1380}, {910, 1380},
            {550, 1440}, {730, 1440}, {490, 1500}, {790, 1500},
            {610, 1560}, {730, 1560}
        };

        ArrayList<int[]> posicionesbuenas = new ArrayList<>();
        for (int[] posicion : posiciones) {
            if (posicionesbuenas.size() >= cantidadenemigos) {
                break;
            }
            agregarposenemigo(posicionesbuenas, posicion[0], posicion[1]);
        }

        int[] filasPreferidas = {24, 22, 20, 18, 16, 14, 12, 10, 8, 6, 4, 2, 25, 23, 21, 19, 17, 15, 13, 11, 9, 7, 5, 3, 1};
        for (int fila : filasPreferidas) {
            for (int columna = 1; columna < mapa[fila].length - 1
                    && posicionesbuenas.size() < cantidadenemigos; columna++) {
                agregarposenemigoEnTile(posicionesbuenas, columna, fila);
            }
        }

        posicionesbuenas.sort((a, b) -> Integer.compare(a[1], b[1]));

        for (int i = 0; i < posicionesbuenas.size() && i < cantidadenemigos; i++) {
            int[] posicion = posicionesbuenas.get(i);
            String indiceImagen;
            int vida;
            double rangoDeteccion;

            if (i < 5) {
                indiceImagen = "enemigo3";
                vida = 3;
                rangoDeteccion = 230;
            } else if (i < 20) {
                indiceImagen = "goomba";
                vida = 2;
                rangoDeteccion = 185;
            } else {
                indiceImagen = "enemigo2";
                vida = 1;
                rangoDeteccion = 165;
            }

            enemigos.add(new EnemigoAnimado(posicion[0], posicion[1], 1, indiceImagen, "quieto", rangoDeteccion, vida));
        }
    }

    private void agregarposenemigoEnTile(ArrayList<int[]> posicionesbuenas, int columna, int fila) {
        int x = columna * tilesize + 7;
        int y = fila * tilesize + 2;

        agregarposenemigo(posicionesbuenas, x, y);
    }

    private void agregarposenemigo(ArrayList<int[]> posicionesbuenas, int x, int y) {
        if (!puedeponerenemigo(posicionesbuenas, x, y)) {
            return;
        }

        posicionesbuenas.add(new int[] {x, y});
    }

    private boolean puedeponerenemigo(ArrayList<int[]> posicionesbuenas, int x, int y) {
        if (enzonacentro(x, y)) {
            return false;
        }

        Rectangle rectEnemigo = new Rectangle(x + 6, y + 28, 34, 28);
        if (rectEnemigo.getX() + rectEnemigo.getWidth() >= anchomapa
                || rectEnemigo.getY() + rectEnemigo.getHeight() >= altomapa) {
            return false;
        }

        for (Rectangle zona : zonascolision) {
            if (rectEnemigo.intersects(zona.getBoundsInLocal())) {
                return false;
            }
        }

        double distanciaJugador = Math.hypot(x - jugadoriniciox, y - jugadorinicioy);
        if (distanciaJugador < 180) {
            return false;
        }

        for (int[] posicion : posicionesbuenas) {
            double distanciaEnemigo = Math.hypot(x - posicion[0], y - posicion[1]);
            if (distanciaEnemigo < 85) {
                return false;
            }
        }

        return true;
    }

    private boolean enzonacentro(int x, int y) {
        int columna = x / tilesize;
        int fila = y / tilesize;
        return fila >= 13 && fila <= 15 && columna >= 2 && columna <= 14;
    }

    private boolean disparogolpea(Disparo disparo, EnemigoAnimado enemigo) {
        Rectangle rectDisparo = disparo.obtenerRectangulo();
        Rectangle rectEnemigo = enemigo.obtenerRectangulo();
        Rectangle rectEnemigoGrande = new Rectangle(
                rectEnemigo.getX() - 12,
                rectEnemigo.getY() - 12,
                rectEnemigo.getWidth() + 24,
                rectEnemigo.getHeight() + 24
        );
        return rectDisparo.intersects(rectEnemigoGrande.getBoundsInLocal());
    }

    private void guardarpuntajefinal() {
        if (puntajeGuardado) {
            return;
        }

        top = leertoppuntajes();
        if (!entraaltop()) {
            puntajeGuardado = true;
            return;
        }

        String nombreJugador = nombrejugador.isEmpty() ? pedirnombretop() : nombrejugador;
        top.add(new Puntaje(nombreJugador, puntuacion));
        ordenartop();

        Path archivo = obtenerrutapuntajes();

        try {
            guardarcsv(archivo);
            puntajeGuardado = true;
        } catch (IOException e) {
            throw new RuntimeException("No se pudo guardar el puntaje final", e);
        }
    }

    private boolean entraaltop() {
        if (top.size() < 10) {
            return true;
        }

        int puntajeMasBajo = top.get(top.size() - 1).puntaje;
        return puntuacion > puntajeMasBajo;
    }

    private String pedirnombreinicio() {
        return pedirnombre("Ingresa tu nombre para guardar tus puntuaciones:");
    }

    private String pedirnombretop() {
        return pedirnombre("Entraste al top 10 con " + puntuacion + " puntos.\nIngresa tu nombre:");
    }

    private String pedirnombre(String mensaje) {
        String nombre = "";
        while (nombre.isEmpty()) {
            nombre = JOptionPane.showInputDialog(null, mensaje, nombrejugador);
            nombre = limpiarnombre(nombre == null ? "" : nombre);

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(null, "El nombre es obligatorio.");
            }
        }
        return nombre;
    }

    private String limpiarnombre(String nombre) {
        return nombre.trim().replace(",", "").replace("\n", " ").replace("\r", " ");
    }

    private List<Puntaje> leertoppuntajes() {
        Path archivo = obtenerrutapuntajes();
        ArrayList<Puntaje> puntajes = new ArrayList<>();

        if (!Files.exists(archivo)) {
            return puntajes;
        }

        try {
            BufferedReader lector = new BufferedReader(new FileReader(archivo.toFile()));
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    String[] datos = linea.split(",");
                    if (datos.length == 2) {
                        puntajes.add(new Puntaje(limpiarnombre(datos[0]), Integer.parseInt(datos[1])));
                    } else {
                        puntajes.add(new Puntaje("Jugador", Integer.parseInt(linea.trim())));
                    }
                }
            }
            lector.close();
        } catch (IOException | NumberFormatException e) {
            return new ArrayList<>();
        }

        puntajes.sort((a, b) -> Integer.compare(b.puntaje, a.puntaje));
        if (puntajes.size() > 10) {
            return new ArrayList<>(puntajes.subList(0, 10));
        }
        return puntajes;
    }

    private void guardarcsv(Path archivo) throws IOException {
        PrintWriter escritor = new PrintWriter(new FileWriter(archivo.toFile()));
        for (Puntaje registro : top) {
            escritor.println(registro.nombre + "," + registro.puntaje);
        }
        escritor.close();
    }

    private void ordenartop() {
        top.sort((a, b) -> Integer.compare(b.puntaje, a.puntaje));

        if (top.size() > 10) {
            top = new ArrayList<>(top.subList(0, 10));
        }
    }

    private Path obtenerrutapuntajes() {
        Path[] rutasPosibles = new Path[] {
            Paths.get("demo", "puntajes.txt"),
            Paths.get("puntajes.txt")
        };

        for (Path ruta : rutasPosibles) {
            Path padre = ruta.toAbsolutePath().getParent();
            if (padre != null && Files.exists(padre)) {
                return ruta;
            }
        }

        return Paths.get("puntajes.txt");
    }

    private String mejorpuntaje() {
        if (top.isEmpty()) {
            return "0";
        }

        Puntaje mejor = top.get(0);
        return mejor.nombre + " - " + mejor.puntaje;
    }

    private void actualizarcamara() {
        camarax = jugador.getX() - canvas.getWidth() / 2.0;
        camaray = jugador.getY() - canvas.getHeight() / 2.0;

        double maxCameraX = Math.max(0, anchomapa - canvas.getWidth());
        double maxCameraY = Math.max(0, altomapa - canvas.getHeight());

        if (camarax < 0) {
            camarax = 0;
        }
        if (camaray < 0) {
            camaray = 0;
        }
        if (camarax > maxCameraX) {
            camarax = maxCameraX;
        }
        if (camaray > maxCameraY) {
            camaray = maxCameraY;
        }
    }
}


