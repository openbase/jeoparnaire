package org.openbase.jeoparnaire.view.server;

/*-
 * #%L
 * Jeoparnaire
 * %%
 * Copyright (C) 2011 - 2024 openbase.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.awt.Color;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class GameColors {
	
	public static final Color BLUE = new Color(0,0,150);
	public static final Color BLUE_GREY = new Color(0,0,100);
	public static final Color BLUE_BORDER = new Color(60,60,210);
	public static final Color ORANGE = new Color(230,200,30);
	public static final Color FONT = new Color(230,230,230);
	public static final Color FONT_DIABLED = new Color(80,80,80);
	public static final Color WHITE = Color.WHITE;
	public static final Color BLACK = Color.BLACK;
	public static final Color FONT_TITLE = ORANGE;
	public static final Color GREEN = new Color(30,200,30);
	public static final Color RED = new Color(230,30,0);


	public static String rgbToHex(final Color color) {
		return String.format("#%06X", (0xFFFFFF & color.getRGB()));
	}
}

