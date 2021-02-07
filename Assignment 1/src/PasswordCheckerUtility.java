import exceptions.*;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kane Timlin
 */

public class PasswordCheckerUtility {

    /**
     *  This method compares whether the two passwords given are the same
     * @param pass A password
     * @param confirmPass (hopefully) the same password
     * @throws UnmatchedException Thrown if passwords do no match
     */
    public static void comparePasswords(String pass, String confirmPass) throws UnmatchedException {
        if (!pass.equals(confirmPass)) {
            throw new UnmatchedException();
        }
    }

    /**
     * This method compares whether the two passwords given are the same
     * @param pass A password
     * @param confirmPass (hopefully) the same password
     * @return true if passwords are the same, false if different
     */
    public static boolean comparePasswordsWithReturn(String pass, String confirmPass) {
        return pass.equals(confirmPass);
    }

    /**
     * Returns an arraylist of passwords and the reason that they are invalid
     * @param p the arraylist of passwords to be checked
     * @return the reasons that the passwords are invalid (empty if all passwords are valid)
     */
    public static ArrayList<String> getInvalidPasswords(ArrayList<String> p) {
        ArrayList<String> invalidPass = new ArrayList<>();
        for (String pass: p) {
            try {
                isValidPassword(pass);
            } catch (Exception e) {
                invalidPass.add(pass + " -> " + e.getMessage());
            }
        }
        return invalidPass;
    }

    /**
     * Checks if the given password has between 6 and 9 characters, inclusive
     * @param p the password to be checked
     * @return true if between 6 and 9 characters, false otherwise
     */
    public static boolean hasBetweenSixAndNineChars(String p) {
        return (p.length() >= 6 && p.length() <= 9);
    }

    /**
     * Checks if the given password has a digit in it
     * @param p the password to be checked
     * @return true if has digit
     * @throws NoDigitException Thrown if no digit
     */
    public static boolean hasDigit(String p) throws NoDigitException {

        for (int i = 0; i < p.length(); i++) {
            if (p.charAt(i) >= '0' && p.charAt(i) <= '9') {
                return true;
            }
        }
        throw new NoDigitException();

    }

    /**
     * Checks if the password has a lowercase character in it
     * @param p the password to be checked
     * @return true if contains lowercase character
     * @throws NoLowerAlphaException thrown if does not contain lowercase character
     */
    public static boolean hasLowerAlpha(String p) throws NoLowerAlphaException {
        for (int i = 0; i < p.length(); i++) {
            if (p.charAt(i) >= 'a' && p.charAt(i) <= 'z') {
                return true;
            }
        }
        throw new NoLowerAlphaException();
    }

    /**
     * Checks if the password has more than 2 of the same character in a sequence
     * @param p the password to be checked
     * @return true if does not have the same char in sequence
     * @throws InvalidSequenceException Thrown if does have same char in sequence
     */
    public static boolean hasSameCharInSequence(String p) throws InvalidSequenceException {
        int tally = 0;
        for (int i = 1; i < p.length(); i++) {
            if (p.charAt(i-1) == p.charAt(i)) {
                tally++;
            } else {
                tally = 0;
            }
            if (tally >= 2) {
                throw new InvalidSequenceException();
            }
        }
        return true;

    }

    /**
     * Checks if the password has a special character in it or not
     * @param p the password to be checked
     * @return true if contains special char
     * @throws NoSpecialCharacterException thrown if does not contain special char
     */
    public static boolean hasSpecialChar(String p) throws NoSpecialCharacterException {
        boolean hasChar;
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
        Matcher matcher = pattern.matcher(p);
        hasChar = !matcher.matches();
        if (hasChar) {
            return true;
        } else {
            throw new NoSpecialCharacterException();
        }

    }

    /**
     * Checks if the password has an uppercase character in it
     * @param p the password to be checked
     * @return true if contains uppercase character
     * @throws NoUpperAlphaException thrown if does not contain uppercase character
     */
    public static boolean hasUpperAlpha(String p) throws NoUpperAlphaException {
        for (int i = 0; i < p.length(); i++) {
            if (p.charAt(i) >= 'A' && p.charAt(i) <= 'Z') {
                return true;
            }
        }
        throw new NoUpperAlphaException();
    }

    /**
     * Checks if the password is a valid length (greater than 6 characters, inclusive)
     * @param p the password to be checked
     * @return true if long enough
     * @throws LengthException thrown if not long enough
     */
    public static boolean isValidLength(String p) throws LengthException {
        if (p.length() >= 6) {
            return true;
        } else {
            throw new LengthException();
        }
    }

    /**
     * Checks if the password is valid
     * @param p the password to be checked
     * @return true if valid password
     * @throws LengthException thrown if password is too short
     * @throws NoUpperAlphaException thrown if password does not contain an uppercase character
     * @throws NoLowerAlphaException thrown if password does not contain a lowercase character
     * @throws NoDigitException thrown if password does not have a digit
     * @throws NoSpecialCharacterException thrown if password does not have a special character
     * @throws InvalidSequenceException thrown if password has 3 or more of the same character in sequence
     */
    public static boolean isValidPassword(String p) throws LengthException, NoUpperAlphaException,
            NoLowerAlphaException, NoDigitException, NoSpecialCharacterException, InvalidSequenceException {

        return isValidLength(p) && hasUpperAlpha(p) && hasLowerAlpha(p) &&
                hasDigit(p) && hasSpecialChar(p) && hasSameCharInSequence(p);

    }

    /**
     * Checks if the password is weak (between 6 and 9 characters)
     * @param p The password to be checked
     * @return true if password is not weak
     * @throws WeakPasswordException thrown if password is weak
     */
    public static boolean isWeakPassword(String p) throws WeakPasswordException {
        if (hasBetweenSixAndNineChars(p)) {
            throw new WeakPasswordException();
        } else {
            return false;
        }
    }

}
