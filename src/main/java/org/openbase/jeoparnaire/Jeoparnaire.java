package org.openbase.jeoparnaire;

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

import org.openbase.jeoparnaire.controller.GameController;
import org.openbase.jeoparnaire.view.server.GUIMainFrame;
import org.openbase.jps.core.JPService;
import org.openbase.jps.preset.JPDebugMode;
import org.openbase.jul.exception.printer.ExceptionPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class Jeoparnaire {

    private static final Logger LOGGER = LoggerFactory.getLogger(Jeoparnaire.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {

            JPService.setApplicationName(Jeoparnaire.class);
            JPService.parseAndExitOnError(args);

            GameController gameController = GameController.getInstance();
            GUIMainFrame.initGUI(gameController);
            gameController.initGame();

            // Start Clients
            if (JPService.getProperty(JPDebugMode.class).getValue()) {
                JeoparnaireClient.main(null);
            }

            gameController.startGame();
        } catch (final Exception ex) {
            ExceptionPrinter.printHistory("Could not start " + JPService.getApplicationName(), ex, LOGGER);
        }
    }
}
