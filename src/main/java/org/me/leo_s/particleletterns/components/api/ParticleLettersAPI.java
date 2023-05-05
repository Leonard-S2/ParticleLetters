package org.me.leo_s.particleletterns.components.api;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.me.leo_s.particleletterns.components.text.TextParticle;

@SuppressWarnings("unused")
public interface ParticleLettersAPI {
    /**
     * @param text The text to be generated
     * @param timePerLetter The time it takes to display the text, in seconds
     * @param lengthLines The length of the lines that make up the text
     * @param spaceLetters The size that each space will have if the text has one
     */
    void generateTextFromColorString(@NotNull String text, @NotNull Location origen, int timePerLetter, double lengthLines, double spaceLetters);
    void generateText(@NotNull TextParticle textParticle, @NotNull Location origen);
    /**
     * @param textParticle The text to be forced to stop
     */
    void forceStopText(@NotNull TextParticle textParticle);
    /**
     * @param name The name of the text to be obtained
     */
    TextParticle getTextParticle(@NotNull String name);
    /**
     * @param name The name of the text to be removed
     */
    void removeTextParticle(@NotNull String name);
    /**
     * @param letter The character of the letter
     */
    byte[][] getLetter(char letter);
    /**
     * @param letter The character of the letter
     */
    byte[][] invertLetter(char letter);
}
