import java.util.Arrays;

/**
 * Created by Equadon on 2015-10-13.
 */
public class Labb1Tests {

    public static void testSumOfDigits() {
        System.out.println("\nSum of digits:");

        printSum(12);
        printSum(1338);
        printSum(1001);
        printSum(12345);
        printSum(999999);
    }

    public static void printSum(int number) {
        System.out.printf("   %-6d  ==>  %d\n", number, Labb1.sumOfDigits(number));
    }

    public static void printSorted(Comparable[] elements) {
        String sortedString = Labb1.isSorted(elements) ? "sorted" : "not sorted";

        System.out.printf("   %-19s (%s)\n", Arrays.toString(elements), sortedString);
    }

    public static void printBubbleSorted(Comparable[] elements) {
        String joinedElements = Arrays.toString(elements);

        Labb1.bubbleSort(elements);
        String sortedString = Labb1.isSorted(elements) ? "sorted" : "not sorted";

        System.out.printf("   %-26s  ==>  %-26s  (%s)\n", joinedElements, Arrays.toString(elements), sortedString);
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

    public static void testBubbleSortInts() {
        Integer[] numbers;

        System.out.println("\nbubbleSort():   [integers]");

        numbers = new Integer[] {1, 2, 3, 4};
        printBubbleSorted(numbers);

        numbers = new Integer[] {1, 2, 5, 4, 3};
        printBubbleSorted(numbers);

        numbers = new Integer[] {9, 8, 7, 6, 2, 4, 1};
        printBubbleSorted(numbers);
    }

    public static void testBubbleSortStrings() {
        String[] strings;

        System.out.println("bubbleSort():   [strings]");

        strings = new String[] {"a", "b", "c"};
        printBubbleSorted(strings);

        strings = new String[] {"e", "d", "c", "b", "a"};
        printBubbleSorted(strings);

        strings = new String[] {"a", "b", "d", "c"};
        printBubbleSorted(strings);
    }

    public static void testBubbleSortDoubles() {
        Double[] doubles;

        System.out.println("bubbleSort():   [doubles]");

        doubles = new Double[] {1.2, 7.8, 9.5, 9.4999};
        printBubbleSorted(doubles);

        doubles = new Double[] {10.5, 5.1, 0.115, 0.1111};
        printBubbleSorted(doubles);

        doubles = new Double[] {1.2, 100.12, 100.11};
        printBubbleSorted(doubles);
    }

    public static void printRange(int end) {
        printRange(String.format("range(%d)", end), ArrayExercises.range(end));
    }

    public static void printRange(int start, int end) {
        printRange(String.format("range(%d, %d)", start, end), ArrayExercises.range(start, end));
    }

    public static void printRange(int start, int end, int step) {
        printRange(String.format("range(%d, %d, %d)", start, end, step), ArrayExercises.range(start, end, step));
    }

    public static void printRange(String range, int[] numbers) {
        System.out.printf("%-16s -> %s\n", range, Arrays.toString(numbers));
    }

    public static void testArrays() {
        printRange(5);
        printRange(1, 7);
        printRange(1, 5, 2);
        printRange(2, 15, 4);
        printRange(10, 5, -1);

        int[] array = ArrayExercises.range(1, 11);

        System.out.println("\n" + Arrays.toString(array) + ":");
        System.out.printf("   contains '3': %s\n", ArrayExercises.contains(array, 3) ? "yes" : "no");
        System.out.printf("   index of '4': %d\n", ArrayExercises.indexOf(array, 4));
        System.out.printf("   count '3': %d\n", ArrayExercises.count(array, 3));

        int[] newArray = array.clone();
        newArray = ArrayExercises.addNumber(newArray, 9);

        newArray = ArrayExercises.replace(newArray, 2, 3);

        System.out.println(Arrays.toString(newArray) + ":");
        System.out.printf("   found two consecutive '9': %s\n", ArrayExercises.hasDouble(newArray, 9) ? "yes" : "no");

        newArray = ArrayExercises.switchElements(newArray, 9, 10);

        System.out.println(Arrays.toString(newArray) + ":");
        System.out.printf("   found two consecutive '9': %s\n", ArrayExercises.hasDouble(newArray, 9) ? "yes" : "No");
    }
}
