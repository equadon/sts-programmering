package labb4.game;

import java.util.Arrays;
import java.util.Random;

public class Utility {
    private static final Random RANDOM = new Random();

    /**
     * Fisher-Yates shuffle.
     */
    public static void shuffle(Integer[] array) {
        int index, temp;
        for (int i = array.length - 1; i > 0; i--)
        {
            index = RANDOM.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    /**
     * Swap two elements in array.
     */
    public static void moveNumber(Integer[] array, int value, int indexTo) {
        int indexFrom = Arrays.asList(array).indexOf(value);

        if (indexFrom != indexTo) {
            array[indexFrom] = array[indexTo];
            array[indexTo] = value;
        }
    }

    public static String splitCamelCase(String s) {
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }
}
