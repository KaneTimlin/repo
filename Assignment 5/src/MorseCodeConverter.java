import javax.crypto.AEADBadTagException;
import java.io.*;
import java.util.ArrayList;

/**
 * @author Kane Timlin
 */

public class MorseCodeConverter {
    /**
     * This method accepts a file containing as many lines as needed of morse code and translates it into english
     * It uses the other version of itself to accomplish this
     * @param file the file containing the morse code
     * @return the translated text
     * @throws FileNotFoundException if the file cannot be thrown
     */
    public static String convertToEnglish(File file) throws FileNotFoundException {
        StringBuilder str = new StringBuilder();
        String code;
        String temp;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while((temp = br.readLine()) != null) {
                str.append(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Something went wrong";
        }
        code = str.toString();
        return convertToEnglish(code);
    }

    /**
     * This method accepts a string of morse code and turns it into a string of english text
     * @param code the morse code to be translated
     * @return the english text
     */
    public static String convertToEnglish(String code) {
        StringBuilder output = new StringBuilder();
        MorseCodeTree<String> tree = new MorseCodeTree<>();
        String[] words = code.split("/");
        String[] letters;
        for (String word : words) {
            output.append(" ");
            letters = word.split(" ");
            for (String letter : letters) {
                output.append(tree.fetch(letter));
            }
        }
        return output.substring(1);
    }

    /**
     * This method turns the morse code tree into an arraylist, using LNR Traversal. It then returns the arraylist as a string
     * @return the contents of the arraylist
     */
    public static String printTree() {
        ArrayList<String> list;
        MorseCodeTree<String> tree = new MorseCodeTree<>();
        list = tree.toArrayList();
        StringBuilder out = new StringBuilder();
        for (String str : list) {
            out.append(" ");
            out.append(str);
        }
        return out.substring(1);
    }

}
