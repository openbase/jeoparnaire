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
import org.openbase.jeoparnaire.data.Category;
import org.openbase.jeoparnaire.data.GameData;
import org.openbase.jeoparnaire.data.Quest;
import org.openbase.jeoparnaire.net.server.ServerService;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.openbase.jul.exception.printer.ExceptionPrinter;
import org.openbase.jul.exception.printer.LogLevel;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class CategoryDisplayPanel extends javax.swing.JPanel implements PropertyChangeListener {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ServerService.class);

    private List<QuestPanel> questPanels;
    private QuestPanel selectedButton;

    /**
     * Creates new form CategoryDisplayPanel
     */
    public CategoryDisplayPanel() {
        this.questPanels = new ArrayList<>();
        this.initComponents();
        this.setBackground(GameColors.BLUE_BORDER);
        this.setBorder(javax.swing.BorderFactory.createLineBorder(GameColors.BLACK, 2));
    }

    private void updateDynamicComponents() {
        GameData data = GameController.getInstance().getData();

        int gridwidth = 1;
        int gridheight = 1;

        double weightx = 1.0;
        double weighty = 1.0;
        int anchor = GridBagConstraints.CENTER;
        int fill = GridBagConstraints.BOTH;

        Insets insets = new Insets(5, 5, 5, 5);
        int ipadx = 0;
        int ipady = 0;

        Category category;
        Quest quest;
        QuestPanel buttonPanel;

        for (int categoryIndex = 0; categoryIndex < data.getCategorys().size(); ++categoryIndex) {
            category = data.getCategorys().get(categoryIndex);
            add(new TopicPanel(category.getName()), new GridBagConstraints(categoryIndex, 0, gridwidth, gridheight, weightx, weighty, anchor, fill, insets, ipadx, ipady));

            for (int questIndex = 0; questIndex < category.getQuests().size(); ++questIndex) {
                quest = category.getQuests().get(questIndex);
                buttonPanel = new QuestPanel(quest, this);
                add(buttonPanel, new GridBagConstraints(categoryIndex, questIndex + 1, gridwidth, gridheight, weightx, weighty, anchor, fill, insets, ipadx, ipady));
                questPanels.add(buttonPanel);
            }
        }
        validate();
    }

    public synchronized void selectRandomly() {
        new Thread() {

            @Override
            public void run() {
                try {
                    long delay = questPanels.size();

                    QuestPanel activeButtonPanel = null;
                    Collections.shuffle(questPanels);

                    for (QuestPanel buttonPanel : questPanels) {
                        if (activeButtonPanel != null) {
                            activeButtonPanel.setSelect(false);
                        }

                        activeButtonPanel = buttonPanel;
                        selectButton(buttonPanel);

                        try {
                            Thread.sleep(delay);
                        } catch (InterruptedException ex) {
                            ExceptionPrinter.printHistory("Could not sleep", ex, LOGGER, LogLevel.WARN);
                        }
                        delay = Math.max(50, delay - 10);
                    }
                } catch (Exception ex) {
                    ExceptionPrinter.printHistory("Error during random selection.", ex, LOGGER);
                }
            }
        }.start();

    }

    public synchronized void selectButton(QuestPanel button) {
        if (selectedButton != null) {
            selectedButton.setSelect(false);
        }

        selectedButton = button;

        if (button != null) {
            selectedButton.setSelect(true);
        }
    }

    public Quest getSelectedQuest() {
        if (selectedButton == null) {
            return null;
        }
        return selectedButton.getQuest();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.GridBagLayout());
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case GameController.DATA_UPDATE:
                updateDynamicComponents();
                break;
            case GameController.QUEST_UPDATE:
                Quest quest = (Quest) evt.getNewValue();
                for (QuestPanel questPanel : questPanels) {
                    if (questPanel.getQuest() == quest) {
                        questPanels.remove(questPanel);
                        break;
                    }
                }
                break;
        }
    }
}
