package org.openbase.jeoparnaire.state;

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

import org.openbase.jeoparnaire.controller.Voting;
import org.openbase.jeoparnaire.net.command.InitVoteCommand;
import org.openbase.jeoparnaire.net.server.ServerService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.printer.ExceptionPrinter;
import org.openbase.jul.exception.printer.LogLevel;
import org.openbase.jul.pattern.statemachine.State;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class InitVoteState extends AbstractGameState {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(InitVoteState.class);
    
	private final Voting voting;
	
	public InitVoteState() {
		this.voting = Voting.getInstance();
	}
	
	
	@Override
	public Class<? extends State> call() throws CouldNotPerformException {
		voting.initVote(gameController.getActiveQuest());
        try {
            ServerService.instance.sendCommandToAllClients(new InitVoteCommand());
        } catch (CouldNotPerformException ex) {
            ExceptionPrinter.printHistory("Could not inform all client about vote initialization", ex, LOGGER, LogLevel.WARN);
        }
		try {
			Thread.sleep(500);
		} catch (InterruptedException ex) {
			Logger.getLogger(InitVoteState.class.getName()).log(Level.SEVERE, null, ex);
		}
		return TaskSolving1State.class;
	}
	
}
