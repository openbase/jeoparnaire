package org.openbase.jeoparnaire.net.server;

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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.MultiException;
import org.openbase.jul.communication.tcp.execution.command.AbstractCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 *
 */
public class ServerService extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerService.class);

    //TODO implement this class like Serverconnection in client side. Move more stuff in clientConnection.
    //TODO lock clientCpnnections.
    public static ServerService instance;

    private final static int SERVER_ID = 9999;
    public final static int SERVER_PORT = 48824;
    public final static long RECONNECTION_TIME = 30000;
    public final static int REFRESH_TIME = 60000;

    private HashMap<Integer, ClientConnection> clientConnections;
    private ServerSocket serverSocket;
    private boolean online;
    private final int serverID = SERVER_ID;

    /**
     *
     */
    public ServerService() {
        this.online = false;
        this.clientConnections = new HashMap<Integer, ClientConnection>();
    }

    @Override
    public void run() {
        while (true) {
            Socket clientSocket = null;
            ClientConnection clientConnection = null;

            try {
                serverSocket = new ServerSocket(SERVER_PORT);
                setOnline(true);

            } catch (IOException ex) {
                LOGGER.info("Couldn't create server socket. " + ex.getMessage() + " Try again in " + RECONNECTION_TIME / 1000 + " secunds.");
                setOnline(false);
                try {
                    Thread.sleep(RECONNECTION_TIME);
                } catch (InterruptedException e1) {
                    return;
                }
            }

            while (online) {
                try {
                    LOGGER.debug("Waiting for clients...");
                    clientSocket = serverSocket.accept();
                    try {
                        int clientID = generateClientID();
                        LOGGER.info("Connecting with client[" + clientID + "]...");

                        clientConnection = new ClientConnection(clientID, serverID, "ClientNo." + clientID, clientSocket);
                        clientConnection.autoConnectionhandling();
                        clientConnections.put(clientID, clientConnection);
                    } catch (Exception ex) {
                        clientSocket.close();
                        throw ex;
                    }
                } catch (IOException ex) {
                    LOGGER.info("Couldn't connect to client.", ex);
                } catch (Exception ex) {
                    LOGGER.error("Couldn't connect to client because error during client service initialisation!", ex);
                }
            }
        }
    }

    private void setOnline(boolean online) {
        this.online = online;
        if (online) {
            LOGGER.info("Server Online.");
        } else {
            LOGGER.info("Server Offline.");
        }
    }

    public void sendCommand(AbstractCommand command, int clientID) throws CouldNotPerformException {
        clientConnections.get(clientID).sendCommand(command);
    }

    public void sendCommandToAllClients(AbstractCommand command) throws CouldNotPerformException {
        Iterator<ClientConnection> iterator = clientConnections.values().iterator();
        MultiException.ExceptionStack exceptionStack = null;
        while (iterator.hasNext()) {
            try {
                iterator.next().sendCommand(command);
            } catch (CouldNotPerformException ex) {
                MultiException.push(this, ex, exceptionStack);
            }
        }
        MultiException.checkAndThrow("Could not send command to all client!", exceptionStack);
    }

    private int clientIDCounter = 0;

    private synchronized int generateClientID() {
        return ++clientIDCounter % Integer.MAX_VALUE;
    }

    public void closeClientConnection(int clientID) {
        clientConnections.get(clientID).close();
    }

    public void finishAllConnections() {
        for (ClientConnection clientConnection : clientConnections.values()) {
            clientConnection.finishConnection();
        }
    }

    public ClientConnection getClientConnection(int clientID) {
        return clientConnections.get(clientID);
    }

    protected void removeClientConnection(int clientID) {
        clientConnections.remove(clientID);
    }

    public boolean isOnline() {
        return online;
    }
}
