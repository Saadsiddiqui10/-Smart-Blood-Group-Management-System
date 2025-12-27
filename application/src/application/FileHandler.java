package application;
import application.Doner;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
	public static void saveDonor(Doner donor) {
        try {
            FileWriter fw = new FileWriter("donors.txt", true);
            fw.write(donor.toString() + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("File Error!");
        }
    }

}