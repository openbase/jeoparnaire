package org.openbase.jeoparnaire.net.client;

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

import org.openbase.jeoparnaire.view.client.InfoPanel;
import org.openbase.jeoparnaire.view.client.InputPanel;
import org.openbase.jeoparnaire.net.command.FinishVoteCommand;
import org.openbase.jeoparnaire.net.command.InitVoteCommand;
import org.openbase.jeoparnaire.net.command.RegisterPlayerCommand;
import org.openbase.jeoparnaire.net.server.ServerService;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.printer.ExceptionPrinter;
import org.openbase.jul.communication.tcp.datatype.Message;
import org.openbase.jul.communication.tcp.TCPServerConnection;
import org.openbase.jul.communication.tcp.execution.command.AbstractCommand;
import org.openbase.jul.communication.tcp.execution.command.ByeCommand;
import org.openbase.jul.communication.tcp.execution.command.server.UserMessageCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class ServerConnection extends TCPServerConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerConnection.class);

    public final InputPanel gui;
    public String playerName;

    public ServerConnection(String hostName, InputPanel gui) {
        super(hostName, ServerService.SERVER_PORT);
        this.gui = gui;
    }

    public void connectToServer(String playerName) {
        this.playerName = playerName;
        autoConnect();
    }

    @Override
    protected void notifyInputActivity() {
    }

    @Override
    protected void notifyOutputActivity() {
    }

    @Override
    protected void notifyConnectionDelay(long delay) {
        gui.updatePing(Long.toString(delay));
    }

    @Override
    protected void notifyConnectionStateChanged() {
        if (isConnected()) {
            try {
                InfoPanel.printAffirmation(this, "Connected");
                sendCommand(new RegisterPlayerCommand(playerName));
            } catch (CouldNotPerformException ex) {
                ExceptionPrinter.printHistory("Could not finish connection handshake!", ex, LOGGER);
            }

        } else {
            InfoPanel.printWarning(this, "Disconnected");
            gui.enableConnectionSettings(true);
            gui.enableVoting(false);
            gui.updatePing("---");
        }
    }

    @Override
    protected void notifyConnecting() {
        InfoPanel.printInfo(this, "Connecting...");
    }

    @Override
    protected void notifyConnectionClosed() {
        InfoPanel.printError(this, "Connection closed");
        gui.enableConnectionSettings(true);
        gui.enableVoting(false);
        gui.updatePing("---");
        finishConnection();
    }

    @Override
    protected void handleIncomingCommand(AbstractCommand command) {
        LOGGER.debug("got command: " + command.getName());
        if (command instanceof InitVoteCommand) {
            gui.enableVoting(true);
            InfoPanel.printInfo(this, "Vote now!");
        } else if (command instanceof UserMessageCommand) {
            Message message = ((UserMessageCommand) command).getMessage();
            InfoPanel.printWarning(this, message.getEmitterName() + ": " + message.getBestMatch());
        } else if (command instanceof FinishVoteCommand) {
            gui.enableVoting(false);
        } else if (command instanceof ByeCommand) {
            close();
        }

    }

    @Override
    protected void notifyConnectionNoRouteToHost() {
        InfoPanel.printWarning(this, "No route to host found!");
    }

    @Override
    protected void notifyConnectionError(String errorMessage) {
         InfoPanel.printError(this, "Connection error occure: "+errorMessage);
    }
}
