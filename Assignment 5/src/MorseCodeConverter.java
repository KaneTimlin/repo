import javax.crypto.AEADBadTagException;
import java.io.*;
import java.util.ArrayList;

public class MorseCodeConverter {

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
