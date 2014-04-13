import net.pythoud.passwords.CharacterSets
import net.pythoud.passwords.PasswordChecker

class PasswordCheckerTest extends GroovyTestCase {

    void testBasicOK() {
        PasswordChecker checker = PasswordChecker.getBasicPasswordChecker()
        assertTrue(checker.check('Test1234'))
        assertTrue(checker.check('jfofdoDslAA8ljl'))
        assertTrue(checker.check('djljladSjSjfklSjklEjk34sDljSO9de'))
    }

    void testBasicFailTooShort() {
        PasswordChecker checker = PasswordChecker.getBasicPasswordChecker()
        assertFalse(checker.check('Test7'))
        assertTrue(checker.isTooShort())
        assertFalse(checker.isTooLong())
        assertFalse(checker.containsIllegalCharacter())
        assertFalse(checker.isCharacterTypeMissing())

        assertFalse(checker.check('I2e4567'))
    }

    void testBasicFailTooLong() {
        PasswordChecker checker = PasswordChecker.getBasicPasswordChecker()
        assertFalse(checker.check('djljladSjSjfklSjklEjk34sDljSO9defjaksgjklasdfj'))
        assertFalse(checker.isTooShort())
        assertTrue(checker.isTooLong())
        assertFalse(checker.containsIllegalCharacter())
        assertFalse(checker.isCharacterTypeMissing())

        assertFalse(checker.check('ThirthyThreeCharactersLongN0Goodx'))
    }

    void testBasicFailIllegalChars() {
        PasswordChecker checker = PasswordChecker.getBasicPasswordChecker()
        assertFalse(checker.check('Test#1234'))
        assertFalse(checker.isTooShort())
        assertFalse(checker.isTooLong())
        assertTrue(checker.containsIllegalCharacter())
        assertFalse(checker.isCharacterTypeMissing())

        assertFalse(checker.check('Test#12@34'))
        assertFalse(checker.check('!jfofdoDslAA8ljl'))
        assertFalse(checker.check('jfofdoDslAA8ljl?'))
        assertFalse(checker.check('!jfofdoDslAA8ljl?'))
        assertFalse(checker.check('!jfofdo#DslAA8ljl?'))
    }

    void testBasicMissingChars() {
        PasswordChecker checker = PasswordChecker.getBasicPasswordChecker()
        assertFalse(checker.check('Testabcde'))
        assertFalse(checker.isTooShort())
        assertFalse(checker.isTooLong())
        assertFalse(checker.containsIllegalCharacter())
        assertTrue(checker.isCharacterTypeMissing())
    }

    PasswordChecker getComplexChecker() {
        PasswordChecker.Factory factory = PasswordChecker.getFactory()
        factory.setMinMaxCharCount(16, 32)
        factory.addCharSet(CharacterSets.LOWER_CASES, 1).addCharSet(CharacterSets.UPPER_CASES, 1)
        factory.addCharSet(CharacterSets.DIGITS, 1).addCharSet(CharacterSets.EASY_SYMBOLS, 1)
        return factory.create()
    }

    void testComplexOK() {
        PasswordChecker checker = getComplexChecker()
        assertTrue(checker.check('jda;fAklVdkE3dk6as'))
        assertTrue(checker.check('jda;fAklVdkE3dk6'))
        assertTrue(checker.check('djljladS@SjfklSjk!Ejk34sDljSO9de'))
    }

    void testComplexFailTooShort() {
        PasswordChecker checker = getComplexChecker()
        assertFalse(checker.check('T@st7'))
        assertTrue(checker.isTooShort())
        assertFalse(checker.isTooLong())
        assertFalse(checker.containsIllegalCharacter())
        assertFalse(checker.isCharacterTypeMissing())

        assertFalse(checker.check('I2e456789@12345'))
    }

    void testComplexFailTooLong() {
        PasswordChecker checker = getComplexChecker()
        assertFalse(checker.check('djljladSjSjfklSjklEjk34@sDljSO9defjaksgjklasdfj?'))
        assertFalse(checker.isTooShort())
        assertTrue(checker.isTooLong())
        assertFalse(checker.containsIllegalCharacter())
        assertFalse(checker.isCharacterTypeMissing())

        assertFalse(checker.check('ThirthyThreeCharactersLongN0Good#'))
    }

    void testComplexFailIllegalChars() {
        PasswordChecker checker = getComplexChecker()
        assertFalse(checker.check('Test#1234@12345_67890'))
        assertFalse(checker.isTooShort())
        assertFalse(checker.isTooLong())
        assertTrue(checker.containsIllegalCharacter())
        assertFalse(checker.isCharacterTypeMissing())
    }

    void testComplexMissingChars() {
        PasswordChecker checker = getComplexChecker()
        assertFalse(checker.check('Test@bcdefghiklmno'))
        assertFalse(checker.isTooShort())
        assertFalse(checker.isTooLong())
        assertFalse(checker.containsIllegalCharacter())
        assertTrue(checker.isCharacterTypeMissing())
    }
}