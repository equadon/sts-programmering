import java.util.Arrays;

public class ArrayTests {

    public static int[] range(int end) {
        return range(0, end, 1);
    }

    public static int[] range(int start, int end) {
        return range(start, end, 1);
    }

    public static int[] range(int start, int end, int step) {
        int length = (int) Math.ceil((end - start) / ((double) step));
        int[] array = new int[length];

        int n = start;
        for (int i = 0; i < length; i++) {
            array[i] = n;
            n += step;
        }

        return array;
    }

    public static boolean contains(int[] elements, int check) {
        for (int element : elements) {
            if (element == check) {
                return true;
            }
        }

        return false;
    }

    public static int indexOf(int[] numbers, int number) {
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == number) {
                return i;
            }
        }

        return -1;
    }

    public static int count(int[] numbers, int checkNumber) {
        int count = 0;

        for (int number : numbers) {
            if (number == checkNumber) {
                count++;
            }
        }

        return count;
    }

    public static boolean hasDouble(int[] numbers, int checkNumber) {
        for (int i = 0; i < numbers.length - 1; i++) {
            if (numbers[i] == checkNumber && numbers[i+1] == checkNumber) {
                return true;
            }
        }

        return false;
    }

    public static int[] addNumber(int[] array, int number) {
        int[] newArray = Arrays.copyOf(array, array.length + 1);

        newArray[array.length] = number;

        return newArray;
    }

    public static int[] replace(int[] array, int from, int to) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == from) {
                array[i] = to;
            }
        }

        return array;
    }

    public static int[] switchElements(int[] array, int index_from, int index_to) {
        int value = array[index_from];
        array[index_from] = array[index_to];
        array[index_to] = value;

        return array;
    }
}
