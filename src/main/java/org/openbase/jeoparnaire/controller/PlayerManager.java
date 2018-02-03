package org.openbase.jeoparnaire.controller;

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

import org.openbase.jeoparnaire.data.Player;
import org.openbase.jeoparnaire.net.server.ClientConnection;
import java.util.*;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.InvalidStateException;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class PlayerManager {
	
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PlayerManager.class);
    
	public static PlayerManager instance;
	public Map<String, Player> playerMap;

	public static synchronized PlayerManager getInstance() {
		if(instance == null) {
			instance = new PlayerManager();
		}
		return instance;
	}
	
	public PlayerManager() {
		this.playerMap = new HashMap<>();
	}
	
	public Player registerPlayer(final String name, final ClientConnection connection) throws CouldNotPerformException {
		LOGGER.info("Register new player "+name);
		Player player = playerMap.get(name);
		if(player == null) {
			player = new Player(name, connection);
			playerMap.put(name, player);
		} else {
			if(player.isOnline()) {
				throw new InvalidStateException("Player name is already online!");
			}
			player.setClientConnection(connection);
		}
		return player;
		
	}
	
	public void addPlayer(Player player) {
		playerMap.put(player.getName(), player);
	}
	
	public Collection<Player> getPlayer() {
		ArrayList<Player> playerList = new ArrayList(playerMap.values());
		Collections.sort(playerList, new Comparator<Player>() {
			

			@Override
			public int compare(Player p1, Player p2) {
				return Integer.compare(p1.getPoints(), p2.getPoints());
			}
		});
		
		Collections.reverse(playerList);
		return Collections.unmodifiableCollection(playerList);
	}
}
