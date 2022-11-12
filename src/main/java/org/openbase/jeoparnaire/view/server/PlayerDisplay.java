package org.openbase.jeoparnaire.view.server;

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

import org.openbase.jeoparnaire.data.Player;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import org.openbase.jeoparnaire.view.GameStyle;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class PlayerDisplay extends javax.swing.JPanel {

	private static final DecimalFormat FORMATER = new DecimalFormat(".##");

	public enum Type {

		Quest, Global
	};

	public static Collection<PlayerDisplay> getPanels(Collection<Player> playerCollection, Type type) {
		ArrayList<PlayerDisplay> arrayList = new ArrayList<>();
		arrayList.add(new PlayerDisplay(null, -1, type));
		int index = 0;
		for (Player player : playerCollection) {
			arrayList.add(new PlayerDisplay(player, ++index, type));
		}
		return arrayList;
	}

	/**
	 * Creates new form PlayerDisplay
	 */
	public PlayerDisplay() {
		this(null, -1, Type.Global);
	}

	public PlayerDisplay(Player player, int position, Type type) {
		initComponents();
		this.setBackground(GameColors.BLUE);
		this.setBorder(javax.swing.BorderFactory.createLineBorder(GameColors.BLACK, 8));
		if (player == null) {
			positionLabel.setForeground(GameColors.FONT_TITLE);
			nameLabel.setForeground(GameColors.FONT_TITLE);
			pingLabel.setForeground(GameColors.FONT_TITLE);
			pointsLabel.setForeground(GameColors.FONT_TITLE);
            
            positionLabel.setFont(positionLabel.getFont().deriveFont(GameStyle.FONT_SIZE));
            nameLabel.setFont(nameLabel.getFont().deriveFont(GameStyle.FONT_SIZE));
            pingLabel.setFont(pingLabel.getFont().deriveFont(GameStyle.FONT_SIZE));
            pointsLabel.setFont(pointsLabel.getFont().deriveFont(GameStyle.FONT_SIZE));

			switch (type) {
				case Global:
					pingLabel.setText("Ping");
					break;
				case Quest:
					pingLabel.setText("Delay");
					break;
				default:
					throw new AssertionError();
			}
			return;
		}

		positionLabel.setForeground(GameColors.FONT);
		nameLabel.setForeground(GameColors.FONT);
		pingLabel.setForeground(GameColors.FONT);
		pointsLabel.setForeground(GameColors.FONT);

		switch (type) {
			case Global:
				positionLabel.setText(Integer.toString(position));
				nameLabel.setText(player.getName());

				if (player.isOnline()) {
					pingLabel.setText(Long.toString(player.getClientConnection().getDelay()));
				} else {
					pingLabel.setText("off");
				}
				pointsLabel.setText(Integer.toString(player.getPoints()));
				break;
			case Quest:
				positionLabel.setText(Integer.toString(position));
				nameLabel.setText(player.getName());
				pingLabel.setText(FORMATER.format(player.getLastDelay() / 1000.) + " sec");

				if (player.getLastPointChange() > 0) {
					pointsLabel.setForeground(GameColors.GREEN);
					pointsLabel.setText("+" + Integer.toString(player.getLastPointChange()));
				} else if (player.getLastPointChange() == 0) {
					pointsLabel.setForeground(GameColors.FONT);
					pointsLabel.setText(Integer.toString(player.getLastPointChange()));
				} else {
					pointsLabel.setForeground(GameColors.RED);
					pointsLabel.setText(Integer.toString(player.getLastPointChange()));
				}
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

        nameLabel = new javax.swing.JLabel();
        pingLabel = new javax.swing.JLabel();
        positionLabel = new javax.swing.JLabel();
        pointsLabel = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(32767, 67));

        nameLabel.setFont(new java.awt.Font("DejaVu Sans", 1, 24)); // NOI18N
        nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nameLabel.setText("Name");

        pingLabel.setFont(new java.awt.Font("DejaVu Sans", 1, 24)); // NOI18N
        pingLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pingLabel.setText("Ping");

        positionLabel.setFont(new java.awt.Font("DejaVu Sans", 1, 24)); // NOI18N
        positionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        positionLabel.setText("Position");

        pointsLabel.setFont(new java.awt.Font("DejaVu Sans", 1, 24)); // NOI18N
        pointsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pointsLabel.setText("Points");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(positionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(pingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pointsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(positionLabel)
                    .addComponent(pingLabel)
                    .addComponent(pointsLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel pingLabel;
    private javax.swing.JLabel pointsLabel;
    private javax.swing.JLabel positionLabel;
    // End of variables declaration//GEN-END:variables
}
