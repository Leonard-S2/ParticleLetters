ParticleLetters API
The ParticleLetters API is an API for the ParticleLetters Minecraft plugin, which allows you to generate letters in the form of particles. With this API, you can integrate the functionality of ParticleLetters into your own Minecraft plugins.

How It Works
The API consists of a ParticleLettersAPI class that allows you to generate letters in the form of particles at any location in the Minecraft world. To use the API, simply follow these steps:

Make sure the ParticleLetters plugin is installed and enabled on your Minecraft server.

Add the following line to the dependencies section of your pom.xml file:

php
Copy code for Maven

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
  
php
Copy code for Gradle
  	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  	dependencies {
	        implementation 'com.github.Leonardo-shitp:ParticleLetters:Tag'
	}
  
Be sure to replace YourUsername with your GitHub username and 1.0.0 with the version of ParticleLetters you're using.

Import the ParticleLettersAPI class in your plugin:

java
Copy code
import com.github.YourUsername.particleletters.ParticleLettersAPI;
Use the ParticleLettersAPI.sendParticleLetters() method to generate the letters in the form of particles:

java
Copy code
ParticleLettersAPI.sendParticleLetters("Hello, world!", 0, 100, 0, 0.5f, 2, 0.2f);
This example will generate the message "Hello, world!" in the air, 100 blocks high over the coordinates X=0, Z=0. The particle size will be 0.5f, the duration will be 2 seconds, and there will be a 0.2f space between each letter.

Methods
ParticleLettersAPI.sendParticleLetters(String text, double x, double y, double z, float size, int duration, float space): generates letters in the form of particles at the specified coordinates.

text: the text that will be displayed in the form of particles.
x, y, z: the coordinates where the particles will be displayed.
size: the size of the particles.
duration: the duration in seconds of the particles.
space: the space between each letter.
Credits
The ParticleLetters plugin and its API were developed by [your name or nickname]. If you have any questions, suggestions, or issues, please don't hesitate to contact me through [your email address or website].

License
The ParticleLetters plugin and its API are distributed under the MIT license. See the LICENSE.md file for more information.
