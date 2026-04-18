package com.proyecto.clases;

import com.proyecto.App;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

public class Item {
	public static String tipovida = "vida";
	public static String tipopuntos = "puntos";

	private int x;
	private int y;
	private int ancho;
	private int alto;
	private String indiceImagen;
	private String tipo;
	private boolean capturado;

	public Item(int x, int y, int ancho, int alto, String indiceImagen) {
		this(x, y, ancho, alto, indiceImagen, tipopuntos);
	}

	public Item(int x, int y, int ancho, int alto, String indiceImagen, String tipo) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.indiceImagen = indiceImagen;
		this.tipo = tipo;
	}
	
	public void pintar(GraphicsContext graficos) {
		if (!capturado) {
			graficos.drawImage(App.imagenes.get(indiceImagen), this.x, this.y, this.ancho, this.alto);
		}
	}
	
	public Rectangle obtenerRectangulo() {
		return new Rectangle(this.x, this.y, this.ancho, this.alto);
	}

	public boolean isCapturado() {
		return capturado;
	}

	public void setCapturado(boolean capturado) {
		this.capturado = capturado;
	}

	public String getTipo() {
		return tipo;
	}
}


