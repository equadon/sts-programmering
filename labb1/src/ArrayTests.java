public class ArrayTests {

    public void run() {
        int[] array = buildArray();

        System.out.printf("Array contains '3': %s\n", containsNumber(array, 3) ? "Yes" : "No");
        System.out.printf("Index of '4': %d\n", indexOf(array, 4));
        System.out.printf("Count '3': %d\n", count(array, 3));
        System.out.printf("Found double '6': %s\n", hasDouble(array, 5) ? "Yes" : "No");

        int[] newArray = array.clone();
        newArray = addNumber(newArray, 3);

        newArray = replace(newArray, 3, 4);
        newArray = switchElements(newArray, 3, 4);
    }

    public int[] buildArray() {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        return array;
    }

    public boolean containsNumber(int[] numbers, int checkNumber) {
        for (int number : numbers) {
            if (number == checkNumber) {
                return true;
            }
        }

        return false;
    }

    public int indexOf(int[] numbers, int number) {
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == number) {
                return i;
            }
        }

        return -1;
    }

    public int count(int[] numbers, int checkNumber) {
        int count = 0;

        for (int number : numbers) {
            if (number == checkNumber) {
                count++;
            }
        }

        return count;
    }

    public boolean hasDouble(int[] numbers, int checkNumber) {
        for (int i = 0; i < numbers.length - 1; i++) {
            if (numbers[i] == checkNumber && numbers[i+1] == checkNumber) {
                return true;
            }
        }

        return false;
    }

    public int[] addNumber(int[] array, int number) {
        int[] newArray = new int[array.length + 1];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }

        newArray[array.length] = number;

        return newArray;
    }

    public int[] replace(int[] array, int from, int to) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == from) {
                array[i] = to;
            }
        }

        return array;
    }

    public int[] switchElements(int[] array, int from, int to) {
        int value = array[from];
        array[from] = array[to];
        array[to] = value;

        return array;
    }
}
