package org.openbase.jeoparnaire.config;

/*-
 * #%L
 * Jeoparnaire
 * %%
 * Copyright (C) 2011 - 2020 openbase.org
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
import nu.xom.Document;
import nu.xom.ParsingException;
import org.openbase.jeoparnaire.jp.JPGameConfig;
import org.openbase.jps.core.JPService;
import org.openbase.jps.exception.JPServiceException;
import org.openbase.jul.processing.xml.processing.XMLProcessor;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class GameConfigParser {

    public static GameData parseConfigFile() {
        
        final Document configDocument;
        File configFile;
        try {
            configFile = JPService.getProperty(JPGameConfig.class).getValue();
        } catch (JPServiceException ex) {
            throw new RuntimeException("Could not load config file!", ex);
        }

        try {
            configDocument = XMLProcessor.createDocumentFromFile(configFile);
        } catch (ParsingException | IOException ex) {
            throw new RuntimeException("Could not parse " + configFile.getAbsolutePath(), ex);
        }

        GameData data = new GameData();
        data.parse(configDocument);
        return data;
    }

}
