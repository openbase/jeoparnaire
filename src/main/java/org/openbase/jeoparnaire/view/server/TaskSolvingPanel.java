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
import org.openbase.jeoparnaire.controller.Voting;
import org.openbase.jeoparnaire.data.Answer;
import org.openbase.jeoparnaire.data.Player;
import org.openbase.jeoparnaire.data.Quest;
import org.openbase.jeoparnaire.state.TaskSolving1State;
import org.openbase.jeoparnaire.state.TaskSolving2State;
import org.openbase.jeoparnaire.state.TaskSolving3State;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import org.openbase.jeoparnaire.view.GameStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class TaskSolvingPanel extends javax.swing.JPanel implements PropertyChangeListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskSolvingPanel.class);
    
	public static final int A = 0;
	public static final int B = 1;
	public static final int C = 2;
	public static final int D = 3;
	private Quest quest;
	private List<Answer> answers;
	private final Voting voting;
	private static final DecimalFormat FORMATER = new DecimalFormat(".##");

	/**
	 * Creates new form TaskSolvingPanel
	 */
	public TaskSolvingPanel() {
		initComponents();
		questionLabel.setForeground(GameColors.FONT_TITLE);
        questionLabel.setFont(questionLabel.getFont().deriveFont(GameStyle.FONT_SIZE));
		questionPanel.setBorder(javax.swing.BorderFactory.createLineBorder(GameColors.BLACK, 8));
		questionPanel.setBackground(GameColors.BLUE);
		setBackground(GameColors.BLUE_BORDER);
		setBorder(javax.swing.BorderFactory.createLineBorder(GameColors.BLACK, 2));
		GameController.getInstance().addPropertyChangeListener(this);
		voting = Voting.getInstance();
		votingDisplay(false);
	}

	public synchronized void setQuest(Quest quest) {
		if (this.quest != null) {
			this.quest.removePropertyChangeListener(this);
		}
		this.quest = quest;
		quest.addPropertyChangeListener(this);
		this.answers = quest.getAnswers();
		updateDynamicComponents();
	}

	private String buildHTML(String id, String value) {
		return "<html>"
				+ "<body>"
				+ "<table cellspacing=\"10\">"
				+ "<tr>"
				+ "<td><font color=\""+GameColors.rgbToHex(GameColors.FONT_TITLE)+"\">" + id + "</font></td>"
				+ "<td><p>" + value + "</p></td>"
				+ "</tr>"
				+ "</table>"
				+ "</body>"
				+ "</html>";
	}

	private void updateDynamicComponents() {
		questionLabel.setText(quest.getQuestion());
		answerALabel.setText(buildHTML("A", answers.get(A).getText()));
		answerBLabel.setText(buildHTML("B", answers.get(B).getText()));
		answerCLabel.setText(buildHTML("C", answers.get(C).getText()));
		answerDLabel.setText(buildHTML("D", answers.get(D).getText()));
        
        answerALabel.setFont(answerALabel.getFont().deriveFont(GameStyle.FONT_SIZE));
        answerBLabel.setFont(answerBLabel.getFont().deriveFont(GameStyle.FONT_SIZE));
        answerCLabel.setFont(answerCLabel.getFont().deriveFont(GameStyle.FONT_SIZE));
        answerDLabel.setFont(answerDLabel.getFont().deriveFont(GameStyle.FONT_SIZE));
        
		answerAPanel.setBackground(GameColors.BLUE);
		answerBPanel.setBackground(GameColors.BLUE);
		answerCPanel.setBackground(GameColors.BLUE);
		answerDPanel.setBackground(GameColors.BLUE);
		answerAPanel.setBorder(javax.swing.BorderFactory.createLineBorder(GameColors.BLACK, 8));
		answerBPanel.setBorder(javax.swing.BorderFactory.createLineBorder(GameColors.BLACK, 8));
		answerCPanel.setBorder(javax.swing.BorderFactory.createLineBorder(GameColors.BLACK, 8));
		answerDPanel.setBorder(javax.swing.BorderFactory.createLineBorder(GameColors.BLACK, 8));

		player1nameLabel.setForeground(GameColors.FONT);
		player1timeLabel.setForeground(GameColors.FONT);
		player2nameLabel.setForeground(GameColors.FONT);
		player2timeLabel.setForeground(GameColors.FONT);
		player3nameLabel.setForeground(GameColors.FONT);
		player3timeLabel.setForeground(GameColors.FONT);

		player1Panel.setBackground(GameColors.BLUE);
		player2Panel.setBackground(GameColors.BLUE);
		player3Panel.setBackground(GameColors.BLUE);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(GameController.QUEST_UPDATE)) {
			setQuest(GameController.getInstance().getActiveQuest());
			votingDisplay(false);
		} else if (evt.getNewValue() == TaskSolving1State.class) {

			answerALabel.setForeground(GameColors.FONT);
			answerBLabel.setForeground(GameColors.FONT);
			answerCLabel.setForeground(GameColors.FONT);
			answerDLabel.setForeground(GameColors.FONT);

			GUIMainFrame.getInstance().setDisplay(GUIMainFrame.Display.TaskSolving);
		} else if (evt.getNewValue() == TaskSolving2State.class) {
			GUIMainFrame.getInstance().setDisplay(GUIMainFrame.Display.TaskSolving);
			votingDisplay(true);
		} else if (evt.getNewValue() == TaskSolving3State.class) {
			if (answers.get(A).isRight()) {
				answerALabel.setForeground(GameColors.GREEN);
			} else {
				answerALabel.setForeground(GameColors.RED);
			}

			if (answers.get(B).isRight()) {
				answerBLabel.setForeground(GameColors.GREEN);
			} else {
				answerBLabel.setForeground(GameColors.RED);
			}

			if (answers.get(C).isRight()) {
				answerCLabel.setForeground(GameColors.GREEN);
			} else {
				answerCLabel.setForeground(GameColors.RED);
			}

			if (answers.get(D).isRight()) {
				answerDLabel.setForeground(GameColors.GREEN);
			} else {
				answerDLabel.setForeground(GameColors.RED);
			}
			GUIMainFrame.getInstance().setDisplay(GUIMainFrame.Display.TaskSolving);
		}
	}

	private synchronized void votingDisplay(final boolean enable) {
		Thread animationThread = new Thread(new Runnable() {

			@Override
			public void run() {
				votingPanel.setVisible(false);
				player1nameLabel.setText("");
				player2nameLabel.setText("");
				player3nameLabel.setText("");
				player1timeLabel.setText("");
				player2timeLabel.setText("");
				player3timeLabel.setText("");
				player1Panel.setBorder(javax.swing.BorderFactory.createLineBorder(GameColors.BLACK, 0));
				player2Panel.setBorder(javax.swing.BorderFactory.createLineBorder(GameColors.BLACK, 0));
				player3Panel.setBorder(javax.swing.BorderFactory.createLineBorder(GameColors.BLACK, 0));
				player1Panel.setOpaque(false);
				player2Panel.setOpaque(false);
				player3Panel.setOpaque(false);

				if (!enable) {
					return;
				}

				votingPanel.setVisible(true);

				Map<Long, Player> winner = voting.getWinner();
				Long[] winnerKeyArray = winner.keySet().toArray(new Long[winner.size()]);

				if (winnerKeyArray.length >= 1) {
					player1Panel.setBorder(javax.swing.BorderFactory.createLineBorder(GameColors.BLACK, 8));
					player1Panel.setOpaque(true);
					player1timeLabel.setText(FORMATER.format(winnerKeyArray[0] / 1000.) + " sec");
					waitFor(500);
					player1nameLabel.setText("1." + winner.get(winnerKeyArray[0]).getName());
					waitFor(2000);
				}

				if (winnerKeyArray.length >= 2) {
					player2Panel.setBorder(javax.swing.BorderFactory.createLineBorder(GameColors.BLACK, 8));
					player2Panel.setOpaque(true);
					player2timeLabel.setText(FORMATER.format(winnerKeyArray[1] / 1000.) + " sec");
					waitFor(1000);
					player2nameLabel.setText("2." + winner.get(winnerKeyArray[1]).getName());
					waitFor(1000);
				}

				if (winnerKeyArray.length >= 3) {
					player3Panel.setBorder(javax.swing.BorderFactory.createLineBorder(GameColors.BLACK, 8));
					player3Panel.setOpaque(true);
					player3timeLabel.setText(FORMATER.format(winnerKeyArray[2] / 1000.) + " sec");
					waitFor(1000);
					player3nameLabel.setText("3." + winner.get(winnerKeyArray[2]).getName());
				}
			}
		});
		animationThread.start();
	}

	private void waitFor(long delay) {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException ex) {
			LOGGER.warn("Could not wait for delay!");
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
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel7 = new javax.swing.JPanel();
        questionPanel = new javax.swing.JPanel();
        questionLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        answerAPanel = new javax.swing.JPanel();
        answerALabel = new javax.swing.JLabel();
        answerBPanel = new javax.swing.JPanel();
        answerBLabel = new javax.swing.JLabel();
        answerCPanel = new javax.swing.JPanel();
        answerCLabel = new javax.swing.JLabel();
        answerDPanel = new javax.swing.JPanel();
        answerDLabel = new javax.swing.JLabel();
        votingPanel = new javax.swing.JPanel();
        player1Panel = new javax.swing.JPanel();
        player1nameLabel = new javax.swing.JLabel();
        player1timeLabel = new javax.swing.JLabel();
        player2Panel = new javax.swing.JPanel();
        player2timeLabel = new javax.swing.JLabel();
        player2nameLabel = new javax.swing.JLabel();
        player3Panel = new javax.swing.JPanel();
        player3timeLabel = new javax.swing.JLabel();
        player3nameLabel = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setBackground(new java.awt.Color(18, 253, 53));

        questionLabel.setFont(new java.awt.Font("DejaVu Sans Condensed", 1, 24)); // NOI18N
        questionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        questionLabel.setText("Title");
        questionLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout questionPanelLayout = new javax.swing.GroupLayout(questionPanel);
        questionPanel.setLayout(questionPanelLayout);
        questionPanelLayout.setHorizontalGroup(
            questionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(questionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        questionPanelLayout.setVerticalGroup(
            questionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(questionPanelLayout.createSequentialGroup()
                .addComponent(questionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        answerALabel.setFont(new java.awt.Font("DejaVu Sans Condensed", 1, 24)); // NOI18N
        answerALabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        answerALabel.setText("A");
        answerALabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout answerAPanelLayout = new javax.swing.GroupLayout(answerAPanel);
        answerAPanel.setLayout(answerAPanelLayout);
        answerAPanelLayout.setHorizontalGroup(
            answerAPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(answerAPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(answerALabel, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                .addContainerGap())
        );
        answerAPanelLayout.setVerticalGroup(
            answerAPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(answerAPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(answerALabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 300;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(answerAPanel, gridBagConstraints);

        answerBLabel.setFont(new java.awt.Font("DejaVu Sans Condensed", 1, 24)); // NOI18N
        answerBLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        answerBLabel.setText("B");
        answerBLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout answerBPanelLayout = new javax.swing.GroupLayout(answerBPanel);
        answerBPanel.setLayout(answerBPanelLayout);
        answerBPanelLayout.setHorizontalGroup(
            answerBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(answerBPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(answerBLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                .addContainerGap())
        );
        answerBPanelLayout.setVerticalGroup(
            answerBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(answerBPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(answerBLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 300;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(answerBPanel, gridBagConstraints);

        answerCLabel.setFont(new java.awt.Font("DejaVu Sans Condensed", 1, 24)); // NOI18N
        answerCLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        answerCLabel.setText("C");
        answerCLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout answerCPanelLayout = new javax.swing.GroupLayout(answerCPanel);
        answerCPanel.setLayout(answerCPanelLayout);
        answerCPanelLayout.setHorizontalGroup(
            answerCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(answerCPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(answerCLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                .addContainerGap())
        );
        answerCPanelLayout.setVerticalGroup(
            answerCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(answerCPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(answerCLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 300;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(answerCPanel, gridBagConstraints);

        answerDLabel.setFont(new java.awt.Font("DejaVu Sans Condensed", 1, 24)); // NOI18N
        answerDLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        answerDLabel.setText("D");
        answerDLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout answerDPanelLayout = new javax.swing.GroupLayout(answerDPanel);
        answerDPanel.setLayout(answerDPanelLayout);
        answerDPanelLayout.setHorizontalGroup(
            answerDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(answerDPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(answerDLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        answerDPanelLayout.setVerticalGroup(
            answerDPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(answerDPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(answerDLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 300;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(answerDPanel, gridBagConstraints);

        votingPanel.setBackground(new java.awt.Color(255, 164, 0));
        votingPanel.setOpaque(false);
        votingPanel.setLayout(new javax.swing.BoxLayout(votingPanel, javax.swing.BoxLayout.LINE_AXIS));

        player1nameLabel.setFont(new java.awt.Font("DejaVu Sans", 1, 36)); // NOI18N
        player1nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        player1nameLabel.setText("1.Erni");

        player1timeLabel.setFont(new java.awt.Font("DejaVu Sans", 0, 24)); // NOI18N
        player1timeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        player1timeLabel.setText("1,3 sec");

        javax.swing.GroupLayout player1PanelLayout = new javax.swing.GroupLayout(player1Panel);
        player1Panel.setLayout(player1PanelLayout);
        player1PanelLayout.setHorizontalGroup(
            player1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, player1PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(player1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(player1timeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(player1nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))
                .addContainerGap())
        );
        player1PanelLayout.setVerticalGroup(
            player1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(player1PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(player1nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(player1timeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                .addContainerGap())
        );

        votingPanel.add(player1Panel);

        player2timeLabel.setFont(new java.awt.Font("DejaVu Sans", 0, 24)); // NOI18N
        player2timeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        player2timeLabel.setText("1,5 sec");

        player2nameLabel.setFont(new java.awt.Font("DejaVu Sans", 1, 36)); // NOI18N
        player2nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        player2nameLabel.setText("2.Bert");

        javax.swing.GroupLayout player2PanelLayout = new javax.swing.GroupLayout(player2Panel);
        player2Panel.setLayout(player2PanelLayout);
        player2PanelLayout.setHorizontalGroup(
            player2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, player2PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(player2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(player2timeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(player2nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE))
                .addContainerGap())
        );
        player2PanelLayout.setVerticalGroup(
            player2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(player2PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(player2nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(player2timeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                .addContainerGap())
        );

        votingPanel.add(player2Panel);

        player3timeLabel.setFont(new java.awt.Font("DejaVu Sans", 0, 24)); // NOI18N
        player3timeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        player3timeLabel.setText("4,3 sec");

        player3nameLabel.setFont(new java.awt.Font("DejaVu Sans", 1, 36)); // NOI18N
        player3nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        player3nameLabel.setText("3.Tiffi");

        javax.swing.GroupLayout player3PanelLayout = new javax.swing.GroupLayout(player3Panel);
        player3Panel.setLayout(player3PanelLayout);
        player3PanelLayout.setHorizontalGroup(
            player3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, player3PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(player3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(player3timeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(player3nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))
                .addContainerGap())
        );
        player3PanelLayout.setVerticalGroup(
            player3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(player3PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(player3nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(player3timeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                .addContainerGap())
        );

        votingPanel.add(player3Panel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 798, Short.MAX_VALUE)
                    .addComponent(questionPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(votingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(questionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 472, Short.MAX_VALUE)
                .addComponent(votingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel answerALabel;
    private javax.swing.JPanel answerAPanel;
    private javax.swing.JLabel answerBLabel;
    private javax.swing.JPanel answerBPanel;
    private javax.swing.JLabel answerCLabel;
    private javax.swing.JPanel answerCPanel;
    private javax.swing.JLabel answerDLabel;
    private javax.swing.JPanel answerDPanel;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel player1Panel;
    private javax.swing.JLabel player1nameLabel;
    private javax.swing.JLabel player1timeLabel;
    private javax.swing.JPanel player2Panel;
    private javax.swing.JLabel player2nameLabel;
    private javax.swing.JLabel player2timeLabel;
    private javax.swing.JPanel player3Panel;
    private javax.swing.JLabel player3nameLabel;
    private javax.swing.JLabel player3timeLabel;
    private javax.swing.JLabel questionLabel;
    private javax.swing.JPanel questionPanel;
    private javax.swing.JPanel votingPanel;
    // End of variables declaration//GEN-END:variables
}
