package org.example.portfoliospring1.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;

@RestController
public class TestController {

    // 과제1. 에라토스테네스의 체 방식으로 구현
    @GetMapping("/prime_number_erato")
    public String checkPrime(@RequestParam int n) {
        if (isPrime(n)) {
            return n + "는 소수가 맞습니다.";
        }
        return n + "는 소수가 아닙니다.";
    }

    private Boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;

        // n의 제곱근까지만 체크
        int sqrt = (int) Math.sqrt(n);
        boolean[] prime = new boolean[sqrt + 1];

        // 모두 소수라고 가정
        for (int i = 0; i <= sqrt; i++) {
            prime[i] = true;
        }

        // 에라토스테네스의 체
        for (int i = 2; i * i <= sqrt; i++) {
            if (prime[i]) {
                for (int j = i * i; j <= sqrt; j += i) {
                    prime[j] = false;
                }
            }
        }

        // n이 2부터 sqrt(n)까지의 소수로 나누어지는지 확인
        for (int i = 2; i <= sqrt; i++) {
            if (prime[i] && n % i == 0) {
                return false;
            }
        }

        return true;
    }

    private void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    @GetMapping("/check-prime-number")
    public String checkPrimeNumber(@RequestParam Integer number) {

        if (isPrime(number)) { // 소수 체크 로직
            return number + "는 소수가 맞습니다.";
        }

        return number + "는 소수가 아닙니다.";
    }

    // O(n^2)
    @GetMapping("/bubble-sort")
    public String bubbleSort() {
        int[] array = { 5,3,8,4,2,9,7,1 };

        System.out.println("최초 함수");
        printArray(array);

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }

        System.out.println("");
        System.out.println("최종 정렬 함수");
        printArray(array);

        return array.toString();
    }

    // 과제 2. 머지 소트 로직
    private void merge(int[] array, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        for (int i = 0; i < n1; i++) {
            leftArray[i] = array[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = array[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }

    private void mergeSortHelper(int[] array, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            mergeSortHelper(array, left, mid);
            mergeSortHelper(array, mid + 1, right);

            merge(array, left, mid, right);
        }
    }

    // O(N*logN)
    @GetMapping("/merge-sort")
    public String mergeSort() {
        int[] array = { 5,3,8,4,2,9,7,1 };

        System.out.println("최초 함수");
        printArray(array);

        mergeSortHelper(array, 0, array.length - 1);

        System.out.println("");
        System.out.println("최종 정렬 함수");
        printArray(array);

        return Arrays.toString(array);
    }

    // 옵션 퀵 소트 로직
    private int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;

        return i + 1;
    }

    private void quickSortHelper(int[] array, int low, int high) {
        if (low < high) {
            int pi = partition(array, low, high);

            quickSortHelper(array, low, pi - 1);
            quickSortHelper(array, pi + 1, high);
        }
    }

    // O(N*logN)
    @GetMapping("/quick-sort")
    public String quickSort() {
        int[] array = { 5,3,8,4,2,9,7,1 };

        System.out.println("최초 함수");
        printArray(array);

        quickSortHelper(array, 0, array.length - 1);

        System.out.println("");
        System.out.println("최종 정렬 함수");
        printArray(array);

        return Arrays.toString(array);
    }
}
