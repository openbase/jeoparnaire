package org.openbase.jeoparnaire.view.client;

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

import org.openbase.jeoparnaire.view.server.GameColors;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class InfoPanel extends JPanel implements ActionListener {

	private static InfoPanel instance;

	private Timer timer;
	private String infoText;
	private String defaultText = "";
	private Object source;

	/** Creates new form InfoPanel */
    public InfoPanel() {
        instance = this;
		initComponents();
		this.timer = new Timer(5000, this);
		this.timer.setActionCommand("TIMER");
    }

	public static void printAffirmation(Object source, String infoText) {
		instance.print(source, infoText, GameColors.GREEN);
	}

	public static void printInfo(Object source, String infoText) {
		instance.print(source, infoText, GameColors.BLACK);
	}

	public static void printWarning(Object source, String infoText) {
		instance.print(source, infoText, GameColors.ORANGE.darker());
	}

	public static void printError(Object source, String infoText) {
		instance.print(source, infoText, GameColors.RED);
	}

	private void print(Object source, String infoText, Color color) {
		instance.source = source;
		this.infoText = infoText;
		this.infoTextLabel.setForeground(color);
		this.infoTextLabel.setText(infoText);
		if(this.timer.isRunning()) {
			timer.restart();
		} else {
			timer.start();
		}
	}
	
	public final static Object FORCE_RESET = null;
	public static void reset(Object source) {
		if(source != FORCE_RESET && instance.source != null && source != instance.source) {
			return;
		}
		if(instance.timer.isRunning()) {
			instance.timer.stop();
		}
		instance.source = null;
		printInfo(null, instance.defaultText);
	}

	@Override
	public void actionPerformed(ActionEvent ex) {
		if(ex.getActionCommand().equals("TIMER")) {
			reset(FORCE_RESET);
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

        infoTextLabel = new javax.swing.JLabel();

        infoTextLabel.setFont(new java.awt.Font("DejaVu Sans", 1, 14)); // NOI18N
        infoTextLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infoTextLabel.setText("Welcome");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(infoTextLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(infoTextLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel infoTextLabel;
    // End of variables declaration//GEN-END:variables
}
