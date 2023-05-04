package org.me.leo_s.particleletterns.components.text;

import org.bukkit.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.json.simple.JSONObject;
import org.me.leo_s.particleletterns.ParticleLetters;
import org.me.leo_s.particleletterns.components.builders.maths.ColorValue;
import org.me.leo_s.particleletterns.components.builders.maths.MathsUtils;
import org.me.leo_s.particleletterns.components.exceptions.TextFormattedInvalid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"unchecked", "UnusedReturnValue"})
public class TextParticle {

    private String text;
    private List<Letter> letters;
    private final int timePerLetter;
    private Color color;
    private final double lengthLines;
    private final double spaceLetters;
    private World world;
    private final List<Character> lettersNeedInvert = List.of('A', 'B', 'D', 'F', 'G', 'J', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'T', 'U', 'V', 'Y', 'W', 'X', '1' , '2', '4', '5', '6', '7', '9');
    private List<Location>  locations;
    private BukkitRunnable task;
    private boolean infinite = false;

    /**
     * @param text The text to be generated
     * @param timePerLetter The time it takes to display the text, in seconds
     * @param color The color of the text
     * @param lengthLines The length of the lines that make up the text
     * @param spaceLetters The size that each space will have if the text has one
     */
    public TextParticle(String text, int timePerLetter, Color color, double lengthLines, double spaceLetters) {
        this.text = text;
        infinite = timePerLetter == -1;
        this.timePerLetter = timePerLetter;
        this.color = color;
        this.lengthLines = lengthLines;
        this.spaceLetters = spaceLetters;
    }

    public TextParticle(TextSession textSession){
        this.text = textSession.getText().toUpperCase();
        this.timePerLetter = textSession.getTimePerLetter();
        this.color = textSession.getColor();
        this.lengthLines = textSession.getLengthLines();
        this.spaceLetters = textSession.getSpaceLetters();
    }

    /**
     * @param text The text to be generated
     * @param timePerLetter The time it takes to display the text, in seconds
     * @param lengthLines The length of the lines that make up the text
     * @param spaceLetters The size that each space will have if the text has one
     * @throws TextFormattedInvalid If the text not contains '&' character
     */
    public TextParticle(String text, int timePerLetter, double lengthLines, double spaceLetters) throws TextFormattedInvalid {
        if (!text.matches(".*&.*")) throw new TextFormattedInvalid(MathsUtils.color("&8[&cParticleLetters&8] &7The text must be formatted with the character &f&"));
        List<Letter> letters = new ArrayList<>();
        char letterChar;
        Color currentColor = Color.BLACK;
        Letter letter;
        for (int i = 0; i < text.length(); i++) {
            letterChar = text.charAt(i);
            if (letterChar == ' ') {
                i++;
                continue;
            }
            if (letterChar == '&') {
                if (i + 1 < text.length()) {
                    char colorChar = text.charAt(i + 1);
                    char l = text.charAt(i + 2);
                    Color color = ColorValue.fromChar(colorChar);
                    if (color != null) {
                        i++;
                        currentColor = color;
                    }
                    letter = new Letter(l, currentColor);
                    letters.add(letter);
                }
            }
        }

        this.letters = letters;
        infinite = timePerLetter == -1;
        this.timePerLetter = timePerLetter;
        this.lengthLines = lengthLines;
        this.spaceLetters = spaceLetters;
    }

    public TextParticle generate(Location origin){
        this.world = origin.getWorld();
        this.locations = getLocations(origin);
        if(task != null) task.cancel();

        String txt = text != null ? text.replace(" ", "") :
                letters.stream()
                        .map(Letter::getLetter)
                        .filter(letter -> letter != ' ')
                        .map(String::valueOf)
                        .collect(Collectors.joining());
        task = new BukkitRunnable() {
            int timePerLetter = TextParticle.this.timePerLetter;
            @Override
            public void run() {
                if (timePerLetter <= 0 && !infinite) {
                    cancel();
                    task = null;
                    return;
                }
                for(Location location : locations){
                    int index = locations.indexOf(location);
                    char letter = txt.charAt(index);
                    generateType(location, letter, index);
                }
                timePerLetter--;
            }
        };
        task.runTaskTimer(ParticleLetters.getInstance(), 0L, 20);
        return this;
    }

