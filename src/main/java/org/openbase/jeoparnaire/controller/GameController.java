package org.openbase.jeoparnaire.controller;

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

import org.openbase.jeoparnaire.config.GameConfigParser;
import org.openbase.jeoparnaire.data.GameData;
import org.openbase.jeoparnaire.data.Quest;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import org.openbase.jeoparnaire.state.GameStartState;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.pattern.statemachine.StateRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class GameController implements PropertyChangeListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    public static final String DATA_UPDATE = "DataUpdate";
    public static final String QUEST_UPDATE = "QuestUpdate";
    public static final String KEY_EVENT = "KeyEvent";
    public static final String SHOW_IMAGE = "ShowImage";

    private static GameController instance;
    private StateRunner stateRunner;

    private GameData data;
    private final PropertyChangeSupport changes;

    public static synchronized GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }
    private Quest activeQuest;

    private GameController() {
        this.changes = new PropertyChangeSupport(this);

    }

    public void initGame() {
        LOGGER.info("Init game");
        stateRunner = new StateRunner();
        data = GameConfigParser.parseConfigFile();
        changes.firePropertyChange(DATA_UPDATE, null, data);
    }

    public void startGame() throws CouldNotPerformException {
        try {
            LOGGER.info("Start game");
            stateRunner.addPropertyChangeListener(this);
            stateRunner.init(GameStartState.class);
            stateRunner.run();
            stateRunner.removePropertyChangeListener(this);
        } catch (CouldNotPerformException ex) {
            throw new CouldNotPerformException("Could not start game!", ex);
        }
    }

    public PropertyChangeSupport getChanges() {
        return changes;
    }

    public GameData getData() {
        return data;
    }

    public StateRunner getStateRunner() {
        return stateRunner;
    }

    public void setActiveQuest(Quest selectedQuest) {
        this.activeQuest = selectedQuest;
        selectedQuest.setActive();
        changes.firePropertyChange(QUEST_UPDATE, null, activeQuest);
    }

    public Quest getActiveQuest() {
        return activeQuest;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changes.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changes.removePropertyChangeListener(listener);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        changes.firePropertyChange(evt);
    }
}
