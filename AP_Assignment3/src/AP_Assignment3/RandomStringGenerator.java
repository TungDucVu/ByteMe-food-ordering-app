package AP_Assignment3;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomStringGenerator implements Serializable{
	private static final long serialVersionUID = 4722139476997277212L;
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 10; 
    private static final Set<String> generatedStrings = new HashSet<>(); 
    private final Random random = new Random();

    public String generateRandomString() {
        String randomString;

        do {
            StringBuilder stringBuilder = new StringBuilder(LENGTH);
            for (int i = 0; i < LENGTH; i++) {
                int index = random.nextInt(CHARACTERS.length());
                stringBuilder.append(CHARACTERS.charAt(index));
            }
            randomString = stringBuilder.toString();
        } while (generatedStrings.contains(randomString)); // Repeat if the string is already generated

        generatedStrings.add(randomString); // Add to the set of generated strings
        return randomString;
    }
    
    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Order ID save to file " + filename);
        System.out.println();
    }
    
    // Load Course list from a file
    public static RandomStringGenerator loadFromFile(String filename) {
    	System.out.println("Loading file " + filename + " as ID.ser");
    	System.out.println();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (RandomStringGenerator) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new RandomStringGenerator(); // Return a new instance if file not found or error occurs
        }
    }
}
