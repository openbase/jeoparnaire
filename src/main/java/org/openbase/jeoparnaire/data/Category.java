package org.openbase.jeoparnaire.data;

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

import java.util.ArrayList;
import java.util.List;
import nu.xom.Element;
import nu.xom.Elements;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.printer.ExceptionPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class Category {

    private static final Logger LOGGER = LoggerFactory.getLogger(Category.class);

    public static final String NAME_ATTRIBUTE = "name";
    private String name;
    private List<Quest> quests;

    public Category() {
        this.quests = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Quest> getQuests() {
        return quests;
    }

    protected Category parse(final Element element) throws CouldNotPerformException {
        try {
            name = element.getAttributeValue(NAME_ATTRIBUTE);
            Elements questElements = element.getChildElements(Quest.QUEST_ELEMENT);
            for (int i = 0; i < questElements.size(); i++) {
                quests.add(new Quest().parse(questElements.get(i)));
            }
            return this;
        } catch (Exception ex) {
            throw ExceptionPrinter.printHistoryAndReturnThrowable(new CouldNotPerformException("Could not parse " + getClass().getName() + "out of [" + element.getNamespaceURI() + "]!", ex), LOGGER);
        }
    }
}
