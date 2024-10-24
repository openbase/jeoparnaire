package org.openbase.jeoparnaire.view.server;

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

import org.openbase.jeoparnaire.controller.GameController;
import org.openbase.jeoparnaire.state.TaskSolving1State;
import org.openbase.jeoparnaire.state.TaskSolving2State;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.printer.ExceptionPrinter;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class ExecutionPanel extends javax.swing.JPanel implements PropertyChangeListener {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ExecutionPanel.class);

    /**
     * Creates new form BlackPanel
     */
    public ExecutionPanel() {
        initComponents();
        imageDisplayPanel.setRazio(true);
        GameController.getInstance().addPropertyChangeListener(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imageDisplayPanel = new org.openbase.jeoparnaire.view.server.ImageTransformationPanel();

        setBackground(new java.awt.Color(0, 0, 0));
        setOpaque(false);

        javax.swing.GroupLayout imageDisplayPanelLayout = new javax.swing.GroupLayout(imageDisplayPanel);
        imageDisplayPanel.setLayout(imageDisplayPanelLayout);
        imageDisplayPanelLayout.setHorizontalGroup(
            imageDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        imageDisplayPanelLayout.setVerticalGroup(
            imageDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(imageDisplayPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(imageDisplayPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.openbase.jeoparnaire.view.server.ImageTransformationPanel imageDisplayPanel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(GameController.QUEST_UPDATE) || evt.getNewValue() == TaskSolving1State.class || evt.getNewValue() == TaskSolving2State.class) {
            imageDisplayPanel.setVisible(false);
        } else if (evt.getPropertyName().equals(GameController.SHOW_IMAGE)) {
            try {
                imageDisplayPanel.setImage(evt.getNewValue().toString());
                imageDisplayPanel.setVisible(true);
            } catch (CouldNotPerformException ex) {
                ExceptionPrinter.printHistory("Could not set image[" + evt.getNewValue().toString() + "]", ex, LOGGER);
            }
        }
    }
}
