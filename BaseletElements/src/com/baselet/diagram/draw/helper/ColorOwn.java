package com.baselet.diagram.draw.helper;

import java.util.HashMap;

import org.apache.log4j.Logger;

public class ColorOwn {

	private static final Logger log = Logger.getLogger(ColorOwn.class);

	public static enum Transparency {
		FOREGROUND(255),
		FULL_TRANSPARENT(0),
		BACKGROUND(125),
		SELECTION_BACKGROUND(15);
		
		private int alpha;

		private Transparency(int alpha) {
			this.alpha = alpha;
		}
		
		public int getAlpha() {
			return alpha;
		}
	}
	
	public static final ColorOwn RED = new ColorOwn(255, 0, 0, Transparency.FOREGROUND);
	public static final ColorOwn GREEN = new ColorOwn(0, 255, 0, Transparency.FOREGROUND);
	public static final ColorOwn BLUE = new ColorOwn(0, 0, 255, Transparency.FOREGROUND);
	public static final ColorOwn YELLOW = new ColorOwn(255, 255, 0, Transparency.FOREGROUND);
	public static final ColorOwn MAGENTA = new ColorOwn(255, 0, 255, Transparency.FOREGROUND);
	public static final ColorOwn WHITE = new ColorOwn(255, 255, 255, Transparency.FOREGROUND);
	public static final ColorOwn BLACK = new ColorOwn(0, 0, 0, Transparency.FOREGROUND);
	public static final ColorOwn ORANGE = new ColorOwn(255, 165, 0, Transparency.FOREGROUND);
	public static final ColorOwn CYAN = new ColorOwn(0, 255, 255, Transparency.FOREGROUND);
	public static final ColorOwn DARK_GRAY = new ColorOwn(70, 70, 70, Transparency.FOREGROUND);
	public static final ColorOwn GRAY = new ColorOwn(120, 120, 120, Transparency.FOREGROUND);
	public static final ColorOwn LIGHT_GRAY = new ColorOwn(200, 200, 200, Transparency.FOREGROUND);
	public static final ColorOwn PINK = new ColorOwn(255, 175, 175, Transparency.FOREGROUND);

	public static final ColorOwn TRANSPARENT = WHITE.transparency(Transparency.FULL_TRANSPARENT); // color white is important because EPS export doesn't support transparency, therefore background will be white
	public static final ColorOwn SELECTION_FG = BLUE;
	public static final ColorOwn SELECTION_BG = new ColorOwn(0, 0, 255, Transparency.SELECTION_BACKGROUND);
	public static final ColorOwn STICKING_POLYGON = BLUE;
	public static final ColorOwn DEFAULT_FOREGROUND = BLACK;
	public static final ColorOwn DEFAULT_BACKGROUND = TRANSPARENT;

	public static final HashMap<String, ColorOwn> COLOR_MAP = new HashMap<String, ColorOwn>();
	static {
		COLOR_MAP.put("black", ColorOwn.BLACK);
		COLOR_MAP.put("blue", ColorOwn.BLUE);
		COLOR_MAP.put("cyan", ColorOwn.CYAN);
		COLOR_MAP.put("dark_gray", ColorOwn.DARK_GRAY);
		COLOR_MAP.put("gray", ColorOwn.GRAY);
		COLOR_MAP.put("green", ColorOwn.GREEN);
		COLOR_MAP.put("light_gray", ColorOwn.LIGHT_GRAY);
		COLOR_MAP.put("magenta", ColorOwn.MAGENTA);
		COLOR_MAP.put("orange", ColorOwn.ORANGE);
		COLOR_MAP.put("pink", ColorOwn.PINK);
		COLOR_MAP.put("red", ColorOwn.RED);
		COLOR_MAP.put("white", ColorOwn.WHITE);
		COLOR_MAP.put("yellow", ColorOwn.YELLOW);
	}
	
	/* fields should be final to avoid changing parts of existing color object (otherwise unexpected visible changes can happen) */
	private final int red;
	private final int green;
	private final int blue;
	private final int alpha;

	public ColorOwn(int red, int green, int blue, Transparency transparency) {
		this(red, green, blue, transparency.getAlpha());
	}
	
	public ColorOwn(int red, int green, int blue, int alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}

	public ColorOwn(String hex) {
		int i = Integer.decode(hex);
		this.red = (i >> 16) & 0xFF;
		this.green = (i >> 8) & 0xFF;
		this.blue = i & 0xFF;
		this.alpha = Transparency.FOREGROUND.getAlpha();
	}

	public int getRed() {
		return red;
	}

	public int getGreen() {
		return green;
	}

	public int getBlue() {
		return blue;
	}

	public int getAlpha() {
		return alpha;
	}
	
	public ColorOwn transparency(Transparency transparency) {
		return new ColorOwn(getRed(), getGreen(), getBlue(), transparency.getAlpha());
	}
	
	public ColorOwn darken(int factor) {
		return new ColorOwn(Math.max(0, getRed()-factor), Math.max(0, getGreen()-factor), Math.max(0, getBlue()-factor), getAlpha());
	}

	/**
	 * Converts colorString into a Color which is available in the colorMap or if not tries to decode the colorString
	 * 
	 * @param colorString
	 *            String which describes the color
	 * @return Color which is related to the String or null if it is no valid colorString
	 */
	public static ColorOwn forString(String colorString, Transparency transparency) {
		if (colorString == null) return null;
		ColorOwn returnColor = null;
		for (String color : COLOR_MAP.keySet()) {
			if (colorString.equalsIgnoreCase(color)) {
				returnColor = COLOR_MAP.get(color);
				break;
			}
		}
		if (returnColor == null) {
			try {
				returnColor = new ColorOwn(colorString);
			} catch (NumberFormatException e) {
				//only print for debugging because message would be printed, when typing the color
				log.debug("Invalid color:" + colorString);
			}
		}
		if (returnColor != null) returnColor = returnColor.transparency(transparency);
		return returnColor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + alpha;
		result = prime * result + blue;
		result = prime * result + green;
		result = prime * result + red;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		ColorOwn other = (ColorOwn) obj;
		if (alpha != other.alpha) return false;
		if (blue != other.blue) return false;
		if (green != other.green) return false;
		if (red != other.red) return false;
		return true;
	}

	@Override
	public String toString() {
		return "ColorOwn [red=" + red + ", green=" + green + ", blue=" + blue + ", alpha=" + alpha + "]";
	}
	
	

}
