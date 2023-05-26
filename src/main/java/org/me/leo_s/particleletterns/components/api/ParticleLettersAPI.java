package org.me.leo_s.particleletterns.components.api;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.me.leo_s.particleletterns.components.exceptions.TextFormattedInvalid;
import org.me.leo_s.particleletterns.components.text.TextParticle;

@SuppressWarnings("unused")
public interface ParticleLettersAPI {
    /**
     * @param text The text to be generated
     * @param timePerLetter The time it takes to display the text, in seconds
     * @param lengthLines The length of the lines that make up the text
     * @param spaceLetters The size that each space will have if the text has one
     * @apiNote The text will be generated in the location where the player is
     */
    void generateTextFromColorString(@NotNull String text, @NotNull Location origen, int timePerLetter, double lengthLines, double spaceLetters);

    /**
     * @param textParticle The object that contains the text to be generated
     * @param origen The location where the text will be generated
     * @apiNote The text will be generated in the location where the player is
     */
    void generateText(@NotNull TextParticle textParticle, @NotNull Location origen);
    /**
     * @param origin The location where the letter will be generated
     * @param pattern The pattern that the letter will have
     * @param lengthLines The length of the lines that make up the letter
     * @param isCustomPattern If the pattern is custom
     * @throws TextFormattedInvalid If the pattern is not valid
     * @apiNote If the pattern is not custom, TextFormattedInvalid will be raised if the pattern is invalid; otherwise, it will be generated normally.
     */
    void generateLetterOrCustomPattern(@NotNull Location origin, byte[] @NotNull [] pattern, double lengthLines, boolean isCustomPattern) throws TextFormattedInvalid;

    /**
     * @param textParticle The text to be forced to stop
     * @apiNote The text will be forced to stop in the location where the player is
     */
    void forceStopText(@NotNull TextParticle textParticle);
    /**
     * @param name The name of the text to be obtained
     * @return The text with the name
     * @apiNote The text will be obtained in the location where the player is
     */
    TextParticle getTextParticle(@NotNull String name);
    /**
     * @param name The name of the text to be removed
     * @apiNote The text will be removed in the location where the player is
     */
    void removeTextParticle(@NotNull String name);
    /**
     * @param letter The character of the letter
     * @return The pattern of the letter
     * @apiNote The letter will be obtained in the location where the player is
     */
    byte[][] getLetter(char letter);
    /**
     * @param letter The character of the letter
     * @return The inverted pattern of the letter
     * @apiNote The letter will be obtained in the location where the player is
     */
    byte[][] invertLetter(char letter);
    /**
     * @param name The name of the custom pattern to be obtained
     * @return The custom pattern with the name
     * @apiNote The custom pattern will be obtained in the location where the player is
     */
    byte[][] getCustomPattern(String name);
}
