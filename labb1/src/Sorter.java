public class Sorter {

    /**
     * Swap elements in descending order and return true if a swap occurred.
     */
    private static boolean swap(Comparable[] elements) {
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

    /**
     * Bubble sort implementation.
     */
    public static Comparable[] bubbleSort(Comparable[] elements) {
        while (swap(elements)) {
            // keep swapping
        }

        return elements;
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
}
