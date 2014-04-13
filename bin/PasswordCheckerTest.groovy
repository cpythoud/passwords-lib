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
}