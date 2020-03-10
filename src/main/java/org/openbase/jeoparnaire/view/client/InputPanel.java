package org.openbase.jeoparnaire.view.client;

/*-
 * #%L
 * Jeoparnaire
 * %%
 * Copyright (C) 2011 - 2020 openbase.org
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

import org.openbase.jeoparnaire.net.client.ServerConnection;
import org.openbase.jeoparnaire.net.command.VoteCommand;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.printer.ExceptionPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class InputPanel extends javax.swing.JPanel {

    private static final Logger LOGGER = LoggerFactory.getLogger(InputPanel.class);

    private boolean voting;
    private ServerConnection serverConnection;

    /**
     * Creates new form InputPanel
     */
    public InputPanel() {
        initComponents();
        enableVoting(false);
        enableConnectionSettings(true);
        activateKeyboardMapping();
    }

    public final void activateKeyboardMapping() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

            @Override
            public synchronized boolean dispatchKeyEvent(KeyEvent evt) {
                if (evt.getID() == KeyEvent.KEY_PRESSED || !voting) {
                    return false;
                }

//				System.out.println(KeyEvent.KEY_LOCATION_LEFT+"KEY: "+evt.getKeyCode()+ ".");
                if (evt.getKeyCode() == KeyEvent.VK_1 || evt.getKeyCode() == KeyEvent.VK_Q) {
                    aToggleButton.setSelected(true);
                    vote(VoteCommand.Vote.A);
                } else if (evt.getKeyCode() == KeyEvent.VK_2 || evt.getKeyCode() == KeyEvent.VK_W) {
                    bToggleButton.setSelected(true);
                    vote(VoteCommand.Vote.B);
                } else if (evt.getKeyCode() == KeyEvent.VK_3 || evt.getKeyCode() == KeyEvent.VK_A) {
                    cToggleButton.setSelected(true);
                    vote(VoteCommand.Vote.C);
                } else if (evt.getKeyCode() == KeyEvent.VK_4 || evt.getKeyCode() == KeyEvent.VK_S) {
                    dToggleButton.setSelected(true);
                    vote(VoteCommand.Vote.D);
                } else {
                    return false;
                }
                return true;
            }
        });
    }

    public final void enableVoting(boolean enable) {
        voting = enable;
        aToggleButton.setEnabled(enable);
        bToggleButton.setEnabled(enable);
        cToggleButton.setEnabled(enable);
        dToggleButton.setEnabled(enable);

        if (enable) {
            aToggleButton.setSelected(false);
            bToggleButton.setSelected(false);
            cToggleButton.setSelected(false);
            dToggleButton.setSelected(false);
        }
    }

    public final void vote(VoteCommand.Vote vote) {
        enableVoting(false);
        try {
            serverConnection.sendCommand(new VoteCommand(vote));
        } catch (CouldNotPerformException ex) {
            ExceptionPrinter.printHistory("Vote failed!", ex, LOGGER);
            InfoPanel.printWarning(this, "Vote failed!");
        }
    }

    public final void enableConnectionSettings(boolean enable) {
        nameTextField.setEnabled(enable);
        hostTextField.setEnabled(enable);
        loginButton.setEnabled(enable);
    }

    public void updatePing(String ping) {
        pingTextField.setText(ping);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        connectionPanel = new javax.swing.JPanel();
        playerLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        playerLabel1 = new javax.swing.JLabel();
        hostTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        pingTextField = new javax.swing.JTextField();
        loginButton = new javax.swing.JButton();
        answerPanel = new javax.swing.JPanel();
        answerAPanel = new javax.swing.JPanel();
        aToggleButton = new javax.swing.JToggleButton();
        answerBPanel = new javax.swing.JPanel();
        bToggleButton = new javax.swing.JToggleButton();
        answerCPanel = new javax.swing.JPanel();
        cToggleButton = new javax.swing.JToggleButton();
        answerDPanel = new javax.swing.JPanel();
        dToggleButton = new javax.swing.JToggleButton();
        infoPanel = new org.openbase.jeoparnaire.view.client.InfoPanel();

        setOpaque(false);

        connectionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Connection"));
        connectionPanel.setOpaque(false);

        playerLabel.setFont(new java.awt.Font("DejaVu Sans", 1, 13)); // NOI18N
        playerLabel.setText("Playername");

        nameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameTextFieldActionPerformed(evt);
            }
        });

        playerLabel1.setFont(new java.awt.Font("DejaVu Sans", 1, 13)); // NOI18N
        playerLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        playerLabel1.setText("Server");

        hostTextField.setText("Jeoparnaire");
        hostTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hostTextFieldActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 1, 13)); // NOI18N
        jLabel1.setText("Ping");

        pingTextField.setEditable(false);
        pingTextField.setEnabled(false);
        pingTextField.setFocusable(false);
        pingTextField.setRequestFocusEnabled(false);
        pingTextField.setVerifyInputWhenFocusTarget(false);
        pingTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pingTextFieldActionPerformed(evt);
            }
        });

        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout connectionPanelLayout = new javax.swing.GroupLayout(connectionPanel);
        connectionPanel.setLayout(connectionPanelLayout);
        connectionPanelLayout.setHorizontalGroup(
            connectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(connectionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(connectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(playerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(playerLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(connectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(connectionPanelLayout.createSequentialGroup()
                        .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(4, 4, 4)
                        .addComponent(pingTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(connectionPanelLayout.createSequentialGroup()
                        .addComponent(hostTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loginButton)))
                .addContainerGap())
        );
        connectionPanelLayout.setVerticalGroup(
            connectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(connectionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(connectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playerLabel)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(pingTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(connectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playerLabel1)
                    .addComponent(hostTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loginButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        answerPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Vote"));
        answerPanel.setOpaque(false);
        java.awt.GridBagLayout answerPanelLayout = new java.awt.GridBagLayout();
        answerPanelLayout.columnWidths = new int[] {0, 10, 0};
        answerPanelLayout.rowHeights = new int[] {0, 10, 0};
        answerPanel.setLayout(answerPanelLayout);

        aToggleButton.setFont(new java.awt.Font("DejaVu Sans", 1, 48)); // NOI18N
        aToggleButton.setText("A");
        aToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aToggleButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout answerAPanelLayout = new javax.swing.GroupLayout(answerAPanel);
        answerAPanel.setLayout(answerAPanelLayout);
        answerAPanelLayout.setHorizontalGroup(
            answerAPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(aToggleButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
        );
        answerAPanelLayout.setVerticalGroup(
            answerAPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(aToggleButton, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        answerPanel.add(answerAPanel, gridBagConstraints);

        bToggleButton.setFont(new java.awt.Font("DejaVu Sans", 1, 48)); // NOI18N
        bToggleButton.setText("B");
        bToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bToggleButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout answerBPanelLayout = new javax.swing.GroupLayout(answerBPanel);
        answerBPanel.setLayout(answerBPanelLayout);
        answerBPanelLayout.setHorizontalGroup(
            answerBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bToggleButton, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
        );
        answerBPanelLayout.setVerticalGroup(
            answerBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bToggleButton, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        answerPanel.add(answerBPanel, gridBagConstraints);

        cToggleButton.setFont(new java.awt.Font("DejaVu Sans", 1, 48)); // NOI18N
        cToggleButton.setText("C");
        cToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cToggleButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout answerCPanelLayout = new javax.swing.GroupLayout(answerCPanel);
        answerCPanel.setLayout(answerCPanelLayout);
        answerCPanelLayout.setHorizontalGroup(
            answerCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cToggleButton, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
        );
        answerCPanelLayout.setVerticalGroup(
            answerCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cToggleButton, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        answerPanel.add(answerCPanel, gridBagConstraints);

        dToggleButton.setFont(new java.awt.Font("DejaVu Sans", 1, 48)); // NOI18N
        dToggleButton.setText("D");
        dToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dToggleButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout answerDPanelLayout = new javax.swing.GroupLayout(answerDPanel);
        answerDPanel.setLayout(answerDPanelLayout);
        answerDPanelLayout.setHorizontalGroup(
            answerDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dToggleButton, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
        );
        answerDPanelLayout.setVerticalGroup(
            answerDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dToggleButton, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        answerPanel.add(answerDPanel, gridBagConstraints);

        infoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Info"));
        infoPanel.setOpaque(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(answerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE)
                    .addComponent(infoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(connectionPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(answerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(connectionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	private void nameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameTextFieldActionPerformed
        // TODO add your handling code here:
	}//GEN-LAST:event_nameTextFieldActionPerformed

	private void hostTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hostTextFieldActionPerformed
        // TODO add your handling code here:
	}//GEN-LAST:event_hostTextFieldActionPerformed

	private void pingTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pingTextFieldActionPerformed
        // TODO add your handling code here:
	}//GEN-LAST:event_pingTextFieldActionPerformed

	private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        if (nameTextField.getText().isEmpty()) {
            InfoPanel.printWarning(this, "Playername is missing!");
            return;
        }

        enableConnectionSettings(false);
        serverConnection = new ServerConnection(hostTextField.getText(), this);
        serverConnection.connectToServer(nameTextField.getText());
	}//GEN-LAST:event_loginButtonActionPerformed

	private void bToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bToggleButtonActionPerformed
        vote(VoteCommand.Vote.B);
	}//GEN-LAST:event_bToggleButtonActionPerformed

	private void cToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cToggleButtonActionPerformed
        vote(VoteCommand.Vote.C);
	}//GEN-LAST:event_cToggleButtonActionPerformed

	private void dToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dToggleButtonActionPerformed
        vote(VoteCommand.Vote.D);
	}//GEN-LAST:event_dToggleButtonActionPerformed

	private void aToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aToggleButtonActionPerformed
        vote(VoteCommand.Vote.A);
	}//GEN-LAST:event_aToggleButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton aToggleButton;
    private javax.swing.JPanel answerAPanel;
    private javax.swing.JPanel answerBPanel;
    private javax.swing.JPanel answerCPanel;
    private javax.swing.JPanel answerDPanel;
    private javax.swing.JPanel answerPanel;
    private javax.swing.JToggleButton bToggleButton;
    private javax.swing.JToggleButton cToggleButton;
    private javax.swing.JPanel connectionPanel;
    private javax.swing.JToggleButton dToggleButton;
    private javax.swing.JTextField hostTextField;
    private org.openbase.jeoparnaire.view.client.InfoPanel infoPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton loginButton;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JTextField pingTextField;
    private javax.swing.JLabel playerLabel;
    private javax.swing.JLabel playerLabel1;
    // End of variables declaration//GEN-END:variables
}
