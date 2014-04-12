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
factory.setMinMaxChars(16, 16).setMainChars(PasswordMaker.LOWER_CASES + PasswordMaker.UPPER_CASES + PasswordMaker.DIGITS)
printTen('16 chars with digits', factory.create())

// 12-24 chars with numbers & symbols
factory.setMinMaxChars(12, 24).setMainChars(PasswordMaker.LOWER_CASES + PasswordMaker.UPPER_CASES + PasswordMaker.DIGITS + PasswordMaker.EASY_SYMBOLS)
printTen('12-24 chars with digits & symbols', factory.create())
