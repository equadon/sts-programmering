public class Labb1 {

    public static void main(String[] args) {
        // Run array tests (Teknikövningar)
        ArrayTests arrayTests = new ArrayTests();
        arrayTests.run();

        testSumOfDigits();

        testIsSorted();

        testBubbleSortInts();
        testBubbleSortStrings();
        testBubbleSortFloats();
    }

    public static void testSumOfDigits() {
        System.out.println("\nSum of digits:");
        System.out.printf("   1338 => %d\n", sumOfDigits(1338));
        System.out.printf("   1001 => %d\n", sumOfDigits(1001));
        System.out.printf("  12345 => %d\n", sumOfDigits(12345));
    }

    public static void testIsSorted() {
        Integer[] numbers;
        String[] strings;

        System.out.println("\nisSorted():");

        numbers = new Integer[] {1, 2, 3, 4, 5};
        printSorted(numbers);

        numbers = new Integer[] {5, 4, 3, 2, 1};
        printSorted(numbers);

        numbers = new Integer[] {1, 2, 4, 3, 5, 9};
        printSorted(numbers);

        strings = new String[] {"a", "b", "c", "d"};
        printSorted(strings);

        strings = new String[] {"a", "b", "e", "c", "d"};
        printSorted(strings);
    }

    public static String joinElements(Comparable[] elements) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < elements.length; i++) {
            sb.append(elements[i]);

            if (i < elements.length - 1) {
                sb.append(", ");
            }
        }

        return sb.toString();
    }

    public static void printSorted(Comparable[] elements) {
        System.out.printf("  [%s]: %s\n", joinElements(elements), Sorter.isSorted(elements) ? "sorted" : "not sorted");
    }

    public static void testBubbleSortInts() {
        Integer[] numbers;

        System.out.println("\nIntegers bubbleSort():");

        numbers = new Integer[] {1, 2, 3, 4};
        printBubbleSorted(numbers);

        numbers = new Integer[] {1, 2, 5, 4, 3};
        printBubbleSorted(numbers);

        numbers = new Integer[] {9, 8, 7, 6, 2, 4, 1};
        printBubbleSorted(numbers);
    }

    public static void testBubbleSortStrings() {
        String[] strings;

        System.out.println("\nStrings bubbleSort():");

        strings = new String[] {"a", "b", "c"};
        printBubbleSorted(strings);

        strings = new String[] {"e", "d", "c", "b", "a"};
        printBubbleSorted(strings);

        strings = new String[] {"a", "b", "d", "c"};
        printBubbleSorted(strings);
    }

    public static void testBubbleSortFloats() {
        Double[] doubles;

        System.out.println("\nDouble bubbleSort():");

        doubles = new Double[] {1.2, 7.8, 9.5, 9.4999};
        printBubbleSorted(doubles);

        doubles = new Double[] {10.5, 5.1, 0.115, 0.1111};
        printBubbleSorted(doubles);

        doubles = new Double[] {1.2, 100.12, 5.9};
        printBubbleSorted(doubles);
    }

    public static void printBubbleSorted(Comparable[] elements) {
        Comparable[] sorted = Sorter.bubbleSort(elements.clone());

        System.out.printf("   bubbleSort([%s]) = [%s]: %s\n", joinElements(elements), joinElements(sorted), Sorter.isSorted(sorted) ? "sorted" : "not sorted");
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
}
