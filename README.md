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

ParticleLettersAPI.ParticleLettersAPI(String text, Location loc, int timePerLetter, String color, double lengthLines, double spaceLetters): generates letters in the form of particles at the specified coordinates.

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
     
**Credits**

The ParticleLetters plugin and its API were developed by [Leo_S]. If you have any questions, suggestions, or issues, please don't hesitate to contact me through [contactleonardsarica@gmail.com].

**License**

The ParticleLetters plugin and its API are distributed under the MIT license. See the LICENSE.md file for more information.
