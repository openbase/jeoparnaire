package org.openbase.jeoparnaire.jp;

/*-
 * #%L
 * Jeoparnaire
 * %%
 * Copyright (C) 2011 - 2022 openbase.org
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
import java.io.File;
import org.openbase.jps.core.JPService;
import org.openbase.jps.exception.JPNotAvailableException;
import org.openbase.jps.exception.JPServiceException;
import org.openbase.jps.preset.AbstractJPFile;
import org.openbase.jps.tools.FileHandler;

/**
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class JPGameConfig extends AbstractJPFile {

    public static final String[] COMMAND_IDENTIFIER = {"-c", "--config"};

    public JPGameConfig() {
        super(COMMAND_IDENTIFIER, FileHandler.ExistenceHandling.Must, FileHandler.AutoMode.Off);
    }

    @Override
    public File getParentDirectory() throws JPServiceException {
        try {
            return JPService.getProperty(JPGameResources.class).getValue();
        } catch (JPServiceException ex) {
            return null;
        }
    }

    @Override
    protected File getPropertyDefaultValue() throws JPNotAvailableException {
        return new File("JeoparnaireGameConfig.xml");
    }

    @Override
    public String getDescription() {
        return "Property can be used to redefine the location of the game config file.";
    }

}
