package org.openbase.jeoparnaire.state;

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
import org.openbase.jeoparnaire.controller.Voting;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.pattern.statemachine.State;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class TaskSolving1State extends AbstractGameState implements PropertyChangeListener {

    public TaskSolving1State() {
    }

    @Override
    public Class<? extends State> call() throws CouldNotPerformException {
        performWait();
        if (Voting.getInstance().getWinner().size() < 3) { // Wait some more for votings
            performWait();
        }
        Voting.getInstance().finishVoting();
        return TaskPostExecutionState.class;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(GameController.KEY_EVENT)) {
            if (((KeyEvent) evt.getNewValue()).getKeyCode() == KeyEvent.VK_ENTER) {
                synchronized (waiter) {
                    waiter.notify();
                }
            }
        }
    }
}
