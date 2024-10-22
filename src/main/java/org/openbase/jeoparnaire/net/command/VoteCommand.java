package org.openbase.jeoparnaire.net.command;

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

import org.openbase.jul.communication.tcp.execution.command.AbstractCommand;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class VoteCommand extends AbstractCommand {

	public enum Vote {

		A(0), B(1), C(2), D(3);
		public final int index;

		Vote(int index) {
			this.index = index;
		}
		
	};
	private Vote vote;

    /**
	 * JSON Constructor
	 */
    public VoteCommand() {
    }
    
	public VoteCommand(Vote vote) {
		super(false);
		this.vote = vote;
	}

	public Vote getVote() {
		return vote;
	}
}
