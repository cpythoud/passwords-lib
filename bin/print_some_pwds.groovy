import net.pythoud.passwords.CharacterSets
import net.pythoud.passwords.PasswordMaker

void printTen(String title, PasswordMaker pm) {
    println(title)
    (1..title.length()).each { print('-')}
    print("\n")
    (1..10).each {
        println(pm.getPassword())
    }
    println()
}

println()

// get default factory and print some 12 letters passwords
def factory = PasswordMaker.getFactory()
printTen('Default Factory', factory.create())

// 16 chars with numbers
factory.setMinMaxChars(16, 16).setMainChars(CharacterSets.LOWER_CASES + CharacterSets.UPPER_CASES + CharacterSets.DIGITS)
printTen('16 chars with digits', factory.create())

// 12-24 chars with numbers & symbols
factory.setMinMaxChars(12, 24).setMainChars(CharacterSets.LOWER_CASES + CharacterSets.UPPER_CASES + CharacterSets.DIGITS + CharacterSets.EASY_SYMBOLS)
printTen('12-24 chars with digits & symbols', factory.create())

// 8 chars, including one uppercase and 1 to 3 digits
factory.reset().setMinMaxChars(8, 8).setMainChars(CharacterSets.LOWER_CASES)
factory.addAltCharGroup(CharacterSets.UPPER_CASES, 1, 1).addAltCharGroup(CharacterSets.DIGITS, 1, 3)
printTen('8 chars, including one uppercase and 1 to 3 digits', factory.create())

// 12 chars, with 2 uppercase, 1 to 3 digits and 1 or 2 symbols
factory.reset().setMainChars(CharacterSets.LOWER_CASES)
factory.addAltCharGroup(CharacterSets.UPPER_CASES, 2, 2).addAltCharGroup(CharacterSets.DIGITS, 1, 3).addAltCharGroup(CharacterSets.EASY_SYMBOLS, 1, 2)
printTen('12 chars, with 2 uppercase, 1 to 3 digits and 1 or 2 symbols', factory.create())

