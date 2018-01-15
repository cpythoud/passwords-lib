package net.pythoud.passwords;

import java.util.Random;

public class Java6RandomUIntGenerator implements RandomUIntGenerator {

    private final Random random = new Random();

    @Override
    public int getNextUInt(int max) {
        if (max < 2)
            throw new IllegalArgumentException("max must be >= 2");
        return random.nextInt(max);
    }
}
