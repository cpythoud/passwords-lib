package net.pythoud.passwords;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ...
 */
public class PasswordChecker {

    private final int minCharCount;
    private final int maxCharCount;
    private final List<String> charSets;
    private final List<Integer> minCountsInCharSets;

    private boolean tooShort;
    private boolean tooLong;
    private boolean illegalChar;
    private boolean missingChar;

    private PasswordChecker(final int minCharCount, final int maxCharCount, final List<String> charSets, final List<Integer> minCountsInCharSets) {
        this.minCharCount = minCharCount;
        this.maxCharCount = maxCharCount;
        this.charSets = charSets;
        this.minCountsInCharSets = minCountsInCharSets;
    }

    public static Factory getFactory() {
        return new Factory();
    }

    public static class Factory {

        private int minCharCount = 8;
        private int maxCharCount = 32;
        private List<String> charSets = new ArrayList<String>();
        private List<Integer> minCountsFromCharSets = new ArrayList<Integer>();

        public Factory setMinMaxCharCount(final int minCharCount, final int maxCharCount) {
            if (minCharCount < 1)
                throw new IllegalArgumentException("Minimum Character Count must be at least 1. Value received: " + minCharCount);
            if (maxCharCount < minCharCount)
                throw new IllegalArgumentException("Maximum Character Count cannot be smaller than Minimum Character Count: "
                        + maxCharCount + " < " + minCharCount);

            this.minCharCount = minCharCount;
            this.maxCharCount = maxCharCount;

            return this;
        }

        public Factory addCharSet(final String charSet, final int minCount) {
            if (charSet == null)
                throw new IllegalArgumentException("charSet cannot be null");
            if (charSet.isEmpty())
                throw new IllegalArgumentException("charSet cannot be empty");
            if (minCount < 0)
                throw new IllegalArgumentException("Minimum Character Count cannot be negative. Value received: " + minCount);

            charSets.add(charSet);
            minCountsFromCharSets.add(minCount);

            return this;
        }

        public Factory reset() {
            minCharCount = 8;
            maxCharCount = 32;
            charSets = new ArrayList<String>();
            minCountsFromCharSets = new ArrayList<Integer>();

            return this;
        }

        public PasswordChecker create() {
            if (charSets.isEmpty())
                throw new IllegalArgumentException("At least one charset must be specified before a PasswordChecker can be created");
            if (setCountsLargerThanMaxPasswordCount())
                throw new IllegalStateException("Conditions can never be fulfilled. "
                        + "Not enough characters in password to satisfy all conditions, assuming character sets are disjoint");

            return new PasswordChecker(minCharCount, maxCharCount,
                    Collections.unmodifiableList(charSets), Collections.unmodifiableList(minCountsFromCharSets));
        }

        private boolean setCountsLargerThanMaxPasswordCount() {
            int sum = 0;
            for (int count: minCountsFromCharSets)
                sum += count;
            return sum > maxCharCount;
        }
    }

    public static PasswordChecker getBasicPasswordChecker() {
        final Factory factory = getFactory();
        factory.addCharSet(CharacterSets.LOWER_CASES, 1);
        factory.addCharSet(CharacterSets.UPPER_CASES, 1);
        factory.addCharSet(CharacterSets.DIGITS, 1);
        return factory.create();
    }

    public boolean isTooShort() {
        return tooShort;
    }

    public boolean isTooLong() {
        return tooLong;
    }

    public boolean containsIllegalCharacter() {
        return illegalChar;
    }

    public boolean isCharacterTypeMissing() {
        return missingChar;
    }

    public boolean check(final String password) {
        resetFlags();

        if (password.length() < minCharCount) {
            tooShort = true;
            return false;
        }
        if (password.length() > maxCharCount) {
            tooLong = true;
            return false;
        }

        final List<Integer> charCategoryCounts = getInitialCountList();

        for (String letter: getLetterList(password)) {
            if (!updateCounts(letter, charCategoryCounts)) {
                illegalChar = true;
                return false;
            }
        }

        final int sets = charSets.size();
        for (int i = 0; i < sets; ++i) {
            if (charCategoryCounts.get(i) < minCountsInCharSets.get(i)) {
                missingChar = true;
                return false;
            }
        }

        return true;
    }

    private void resetFlags() {
        tooShort = false;
        tooLong = false;
        illegalChar = false;
        missingChar = false;
    }

    private List<Integer> getInitialCountList() {
        final List<Integer> initialCountList = new ArrayList<Integer>();

        final int sets = charSets.size();
        for (int i = 0; i < sets; ++i)
            initialCountList.add(0);

        return initialCountList;
    }

    private List<String> getLetterList(final String password) {
        final List<String> letterList = new ArrayList<String>();

        final int length = password.length();
        for (int i = 0; i < length; ++i)
            letterList.add(password.substring(i, i + 1));

        return letterList;
    }

    private boolean updateCounts(final String letter, final List<Integer> charCategoryCount) {
        boolean found = false;

        final int sets = charSets.size();
        for (int i = 0; i < sets; ++i) {
            if (charSets.get(i).contains(letter)) {
                charCategoryCount.set(i, charCategoryCount.get(i) + 1);
                found = true;
            }
        }

        return found;
    }
}
