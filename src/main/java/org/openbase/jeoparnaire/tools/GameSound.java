package org.openbase.jeoparnaire.tools;

/*-
 * #%L
 * Jeoparnaire
 * %%
 * Copyright (C) 2011 - 2018 openbase.org
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

import java.io.File;
import org.openbase.jul.audio.AudioDataImpl;
import org.openbase.jul.audio.AudioPlayer;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.printer.ExceptionPrinter;

/**
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public enum GameSound {

    /*
	 * - Agent explodier
	 * - Muttaschiff explodiert
	 * - Laser
	 * - Mine Legen
	 * - Marker setzen
	 * - Hilferuf
	 * - (tanken)
	 * - Unterstützen
	 * Resource abliefern	 */
    RightVote("sound/right-vote.wav"),
    WrongVote("sound/wrong-vote.wav"),
    StartVoting("sound/start-voting.wav");

    private final AudioDataImpl audioData;

    private static final AudioPlayer AUDIO_SERVER = new AudioPlayer(40);

    private GameSound(final String uri) {
        AudioDataImpl tmpData = null;
        try {
            tmpData = new AudioDataImpl(new File(ClassLoader.getSystemResource(uri).getFile()));
        } catch (Exception ex) {
            ExceptionPrinter.printHistory(new CouldNotPerformException("Could not load Soundfile[" + uri + "] of " + name(), ex), System.err);
        }
        this.audioData = tmpData;
    }

    public void play() {
        if (audioData == null) {
            return;
        }
        AUDIO_SERVER.playAudio(audioData);
    }
    
    public void stop() {
        if (audioData == null) {
            return;
        }
        AUDIO_SERVER.playAudio(audioData);
    }
}
