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

import org.openbase.jeoparnaire.controller.GameController;
import org.openbase.jeoparnaire.data.Quest;
import org.openbase.jeoparnaire.net.server.ServerService;
import java.io.IOException;
import org.openbase.jul.exception.printer.ExceptionPrinter;
import org.openbase.jul.exception.printer.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class AppExecutorService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerService.class);
    
	public static void execute(String executionCommand, Waitable waitable) {		
		
		if(executionCommand == null) {
			return;
		}
		
		try {
			if (executionCommand.isEmpty()) {
				return;
			}

			if (executionCommand.startsWith(Quest.SHOW_IMAGE_COMMAND_VALUE)) {
				GameController.getInstance().getChanges().firePropertyChange(GameController.SHOW_IMAGE, null, executionCommand.replaceFirst(Quest.SHOW_IMAGE_COMMAND_VALUE+" ", ""));
				waitable.performWait();
			} else {
				try {
					Runtime.getRuntime().exec(executionCommand).waitFor();
				} catch (InterruptedException | IOException ex) {
					ExceptionPrinter.printHistory("Could not execute [" + executionCommand + "]!", ex, LOGGER, LogLevel.WARN);
				}
			}
		} catch (Exception ex) {
			ExceptionPrinter.printHistory("State execution failed!", ex, LOGGER);
		}
	}
}
