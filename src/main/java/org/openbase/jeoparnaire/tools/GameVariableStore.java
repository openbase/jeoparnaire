package org.openbase.jeoparnaire.tools;

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

import org.openbase.jeoparnaire.jp.JPGameResources;
import org.openbase.jps.core.JPService;
import org.openbase.jps.exception.JPNotAvailableException;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.FatalImplementationErrorException;
import org.openbase.jul.processing.VariableProcessor;
import org.openbase.jul.processing.VariableStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class GameVariableStore {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameVariableStore.class);
    
    private static GameVariableStore instance;
    private final VariableStore variableStore;

    private GameVariableStore() {
        this.variableStore = new VariableStore("JeoparnaireVariableStore");
    }

    private void init() {
        try {
            variableStore.store("RESOURCES", JPService.getProperty(JPGameResources.class).getValue().getAbsolutePath());
            LOGGER.info("Resource directory is set to "+JPService.getProperty(JPGameResources.class).getValue().getAbsolutePath());
        } catch (JPNotAvailableException ex) {
            new FatalImplementationErrorException("Could not init variable store!", this, ex);
        }
    }

    public static String resolveVariables(final String context) throws CouldNotPerformException {
        return VariableProcessor.resolveVariables(context, true, getInstance().variableStore);
    }

    public static synchronized GameVariableStore getInstance() {
        if (instance == null) {
            instance = new GameVariableStore();
            instance.init();
        }
        return instance;
    }
}
