import net.pythoud.passwords.PasswordChecker

class PasswordCheckerTest extends GroovyTestCase {

    void testBasicOK() {
        PasswordChecker checker = PasswordChecker.getBasicPasswordChecker()
        assertTrue(checker.check('Test1234'))
        assertTrue(checker.check('jfofdoDslAA8ljl'))
        assertTrue(checker.check('djljladSjSjfklSjklEjk34sDljSO9de'))
    }
}