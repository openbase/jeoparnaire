
package org.openbase.jeoparnaire.controller;

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

import org.openbase.jeoparnaire.data.Player;
import org.openbase.jeoparnaire.data.Quest;
import org.openbase.jeoparnaire.net.command.FinishVoteCommand;
import org.openbase.jeoparnaire.net.command.VoteCommand.Vote;
import org.openbase.jeoparnaire.net.server.ServerService;
import java.util.*;
import java.util.logging.Level;
import org.openbase.jeoparnaire.tools.GameSound;
import org.openbase.jul.exception.CouldNotPerformException;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class Voting {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Voting.class);

    private static Voting instance;
    private Quest activeQuest;
    private long timer;
    private final Map<Long, Player> winner, loser;
    private boolean votingActive;

    public Voting() {
        this.winner = new TreeMap<>();
        this.loser = new TreeMap<>();
        this.activeQuest = null;
        this.timer = -1;
        this.votingActive = false;
    }

    public synchronized static Voting getInstance() {
        if (instance == null) {
            instance = new Voting();
        }

        return instance;
    }

    public void initVote(Quest quest) {
        winner.clear();
        loser.clear();
        activeQuest = quest;
        timer = System.currentTimeMillis();
        votingActive = true;
    }

    public void updatePoints() {
        int i = 0;
        int points;
        Player player;
        for (long delay : winner.keySet()) {
            player = winner.get(delay);
            points = (int) (activeQuest.getPoints() - (activeQuest.getPoints() * i * 0.01));
            player.addPoints(Math.max((int) (activeQuest.getPoints() * 0.5), points), delay);
            ++i;
        }

        i = 0;
        for (long delay : loser.keySet()) {
            player = loser.get(delay);
            points = (int)  ((activeQuest.getPoints() - i * 100) * 0.05);
            player.addPoints(-Math.max(0, points), delay);
            ++i;
        }
    }

    public Map<Long, Player> getLoser() {
        return Collections.unmodifiableMap(loser);
    }

    public Map<Long, Player> getWinner() {
        return Collections.unmodifiableMap(winner);
    }

    public synchronized void submitVote(Vote vote, Player player) {
        if (!votingActive) {
            LOGGER.warn("Ignore vote, no active vote process!");
            return;
        }
        if (activeQuest == null) {
            LOGGER.warn("Ignore vote, no active quest!");
            return;
        }

        long delay = System.currentTimeMillis() - timer;
        if (winner.containsKey(delay) || loser.containsKey(delay)) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(Voting.class.getName()).log(Level.SEVERE, null, ex);
            }
            ++delay;
        }

        if (!activeQuest.getAnswers().get(vote.index).isRight()) {
            loser.put(delay, player);
            GameSound.WrongVote.play();
            return;
        }
        winner.put(delay, player);
        GameSound.RightVote.play();
    }

    public Collection<Player> getWinnerAndLoser() {
        ArrayList<Player> playerList = new ArrayList(winner.values());
        playerList.addAll(loser.values());
        Collections.sort(playerList, new Comparator<Player>() {

            @Override
            public int compare(Player p1, Player p2) {
                return Integer.compare(p1.getPoints(), p2.getPoints());
            }
        });

        Collections.reverse(playerList);
        for (int i = 0; i < playerList.size(); i++) {
            Player player = playerList.get(i);
            System.out.println(player.getName()+", "+player.getPoints());
        }
        return Collections.unmodifiableCollection(playerList);
    }

    public void finishVoting() {
        votingActive = false;
        
        try {
            ServerService.instance.sendCommandToAllClients(new FinishVoteCommand());
        } catch (CouldNotPerformException ex) {
           LOGGER.warn("Some issues occured during voting finalization!", ex);
        }
    }
}
