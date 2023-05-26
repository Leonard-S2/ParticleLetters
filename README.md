**ParticleLetters API**

The ParticleLetters API is an API for the ParticleLetters Minecraft plugin, which allows you to generate letters in the form of particles. With this API, you can integrate the functionality of ParticleLetters into your own Minecraft plugins.

**How It Works**

The API consists of a ParticleLettersAPI class that allows you to generate letters in the form of particles at any location in the Minecraft world. To use the API, simply follow these steps:

Make sure the ParticleLetters plugin is installed and enabled on your Minecraft server.

Add the following line to the dependencies section of your pom.xml file:

**Copy code for Maven**

	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
  
	<dependency>
	    <groupId>com.github.Leonardo-shitp</groupId>
	    <artifactId>ParticleLetters</artifactId>
	    <version>Tag</version>
	</dependency>
  
**Copy code for Gradle**

  	repositories {
			...
			maven { url 'https://jitpack.io' }
		}
  
  	dependencies {
	        implementation 'com.github.Leonardo-shitp:ParticleLetters:Tag'
	}

**Copy code**

ParticleLettersAPI.generateText("Hello, world!", Location, 10, Color, 0.9, 2.9);
This example will generate the message "Hello world!" in the "Location" location. The color of the particles will be "Color", with a duration of 10 seconds, the size of the lines will be 0.9 and the size of the spaces between the letters will be 2.9 if the message contains any space.

**Methods**

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
     
**Credits**

The ParticleLetters plugin and its API were developed by [Leo_S]. If you have any questions, suggestions, or issues, please don't hesitate to contact me through [contactleonardsarica@gmail.com].

**License**

The ParticleLetters plugin and its API are distributed under the MIT license. See the LICENSE.md file for more information.
