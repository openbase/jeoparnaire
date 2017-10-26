package org.openbase.jeoparnaire.data;

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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nu.xom.Element;
import nu.xom.Elements;
import org.openbase.jeoparnaire.tools.GameVariableStore;
import org.openbase.jul.exception.printer.ExceptionPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class Quest {
    
    

    private static final Logger LOGGER = LoggerFactory.getLogger(Quest.class);

    public static final String QUEST_SOLVED = "QuestSolved";
    public static final String QUEST_ACTIVE = "QuestActive";

    public static final int IS_FIRST_ELEMENT = 0;

    public static final String QUEST_ELEMENT = "Quest";
    public static final String EXECUTION_ELEMENT = "Execution";
    public static final String ANSWER_ELEMENT = "Answer";
    public static final String POINTS_ATTRIBUTE = "points";
    public static final String TEXT_ATTRIBUTE = "text";
    public static final String QUESTION_ATTRIBUTE = "question";
    public static final String COMMAND_ATTRIBUTE = "command";
    public static final String COMMAND_TYPE_ATTRIBUTE = "type";
    public static final String COMMAND_TYPE_PRE_VALUE = "pre";
    public static final String COMMAND_TYPE_POST_VALUE = "post";
    public static final String COMMAND_TYPE_FINAL_VALUE = "final";
    public static final String SHOW_IMAGE_COMMAND_VALUE = "showimage";

    private PropertyChangeSupport changes;
    private String question;
    private final List<Answer> answers;
    private int points;
    private String postExecutionCommand;
    private String preExecutionCommand;
    private String finalExecutionCommand;
    private boolean solved;

    public Quest() {
        this.answers = new ArrayList<>();
        this.solved = false;
        this.changes = new PropertyChangeSupport(this);
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public String getQuestion() {
        return question;
    }

    public boolean hasPreExecutionCommand() {
        return preExecutionCommand != null;
    }

    public boolean hasPostExecutionCommand() {
        return postExecutionCommand != null;
    }

    public boolean hasFinalExecutionCommand() {
        return finalExecutionCommand != null;
    }

    public String getPreExecutionCommand() {
        return preExecutionCommand;
    }

    public String getPostExecutionCommand() {
        return postExecutionCommand;
    }

    public String getFinalExecutionCommand() {
        return finalExecutionCommand;
    }

    public int getPoints() {
        return points;
    }

    protected Quest parse(Element element) {
        try {
            points = Integer.parseInt(element.getAttributeValue(POINTS_ATTRIBUTE));
            question = element.getAttributeValue(QUESTION_ATTRIBUTE);
            
            Elements executionElements = element.getChildElements(EXECUTION_ELEMENT);
            for (int i = 0; i < executionElements.size(); i++) {
                if (executionElements.get(i).getAttributeValue(COMMAND_TYPE_ATTRIBUTE).equals(COMMAND_TYPE_PRE_VALUE)) {
                    preExecutionCommand = GameVariableStore.resolveVariables(executionElements.get(i).getAttributeValue(COMMAND_ATTRIBUTE));
                } else if (executionElements.get(i).getAttributeValue(COMMAND_TYPE_ATTRIBUTE).equals(COMMAND_TYPE_POST_VALUE)) {
                    postExecutionCommand = GameVariableStore.resolveVariables(executionElements.get(i).getAttributeValue(COMMAND_ATTRIBUTE));
                } else if (executionElements.get(i).getAttributeValue(COMMAND_TYPE_ATTRIBUTE).equals(COMMAND_TYPE_FINAL_VALUE)) {
                    finalExecutionCommand = GameVariableStore.resolveVariables(executionElements.get(i).getAttributeValue(COMMAND_ATTRIBUTE));
                } else {
                    LOGGER.warn("Unknown command type found! prepare as pre command.");
                    preExecutionCommand = GameVariableStore.resolveVariables(executionElements.get(i).getAttributeValue(COMMAND_ATTRIBUTE));
                }
            }

            Elements answerElements = element.getChildElements(ANSWER_ELEMENT);
            for (int i = 0; i < answerElements.size(); ++i) {
                answers.add(new Answer(answerElements.get(i).getAttributeValue(TEXT_ATTRIBUTE), i == IS_FIRST_ELEMENT));
            }
            Collections.shuffle(answers);
            return this;
        } catch (Exception ex) {
            ExceptionPrinter.printHistory("Could not parse " + getClass().getName() + "out of [" + element.getNamespaceURI() + "]!", ex, LOGGER);
            throw new RuntimeException(ex);
        }
    }

    public void setActive() {
        changes.firePropertyChange(QUEST_ACTIVE, null, null);
    }

    public void setSolved(boolean solved) {
        changes.firePropertyChange(QUEST_SOLVED, null, null);
        this.solved = solved;
    }

    public boolean isSolved() {
        return solved;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changes.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changes.removePropertyChangeListener(listener);
    }
}
