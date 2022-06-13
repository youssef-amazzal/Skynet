package view;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class Palette {
	private String title;
	private Color PrimaryColor;
	private Color SecondaryColor;
	private Color AccentColor;
	private Color AlphaAccentColor;


	public Palette(String title, Color PrimaryColor, Color SecondaryColor, Color AccentColor) {
		this.title = title;
		this.PrimaryColor = PrimaryColor;
		this.SecondaryColor = SecondaryColor;
		this.AccentColor = AccentColor;
		this.AlphaAccentColor = new Color(AccentColor.getRed(), AccentColor.getGreen(), AccentColor.getBlue(), 0.4);
	}

	public Palette(String title, String PrimaryColor, String SecondaryColor, String AccentColor) {
		this.title = title;
		this.PrimaryColor = Color.valueOf(PrimaryColor);
		this.SecondaryColor = Color.valueOf(SecondaryColor);
		this.AccentColor = Color.valueOf(AccentColor);
		this.AlphaAccentColor = new Color(this.AccentColor.getRed(), this.AccentColor.getGreen(), this.AccentColor.getBlue(), 0.4);
	}

	@Override
	public String toString() {
		return "Palette{" +
				"title='" + title + '\'' +
				", PrimaryColor= " + this.getHexValue(PrimaryColor) +
				", SecondaryColor=" + this.getHexValue(SecondaryColor) +
				", AccentColor=" + this.getHexValue(AccentColor) +
				", AlphaAccentColor=" + this.getHexValue(AlphaAccentColor) +
				'}';
	}

	public String getHexValue(Color color) {
		return String.format("#%02X%02X%02X%02X", (int) (color.getRed() * 255), (int) (color.getGreen() * 255), (int) (color.getBlue() * 255 ), (int) (color.getOpacity() * 255 ));
	}

	public void changeAlphaAccentColorOpacity(double opacity) {
		AlphaAccentColor = new Color(AlphaAccentColor.getRed(), AlphaAccentColor.getGreen(), AlphaAccentColor.getBlue(), opacity);
	}

	public void usePalette(Scene scene) {
		Node root = scene.lookup(".root");
		root.setStyle(""
				+ "PrimaryColor: " + this.getHexValue(PrimaryColor) + " ;"
				+ "SecondaryColor: " + this.getHexValue(SecondaryColor) + " ;"
				+ "AccentColor: " + this.getHexValue(AccentColor) + " ;"
				+ "AlphaAccentColor: " + this.getHexValue(AlphaAccentColor) + " ;"
		);

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Color getPrimaryColor() {
		return PrimaryColor;
	}

	public void setPrimaryColor(Color newPrimaryColor) {
		this.PrimaryColor = newPrimaryColor;
	}

	public Color getSecondaryColor() {
		return SecondaryColor;
	}

	public void setSecondaryColor(Color secondaryColor) {
		SecondaryColor = secondaryColor;
	}

	public Color getAccentColor() {
		return AccentColor;
	}

	public void setAccentColor(Color accentColor) {
		AccentColor = accentColor;
		AlphaAccentColor = new Color(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), AlphaAccentColor.getOpacity());
	}
}
