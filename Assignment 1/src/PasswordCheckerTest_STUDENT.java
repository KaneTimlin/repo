
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import exceptions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.function.Executable;

import javax.swing.text.PlainDocument;

/**
 * STUDENT tests for the methods of PasswordChecker
 * @author Kane Timlin
 *
 */
public class PasswordCheckerTest_STUDENT {



	ArrayList<String> validPasswordsArray;
	ArrayList<String> invalidPasswordsArray;
	String password = "T3x!";
	String plainText = "Text";
	String longPassword = "thisIsAVeryLongPassword";
	String allCaps = "APPLES";
	String noUpperCase = "apples";
	String weakPassword = "weaKPas";
	String charsInSequence = "dddft33!";



	@Before
	public void setUp() throws Exception {

		String[] invalidPasswords = {"Pass", "passwor@d123", "28&8888fDb", "eg33FddRf", "*#&$Dsfwf"};
		String[] validPasswords = {"Password@123", "cheeSE3#", "eeDjjes!3$", "dkju*89^D"};
		validPasswordsArray = new ArrayList<>();
		invalidPasswordsArray = new ArrayList<>();
		validPasswordsArray.addAll(Arrays.asList(validPasswords));
		invalidPasswordsArray.addAll(Arrays.asList(invalidPasswords));
		
	}

	@After
	public void tearDown() throws Exception {
		invalidPasswordsArray = null;
		validPasswordsArray = null;
	}

	/**
	 * Test if the password is less than 6 characters long.
	 * This test should throw a LengthException for second case.
	 */
	@Test
	public void testIsValidPasswordTooShort()
	{
		try {
			assertTrue(PasswordCheckerUtility.isValidLength(longPassword));
		} catch (LengthException e) {
			e.printStackTrace();
		}
		Throwable exception = assertThrows(LengthException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				PasswordCheckerUtility.isValidLength(password);
			}
		});

		assertEquals("The password must be at least 6 characters long", exception.getMessage());
	}
	
	/**
	 * Test if the password has at least one uppercase alpha character
	 * This test should throw a NoUpperAlphaException for second case
	 */
	@Test
	public void testIsValidPasswordNoUpperAlpha()
	{
		try {
			assertTrue(PasswordCheckerUtility.hasUpperAlpha(plainText));
		} catch (NoUpperAlphaException e) {
			e.printStackTrace();
		}

		Throwable exception = assertThrows(NoUpperAlphaException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				PasswordCheckerUtility.hasUpperAlpha(noUpperCase);
			}
		});

		assertEquals("The password must contain at least one uppercase alphabetic character", exception.getMessage());
	}
	
	/**
	 * Test if the password has at least one lowercase alpha character
	 * This test should throw a NoLowerAlphaException for second case
	 */
	@Test
	public void testIsValidPasswordNoLowerAlpha()
	{
		try {
			assertTrue(PasswordCheckerUtility.hasLowerAlpha(plainText));
		} catch (NoLowerAlphaException e) {
			e.printStackTrace();
		}

		Throwable exception = assertThrows(NoLowerAlphaException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				PasswordCheckerUtility.hasLowerAlpha(allCaps);
			}
		});

		assertEquals("The password must contain at least one lower case alphabetic character", exception.getMessage());
	}
	/**
	 * Test if the password has more than 2 of the same character in sequence
	 * This test should throw a InvalidSequenceException for second case
	 */
	@Test
	public void testIsWeakPassword()
	{
		try {
			assertFalse(PasswordCheckerUtility.isWeakPassword(longPassword));
		} catch (WeakPasswordException e) {
			e.printStackTrace();
		}

		Throwable exception = assertThrows(WeakPasswordException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				PasswordCheckerUtility.isWeakPassword(weakPassword);
			}
		});

		assertEquals("The password is too weak", exception.getMessage());
	}
	
	/**
	 * Test if the password has more than 2 of the same character in sequence
	 * This test should throw a InvalidSequenceException for second case
	 */
	@Test
	public void testIsValidPasswordInvalidSequence()
	{
		try {
			assertTrue(PasswordCheckerUtility.hasSameCharInSequence(longPassword));
		} catch (InvalidSequenceException e) {
			e.printStackTrace();
		}

		Throwable exception = assertThrows(InvalidSequenceException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				PasswordCheckerUtility.hasSameCharInSequence(charsInSequence);
			}
		});

		assertEquals("The password cannot contain more than two of the same character in sequence", exception.getMessage());
	}
	
	/**
	 * Test if the password has at least one digit
	 * One test should throw a NoDigitException
	 */
	@Test
	public void testIsValidPasswordNoDigit()
	{
		try {
			assertTrue(PasswordCheckerUtility.hasDigit(password));
		} catch (NoDigitException e) {
			e.printStackTrace();
		}

		Throwable exception = assertThrows(NoDigitException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				PasswordCheckerUtility.hasDigit(plainText);
			}
		});

		assertEquals("The password must contain at least one digit", exception.getMessage());
	}
	
	/**
	 * Test correct passwords
	 * This test should not throw an exception
	 */
	@Test
	public void testIsValidPasswordSuccessful()
	{
		ArrayList<String> results;
		results = PasswordCheckerUtility.getInvalidPasswords(validPasswordsArray);
		assertTrue(results.isEmpty());
	}
	
	/**
	 * Test the invalidPasswords method
	 * Check the results of the ArrayList of Strings returned by the validPasswords method
	 */
	@Test
	public void testInvalidPasswords() {
		ArrayList<String> results;
		results = PasswordCheckerUtility.getInvalidPasswords(invalidPasswordsArray);

		assertEquals("Pass -> The password must be at least 6 characters long", results.get(0));
		assertEquals("passwor@d123 -> The password must contain at least one uppercase alphabetic character", results.get(1));
		assertEquals("28&8888fDb -> The password cannot contain more than two of the same character in sequence", results.get(2));
		assertEquals("eg33FddRf -> The password must contain at least one special character", results.get(3));
		assertEquals("*#&$Dsfwf -> The password must contain at least one digit", results.get(4));

	}
	
}
