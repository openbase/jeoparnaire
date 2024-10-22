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
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Nodes;
import org.openbase.jul.exception.printer.ExceptionPrinter;
import org.openbase.jul.processing.xml.processing.XMLProcessor;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class GameData {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(GameData.class);
    
	public static final String CATEGORY_XPATH = "/GameData/Category";
	private final List<Category> categorys;

	public GameData() {
		this.categorys = new ArrayList<>();
	}

	public List<Category> getCategorys() {
		return categorys;
	}

	public void parse(final Document configDocument) {
		try {
			Nodes categoryNodes = XMLProcessor.extractNodesByXpath(CATEGORY_XPATH, configDocument, XMLProcessor.NumberOfNodes.AT_LEAST_ONE);
			for (int i = 0; i < categoryNodes.size(); i++) {
				categorys.add(new Category().parse((Element) categoryNodes.get(i)));
			}
		} catch (Exception ex) {
			ExceptionPrinter.printHistory("Could not parse " + getClass().getName() + "out of [" + configDocument.getBaseURI() + "]!", ex, LOGGER);
			throw new RuntimeException(ex);
		}
	}

	public boolean isQuestAvailable() {
		for(Category category : categorys) {
			for(Quest quest : category.getQuests()) {
				if(quest.isSolved()) {
					return true;
				}
			}
		}
		return false;
	}
}
