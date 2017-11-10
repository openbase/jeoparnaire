package org.openbase.jeoparnaire.view.server;

/*-
 * #%L
 * Jeoparnaire
 * %%
 * Copyright (C) 2011 - 2017 openbase.org
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
import org.openbase.jeoparnaire.controller.PlayerManager;
import org.openbase.jeoparnaire.controller.Voting;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.openbase.jeoparnaire.state.PresentGlobalPointsState;
import org.openbase.jeoparnaire.state.PresentQuestPointsState;
import org.openbase.jeoparnaire.view.GameStyle;
import org.openbase.jul.visual.swing.layout.LayoutGenerator;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class PointOverview extends javax.swing.JPanel implements PropertyChangeListener {

	private final PlayerManager playerManager;
	private final Voting voting;

	/**
	 * Creates new form PointOverview
	 */
	public PointOverview() {
		this.initComponents();
		this.playerManager = PlayerManager.getInstance();
		this.voting = Voting.getInstance();
		GameController.getInstance().addPropertyChangeListener(this);
		this.setBackground(GameColors.BLUE_BORDER);
		this.setBorder(javax.swing.BorderFactory.createLineBorder(GameColors.BLACK, 2));
		playerLabel.setForeground(GameColors.FONT_TITLE);
        playerLabel.setFont(playerLabel.getFont().deriveFont(GameStyle.FONT_SIZE));
		titlePanel.setBackground(GameColors.BLUE);
		titlePanel.setBorder(javax.swing.BorderFactory.createLineBorder(GameColors.BLACK, 8));
		this.updateDynamicComponents(PlayerDisplay.Type.Global);
		this.scrollPane.getViewport().setOpaque(false);
		this.scrollPane.getViewport().setBackground(Color.red);
	}

	public final void updateDynamicComponents(PlayerDisplay.Type type) {
		playerLabel.setText(type.name()+" Points");
		switch (type) {
			case Global:
				LayoutGenerator.generateHorizontalLayout(displayPanel, PlayerDisplay.getPanels(playerManager.getPlayer(), type));
				break;
			case Quest:
				LayoutGenerator.generateHorizontalLayout(displayPanel, PlayerDisplay.getPanels(voting.getWinnerAndLoser(), type));
				break;
			default:
				throw new AssertionError();
		}
		
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlePanel = new javax.swing.JPanel();
        playerLabel = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        displayPanel = new javax.swing.JPanel();

        playerLabel.setFont(new java.awt.Font("DejaVu Sans Condensed", 1, 24)); // NOI18N
        playerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        playerLabel.setText("Player");
        playerLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout titlePanelLayout = new javax.swing.GroupLayout(titlePanel);
        titlePanel.setLayout(titlePanelLayout);
        titlePanelLayout.setHorizontalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(playerLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        titlePanelLayout.setVerticalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(playerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        scrollPane.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        scrollPane.setOpaque(false);

        displayPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        displayPanel.setOpaque(false);

        javax.swing.GroupLayout displayPanelLayout = new javax.swing.GroupLayout(displayPanel);
        displayPanel.setLayout(displayPanelLayout);
        displayPanelLayout.setHorizontalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 807, Short.MAX_VALUE)
        );
        displayPanelLayout.setVerticalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 284, Short.MAX_VALUE)
        );

        scrollPane.setViewportView(displayPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scrollPane)
                    .addComponent(titlePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollPane)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel displayPanel;
    private javax.swing.JLabel playerLabel;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JPanel titlePanel;
    // End of variables declaration//GEN-END:variables

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getNewValue() == PresentQuestPointsState.class) {
			updateDynamicComponents(PlayerDisplay.Type.Quest);
		} else if (evt.getNewValue() == PresentGlobalPointsState.class) {
			updateDynamicComponents(PlayerDisplay.Type.Global);
		}
	}
}