    /**
     * @return Returns the location that each letter will have including if it has a space next to it
     */
    public List<Location> getLocations(Location origin) {
        try {
            List<Location> locations = new ArrayList<>();
            double totalWidth;
            if(text != null) totalWidth = 1 + (text.replaceAll(" ", "").length() * 5 + (spaceLetters * text.length()));
            else totalWidth = 1 + (letters.stream().filter(a -> a.getLetter() != ' ').count() * 5 + (spaceLetters * letters.size()));

            double leftMost = totalWidth / -2;
            int separation = 0;
            for (int i = 0; i < (text != null ? text.length() : letters.size()); i++) {
                char letter = text != null ? text.charAt(i) : letters.get(i).getLetter();
                if (letter == ' ') {
                    separation += this.spaceLetters;
                    continue;
                }
                separation += this.spaceLetters;
                double x = leftMost + separation;
                locations.add(new Location(world, origin.getX() + x, origin.getY(), origin.getZ()));
            }
            return locations;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * @param origin The point of origin where the letter will begin to be generated
     * @param letter The letter to be generated
     * @param index It is used to determine the position of the letter in the word.
     */
    public void generateType(Location origin, char letter, int index){
        try {
            byte[][] pattern = lettersNeedInvert.contains(letter) ? invertLetter(letter) : getLetter(letter);
            Vector offset = new Vector(spaceLetters * index, 0, 0);
            Vector up = new Vector(0, lengthLines, 0);
            origin.clone().add(offset);
            Location location = new Location(world, origin.getX(), origin.getY(), origin.getZ()).add(offset).subtract(0.5, 0, 0.5);

            for (int i = 0; i < pattern.length; i++) {
                for (int j = 0; j < pattern[i].length; j++) {
                    if (pattern[i][j] == 1) {
                        Location particleLoc = new Location(world, location.getX(), location.getY(), location.getZ()).add(up.clone().multiply(i)).add(new Vector(j, 0, 0));
                        world.spawnParticle(Particle.REDSTONE, particleLoc, 3, 0, 0, 0, 1, new Particle.DustOptions(text != null ? color : letters.get(index).getColor(), 3));
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void cancelTask() {
        if (task != null) {
            task.cancel();
        }
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        if(text != null) {
            json.put("text", text);
            json.put("color", MathsUtils.colorToJson(color));
        }else{
            String letters = this.letters.stream().map(letter -> "&" + ColorValue.colorToChar(letter.getColor()) + letter.getLetter()).collect(Collectors.joining());
            json.put("text", letters);
        }
        json.put("timePerLetter", timePerLetter);
        json.put("lengthLines", lengthLines);
        json.put("spaceLetters", spaceLetters);
        return json;
    }

    public static class Letter{

        private final Character letter;
        private final Color color;

        public Letter(char letter, Color color){
            this.letter = letter;
            this.color = color;
        }

        public Character getLetter() {
            return letter;
        }

        public Color getColor() {
            return color;
        }
    }

    /**
     * Inverts the matrix vertically.
     *
     * @param letter the letter to invert
     * @return the inverted matrix
     */
    public static byte[][] invertLetter(char letter) {
        byte[][] matrix = getLetter(letter);
        byte[][] invertedMatrix = new byte[matrix.length][matrix[0].length];
        for (int row = 0; row < matrix.length; row++) {
            System.arraycopy(matrix[matrix.length - 1 - row], 0, invertedMatrix[row], 0, matrix[0].length);
        }
        return invertedMatrix;
    }
    /**
     * @param letter The letter to be inverted
     * @return Returns the pattern of the letter
     */
    public static byte[][] getLetter(char letter) {
        return switch (letter) {
            case 'A' -> new byte[][]{
                    {0, 1, 1, 1, 0},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 1, 1, 1, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1}
            };
            case 'B' -> new byte[][]{
                    {1, 1, 1, 0, 0},
                    {1, 0, 0, 1, 0},
                    {1, 0, 0, 1, 0},
                    {1, 1, 1, 0, 0},
                    {1, 0, 0, 1, 0},
                    {1, 0, 0, 1, 0},
                    {1, 1, 1, 0, 0}
            };
            case 'C' -> new byte[][]{
                    {0, 1, 1, 1, 0},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0},
                    {1, 0, 0, 0, 0},
                    {1, 0, 0, 0, 0},
                    {1, 0, 0, 0, 1},
                    {0, 1, 1, 1, 0}
            };
            case 'D' -> new byte[][]{
                    {1, 1, 1, 0, 0},
                    {1, 0, 0, 1, 0},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 1, 0},
                    {1, 1, 1, 0, 0}
            };
            case 'E' -> new byte[][]{
                    {1, 1, 1, 1, 0},
                    {1, 0, 0, 0, 0},
                    {1, 0, 0, 0, 0},
                    {1, 1, 1, 0, 0},
                    {1, 0, 0, 0, 0},
                    {1, 0, 0, 0, 0},
                    {1, 1, 1, 1, 0}
            };
            case 'F' -> new byte[][]{
                    {1, 1, 1, 1, 0},
                    {1, 0, 0, 0, 0},
                    {1, 0, 0, 0, 0},
                    {1, 1, 1, 0, 0},
                    {1, 0, 0, 0, 0},
                    {1, 0, 0, 0, 0},
                    {1, 0, 0, 0, 0}
            };
            case 'G' -> new byte[][]{
                    {0, 1, 1, 1, 0},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0},
                    {1, 0, 1, 1, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {0, 1, 1, 1, 0}
            };
            case 'H' -> new byte[][]{
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 1, 1, 1, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1}
            };
            case 'I' -> new byte[][]{
                    {1, 1, 1, 1 ,1},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0},
                    {1, 1, 1, 1, 1}
            };
            case 'J' -> new byte[][]{
                    {0, 0, 0, 0, 1},
                    {0, 0, 0, 0, 1},
                    {0, 0, 0, 0, 1},
                    {0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {0, 1, 1, 1, 0}
            };
            case 'K' -> new byte[][]{
                    {1, 0, 0, 1, 0},
                    {1, 0, 1, 0, 0},
                    {1, 1, 0, 0, 0},
                    {1, 1, 0, 0, 0},
                    {1, 0, 1, 0, 0},
                    {1, 0, 0, 1, 0},
                    {1, 0, 0, 0, 1}
            };
            case 'L' -> new byte[][]{
                    {1, 0, 0, 0, 0},
                    {1, 0, 0, 0, 0},
                    {1, 0, 0, 0, 0},
                    {1, 0, 0, 0, 0},
                    {1, 0, 0, 0, 0},
                    {1, 0, 0, 0, 0},
                    {1, 1, 1, 1, 1}
            };
            case 'M' -> new byte[][]{
                    {1, 0, 0, 0, 1},
                    {1, 1, 0, 1, 1},
                    {1, 0, 1, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1}
            };
            case 'N' -> new byte[][]{
                    {1, 0, 0, 0, 1},
                    {1, 1, 0, 0, 1},
                    {1, 0, 1, 0, 1},
                    {1, 0, 0, 1, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1}
            };
            case 'Ñ' -> new byte[][]{
                    {0, 0, 1, 0, 0},
                    {1, 0, 0, 0, 1},
                    {1, 1, 0, 0, 1},
                    {1, 0, 1, 0, 1},
                    {1, 0, 0, 1, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1}
            };
            case 'O' -> new byte[][]{
                    {0, 1, 1, 1, 0},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {0, 1, 1, 1, 0}
            };
            case 'P' -> new byte[][]{
                    {1, 1, 1, 1, 0},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 1, 1, 1, 0},
                    {1, 0, 0, 0, 0},
                    {1, 0, 0, 0, 0},
                    {1, 0, 0, 0, 0}
            };
            case 'Q' -> new byte[][]{
                    {0, 1, 1, 1, 0},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 1, 0, 1},
                    {1, 0, 0, 1, 0},
                    {0, 1, 1, 0, 1}
            };
            case 'R' -> new byte[][]{
                    {1, 1, 1, 1, 0},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 1, 1, 1, 0},
                    {1, 0, 0, 1, 0},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1}
            };
            case 'S' -> new byte[][]{
                    {0, 1, 1, 1, 0},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0},
                    {0, 1, 1, 1, 0},
                    {0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {0, 1, 1, 1, 0}
            };
            case 'T' -> new byte[][]{
                    {1, 1, 1, 1, 1},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0}
            };
            case 'U' -> new byte[][]{
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {0, 1, 1, 1, 0}
            };
            case 'V' -> new byte[][]{
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {0, 1, 0, 1, 0},
                    {0, 1, 0, 1, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0}
            };
            case 'W' -> new byte[][]{
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 1, 0, 1},
                    {1, 0, 1, 0, 1},
                    {1, 1, 0, 1, 1},
                    {0, 1, 0, 1, 0}
            };
            case 'X' -> new byte[][]{
                    {1, 0, 0, 0, 1},
                    {0, 1, 0, 1, 0},
                    {0, 0, 1, 0, 0},
                    {0, 1, 0, 1, 0},
                    {1, 0, 0, 0, 1},
                    {0, 1, 0, 1, 0},
                    {0, 0, 1, 0, 0}
            };
            case 'Y' -> new byte[][]{
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1},
                    {0, 1, 1, 1, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0}
            };
            case 'Z' -> new byte[][]{
                    {1, 1, 1, 1, 1},
                    {0, 0, 0, 0, 1},
                    {0, 0, 0, 1, 0},
                    {0, 0, 1, 0, 0},
                    {0, 1, 0, 0, 0},
                    {1, 0, 0, 0, 0},
                    {1, 1, 1, 1, 1}
            };
            case '0' -> new byte[][]{
                    {0,1,1,1,0},
                    {1,0,0,0,1},
                    {1,0,0,1,1},
                    {1,0,1,0,1},
                    {1,1,0,0,1},
                    {1,0,0,0,1},
                    {0,1,1,1,0}
            };
            case '1' -> new byte[][]{
                    {0,0,1,0,0},
                    {0,1,1,0,0},
                    {1,0,1,0,0},
                    {0,0,1,0,0},
                    {0,0,1,0,0},
                    {0,0,1,0,0},
                    {1,1,1,1,1}
            };
            case '2' -> new byte[][]{
                    {0,1,1,1,0},
                    {1,0,0,0,1},
                    {0,0,0,0,1},
                    {0,0,0,1,0},
                    {0,0,1,0,0},
                    {0,1,0,0,0},
                    {1,1,1,1,1}
            };
            case '3' -> new byte[][]{
                    {0,1,1,1,0},
                    {1,0,0,0,1},
                    {0,0,0,0,1},
                    {0,0,1,1,0},
                    {0,0,0,0,1},
                    {1,0,0,0,1},
                    {0,1,1,1,0}
            };
            case '4' -> new byte[][]{
                    {0,0,0,1,0},
                    {0,0,1,1,0},
                    {0,1,0,1,0},
                    {1,0,0,1,0},
                    {1,1,1,1,1},
                    {0,0,0,1,0},
                    {0,0,0,1,0}
            };
            case '5' -> new byte[][]{
                    {1,1,1,1,1},
                    {1,0,0,0,0},
                    {1,1,1,1,0},
                    {0,0,0,0,1},
                    {0,0,0,0,1},
                    {1,0,0,0,1},
                    {0,1,1,1,0}
            };
            case '6' -> new byte[][]{
                    {0,1,1,1,0},
                    {1,0,0,0,1},
                    {1,0,0,0,0},
                    {1,1,1,1,0},
                    {1,0,0,0,1},
                    {1,0,0,0,1},
                    {0,1,1,1,0}
            };
            case '7' -> new byte[][]{
                    {1,1,1,1,1},
                    {0,0,0,0,1},
                    {0,0,0,1,0},
                    {0,0,1,0,0},
                    {0,1,0,0,0},
                    {0,1,0,0,0},
                    {0,1,0,0,0}
            };
            case '8' -> new byte[][]{
                    {0,1,1,1,0},
                    {1,0,0,0,1},
                    {1,0,0,0,1},
                    {0,1,1,1,0},
                    {1,0,0,0,1},
                    {1,0,0,0,1},
                    {0,1,1,1,0}
            };
            case '9' -> new byte[][]{
                    {0,1,1,1,0},
                    {1,0,0,0,1},
                    {1,0,0,0,1},
                    {0,1,1,1,1},
                    {0,0,0,0,1},
                    {0,0,0,0,1},
                    {0,1,1,1,0}
            };
            default -> null;
        };
    }
}
