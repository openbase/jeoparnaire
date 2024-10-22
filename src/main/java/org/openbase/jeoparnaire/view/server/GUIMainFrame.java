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
import org.openbase.jeoparnaire.data.Quest;
import java.awt.CardLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import org.openbase.jeoparnaire.state.GameStartState;
import org.openbase.jeoparnaire.state.PresentQuestPointsState;
import org.openbase.jeoparnaire.state.TaskFinalExecutionState;
import org.openbase.jeoparnaire.state.TaskPostExecutionState;
import org.openbase.jeoparnaire.state.TaskPreExecutionState;
import org.openbase.jeoparnaire.state.TaskSolving1State;
import org.openbase.jeoparnaire.state.TaskSolving2State;
import org.openbase.jeoparnaire.state.WaitForQuestState;
import org.openbase.jul.exception.printer.ExceptionPrinter;
import org.openbase.jul.exception.printer.LogLevel;
import org.openbase.jul.pattern.statemachine.StateRunner;
import org.openbase.jul.visual.swing.screen.ScreenModeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class GUIMainFrame extends javax.swing.JFrame implements PropertyChangeListener {

    public enum Display {

        Category, Loading, Welcome, TaskSolving, Execution, PointOverview
    };

    private static final Logger LOGGER = LoggerFactory.getLogger(GUIMainFrame.class);

    private ScreenModeController screenModeController;
    private GameController gameController;
    private static GUIMainFrame instance;
    private CardLayout cardLayout;
    private CategoryDisplayPanel categoryDisplayPanel;
    private TaskSolvingPanel taskSolvingPanel;

    /**
     * Creates new form GUIMainFrame
     */
    private GUIMainFrame() {
        this.initComponents();
        this.getContentPane().setBackground(GameColors.BLUE);
        this.cardLayout = new CardLayout();
        this.displayPanel.setLayout(cardLayout);

        this.categoryDisplayPanel = new CategoryDisplayPanel();
        this.taskSolvingPanel = new TaskSolvingPanel();
        this.displayPanel.add(categoryDisplayPanel, Display.Category.name());
        this.displayPanel.add(taskSolvingPanel, Display.TaskSolving.name());
        this.displayPanel.add(new LoadingPanel(), Display.Loading.name());
        this.displayPanel.add(new WelcomePanel(), Display.Welcome.name());
        this.displayPanel.add(new ExecutionPanel(), Display.Execution.name());
        this.displayPanel.add(new PointOverview(), Display.PointOverview.name());

        this.setDisplay(Display.Loading);
        this.screenModeController = new ScreenModeController(this, ScreenModeController.ScreenMode.Fullscreen, ScreenModeController.OnTopMode.Always);
        this.activateKeyboardMapping();
    }

    /**
     * Creates new form GUIMainFrame
     */
    private GUIMainFrame(GameController gameController) {
        this();
        this.gameController = gameController;
        this.gameController.addPropertyChangeListener(this);
        this.gameController.addPropertyChangeListener(categoryDisplayPanel);
    }

    public static GUIMainFrame getInstance() {
        if (instance == null) {
            throw new RuntimeException("Coult not return instance, because instance never initialized.");
        }
        return instance;
    }

    public static synchronized GUIMainFrame initGUI(final GameController gameController) {
        if (instance != null) {
            LOGGER.error("Gui already initialized!");
            return instance;
        }
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {

                @Override
                public void run() {
                    try {
                        instance = new GUIMainFrame(gameController);
                    } catch (Exception ex) {
                        ExceptionPrinter.printHistory("Could not init GUI!", ex, LOGGER);
                    }
                }
            });
        } catch (InterruptedException | InvocationTargetException ex) {
            ExceptionPrinter.printHistory("Could not setup GUI propertly.", ex, LOGGER, LogLevel.WARN);
        }

        return instance;
    }

    public final void setDisplay(Display display) {
        cardLayout.show(displayPanel, display.name());
        validate();
    }

    public final void activateKeyboardMapping() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

            @Override
            public boolean dispatchKeyEvent(KeyEvent evt) {
                if (evt.getID() == KeyEvent.KEY_PRESSED) {
                    return false;
                }

                LOGGER.info("KEy:" + evt.getKeyCode());

                if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
                    evt.setKeyCode(KeyEvent.VK_ENTER);
                } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
                    evt.setKeyCode(KeyEvent.VK_SPACE);
                }

                gameController.getChanges().firePropertyChange(GameController.KEY_EVENT, null, evt);

                //System.out.println(KeyEvent.KEY_LOCATION_LEFT+"KEY: "+evt.getKeyCode()+ ".");
                if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                } else if ((evt.getKeyCode() == KeyEvent.VK_ENTER)) {
                    if (gameController.getStateRunner().isCurrentState(WaitForQuestState.class)) {
                        Quest selectedQuest = categoryDisplayPanel.getSelectedQuest();
                        if (selectedQuest != null) {
                            gameController.setActiveQuest(selectedQuest);
                        }
                    }
                } else if ((evt.getKeyCode() == KeyEvent.VK_SPACE)) {
                    categoryDisplayPanel.selectRandomly();
                } else if (evt.getKeyCode() == KeyEvent.VK_F11) {
                    screenModeController.toggleFullScreen();
                } else {
                    return false;
                }
                return true;
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        displayPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Jeoparnaire");
        setFocusable(false);
        setUndecorated(true);

        displayPanel.setOpaque(false);

        javax.swing.GroupLayout displayPanelLayout = new javax.swing.GroupLayout(displayPanel);
        displayPanel.setLayout(displayPanelLayout);
        displayPanelLayout.setHorizontalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1052, Short.MAX_VALUE)
        );
        displayPanelLayout.setVerticalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 768, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(displayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(displayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel displayPanel;
    // End of variables declaration//GEN-END:variables

    public CategoryDisplayPanel getCategoryDisplayPanel() {
        return categoryDisplayPanel;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(StateRunner.STATE_CHANGE)) {
            if (evt.getNewValue() == WaitForQuestState.class) {
                setDisplay(Display.Category);
            } else if ((evt.getNewValue() == GameStartState.class)) {
                setDisplay(Display.Welcome);
            } else if ((evt.getNewValue() == TaskPreExecutionState.class)) {
                setDisplay(Display.Execution);
                setAlwaysOnTop(false);
            } else if ((evt.getNewValue() == TaskPostExecutionState.class)) {
                setDisplay(Display.Execution);
                setAlwaysOnTop(false);
            } else if ((evt.getNewValue() == TaskFinalExecutionState.class)) {
                setDisplay(Display.Execution);
                setAlwaysOnTop(false);
            } else if (evt.getNewValue() == TaskSolving1State.class) {
                setAlwaysOnTop(true);
                toFront();
                requestFocus();
            } else if (evt.getNewValue() == TaskSolving2State.class) {
                setAlwaysOnTop(true);
                toFront();
                requestFocus();
            } else if (evt.getNewValue() == PresentQuestPointsState.class) {
                setAlwaysOnTop(true);
                toFront();
                requestFocus();
                setDisplay(Display.PointOverview);
            }
        }
    }
}
