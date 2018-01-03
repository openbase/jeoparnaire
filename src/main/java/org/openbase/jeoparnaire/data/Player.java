package org.openbase.jeoparnaire.data;

/*-
 * #%L
 * Jeoparnaire
 * %%
 * Copyright (C) 2011 - 2018 openbase.org
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

import org.openbase.jeoparnaire.net.server.ClientConnection;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class Player {
	
	
	private String name;
	private int points, lastPointChange;
	private long lastDelay;
	private int questCounter;
	private ClientConnection clientConnection;

	public Player(String name, ClientConnection clientConnection) {
		this.name = name;
		this.points = 0;
		this.questCounter = 0;
		this.lastDelay = 0;
		this.clientConnection = clientConnection;
	}

	public String getName() {
		return name;
	}

	public int getPoints() {
		return points;
	}

	public int getQuestCounter() {
		return questCounter;
	}
	
	public void newQuest() {
		questCounter++;
	}

	public void addPoints(int points, long deplay) {
		this.points = Math.max(0, this.points + points);
		lastPointChange = points;
		lastDelay = deplay;
	}

	public int getLastPointChange() {
		return lastPointChange;
	}

	public long getLastDelay() {
		return lastDelay;
	}
	
	public boolean isOnline() {
		return clientConnection != null;
	}

//	public void disconnect() {
//		if(clientConnection.isConnected()) {
//		}
//	}
	
	public void setClientConnection(ClientConnection clientConnection) {
		this.clientConnection = clientConnection;
	}

	public ClientConnection getClientConnection() {
		return clientConnection;
	}
}
