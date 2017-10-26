package org.openbase.jeoparnaire.config;

/*-
 * #%L
 * Jeoparnaire
 * %%
 * Copyright (C) 2011 - 2017 openbase.org
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

import org.openbase.jeoparnaire.data.GameData;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import nu.xom.Document;
import nu.xom.ParsingException;
import org.openbase.jul.extension.xml.processing.XMLProcessor;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class GameConfigParser {
	
	public static final String CONFIG_FILE_URI = "ImageConfig.xml";
	
	public static GameData parseConfigFile() {
		final Document configDocument;
		File configFile;
		try {
			configFile = new File(ClassLoader.getSystemResource(CONFIG_FILE_URI).toURI());
		} catch (URISyntaxException ex) {
			throw new RuntimeException("Could not load "+ CONFIG_FILE_URI, ex);
		}
		
		try {
			configDocument = XMLProcessor.createDocumentFromFile(configFile);
		} catch (ParsingException | IOException ex) {
			throw new RuntimeException("Could not parse "+ CONFIG_FILE_URI, ex);
		}
		
		GameData data = new GameData();
		data.parse(configDocument);
		return data;
	}
	
}
