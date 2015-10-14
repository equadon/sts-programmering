public class Labb1 {

    public static void main(String[] args) {
        Labb1Tests.testArrays(); // Run array tests (Teknikövningar)

        Labb1Tests.testSumOfDigits();

        Labb1Tests.testIsSorted();

        Labb1Tests.testBubbleSortInts();
        Labb1Tests.testBubbleSortStrings();
        Labb1Tests.testBubbleSortDoubles();
    }

    /**
     * Calculate the sum of the digits in number.
     */
    public static int sumOfDigits(int number) {
        int sum = 0;

        while (number > 0) {
            sum += number % 10;
            number = number / 10;
        }

        return sum;
    }

    /**
     * Check if elements are sorted in ascending order.
     */
    public static boolean isSorted(Comparable[] elements) {
        for (int i = 0; i < elements.length - 1; i++) {
            Comparable value = elements[i];
            Comparable next = elements[i+1];

            if (value.compareTo(next) > 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Bubble sort.
     */
    public static Comparable[] bubbleSort(Comparable[] elements) {
        while (bytintill(elements)) {
            // keep swapping
        }

        return elements;
    }

    /**
     * Swap elements in descending order and return true if a swap occurred.
     */
    private static boolean bytintill(Comparable[] elements) {
        boolean swapped = false;

        for (int i = 0; i < elements.length - 1; i++) {
            Comparable value = elements[i];
            Comparable next = elements[i+1];

            if (value.compareTo(next) > 0) {
                elements[i] = next;
                elements[i+1] = value;

                swapped = true;
            }
        }

        return swapped;
    }
}
