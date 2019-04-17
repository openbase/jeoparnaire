package org.openbase.jeoparnaire.state;

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

import org.openbase.jeoparnaire.controller.GameController;
import org.openbase.jeoparnaire.tools.Waitable;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import org.openbase.jul.pattern.statemachine.State;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public abstract class AbstractGameState implements State, PropertyChangeListener, Waitable {

    protected final Object waiter = new Object();
    protected GameController gameController = GameController.getInstance();

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    @Override
    public void performWait() {
        gameController.addPropertyChangeListener(this);
        try {
            synchronized (waiter) {
                waiter.wait();
            }
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(WaitForQuestState.class.getName()).log(Level.SEVERE, null, ex);
        }
        gameController.removePropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(GameController.KEY_EVENT) && (int) evt.getNewValue() == KeyEvent.VK_BACK_SPACE) {
            System.out.println("BACK");
        }
    }
}
