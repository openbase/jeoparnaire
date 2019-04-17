package org.openbase.jeoparnaire.net.server;

/*-
 * #%L
 * Jeoparnaire
 * %%
 * Copyright (C) 2011 - 2019 openbase.org
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

import org.openbase.jeoparnaire.controller.PlayerManager;
import org.openbase.jeoparnaire.controller.Voting;
import org.openbase.jeoparnaire.data.Player;
import org.openbase.jeoparnaire.net.command.RegisterPlayerCommand;
import org.openbase.jeoparnaire.net.command.VoteCommand;
import java.net.Socket;
import java.util.logging.Level;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.printer.ExceptionPrinter;
import org.openbase.jul.extension.tcp.datatype.Message;
import org.openbase.jul.extension.tcp.TCPClientConnection;
import org.openbase.jul.extension.tcp.execution.command.AbstractCommand;
import org.openbase.jul.extension.tcp.execution.command.server.UserMessageCommand;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class ClientConnection extends TCPClientConnection {

	private Player player;
	private Voting voting;

	public ClientConnection(int clientID, int serverID, String clientName, Socket socket) {
		super(clientID, serverID, clientName, socket);
		this.voting = Voting.getInstance();
	}

	@Override
	protected void notifyInputActivity() {
	}

	@Override
	protected void notifyOutputActivity() {
	}

	@Override
	protected void notifyConnectionDelay(long delay) {
	}

	@Override
	protected void notifyConnectionStateChanged() {
		if(!isConnected()) {
			if(player != null) {
				player.setClientConnection(null);
			}
		}
	}

	@Override
	protected void notifyConnecting() {
	}

	@Override
	protected void notifyConnectionClosed() {
		if(player != null) {
			player.setClientConnection(null);
		}
	}

	@Override
	protected void handleIncomingCommand(AbstractCommand command) {
		logger.info( "Got command: " + command.getName());
		if (command instanceof RegisterPlayerCommand) {
			RegisterPlayerCommand registerPlayerCommand = (RegisterPlayerCommand) command;
			try {
				player = PlayerManager.getInstance().registerPlayer(registerPlayerCommand.getPlayerName(), this);
			} catch (CouldNotPerformException ex) {
                try {
                    sendCommand(new UserMessageCommand(new Message("Could not connect!" + ex.getMessage(), registerPlayerCommand.getPlayerName(), "Host")));
                } catch (CouldNotPerformException exx) {
                    ExceptionPrinter.printHistory("Error during command build!", exx, logger);
                }
				try {
					Thread.sleep(2000);
				} catch (InterruptedException ex1) {
					java.util.logging.Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex1);
				}
				finishConnection();
			}
		} else if (command instanceof VoteCommand) {
			if(player == null) {
				logger.error("Could not submit vote! Player instance is null!");
				assert false;
				return;
			}
			voting.submitVote(((VoteCommand) command).getVote(), player);
		}
	}

    @Override
    protected void notifyConnectionError(String errorMessage) {
        logger.error("Connectoin error: "+errorMessage);
    }
}
