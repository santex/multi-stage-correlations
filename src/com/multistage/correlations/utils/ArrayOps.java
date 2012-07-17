package com.multistage.correlations.utils;

public class ArrayOps {

	/**
	 * #1 Find the index of the Lagest value
	 * 
	 * @param nums
	 *            an array of integers.
	 * @return the largest value in nums.
	 */

	public static int findLargest(double[] A) {
		// variable to keep track of largest item
		double largestValue = A[0];
		int largestIndex = 0;
		for (int i = 1; i < A.length; i++) {
			if (A[i] > largestValue) {
				largestValue = A[i];
				largestIndex = i;
			}
		}
		return largestIndex;
	}

	/**
	 * #1 Find the index of the Lagest value
	 * 
	 * @param nums
	 *            an array of integers.
	 * @return the largest value in nums.
	 */

	public static int findLargest(double[] A, int boundary) {
		// variable to keep track of largest item
		double largestValue = A[0];
		int largestIndex = 0;
		for (int i = 1; i <= boundary; i++) {
			if (A[i] > largestValue) {
				largestValue = A[i];
				largestIndex = i;
			}
		}
		return largestIndex;
	}

	/**
	 * #1 Find the index of the Smallest value
	 * 
	 * @param nums
	 *            an array of integers.
	 * @return the largest value in nums.
	 */

	public static int findSmallest(double[] A) {
		// variable to keep track of largest item
		double smallestValue = A[0];
		int smallestIndex = 0;
		for (int i = 1; i < A.length; i++) {
			if (A[i] < smallestValue) {
				smallestValue = A[i];
				smallestIndex = i;
			}
		}

		return smallestIndex;
	}

	/**
	 * #1 Find the index of the Smallest value
	 * 
	 * @param nums
	 *            an array of integers.
	 * @return the largest value in nums.
	 */

	public static int findSmallest(double[] A, int boundary) {
		// variable to keep track of largest item
		double smallestValue = A[0];
		int smallestIndex = 0;
		for (int i = 1; i < boundary; i++) {
			if (A[i] < smallestValue) {
				smallestValue = A[i];
				smallestIndex = i;
			}
		}

		return smallestIndex;
	}

	/**
	 * Sort array
	 * 
	 * @param nums
	 *            an array of integers.
	 * @return the largest value in nums.
	 */

	public static double[] Sort(double[] unsorted) {
		// allocate an array to hold the result
		double[] sorted = new double[unsorted.length];

		// boundary is the last element of the unsorted array
		int boundary = unsorted.length - 1;

		while (boundary >= 0) {
			// find the largest item in the unsorted array
			int largest = findLargest(unsorted, boundary);

			// swap it with the boundary item
			sorted[boundary] = unsorted[largest];
			unsorted[largest] = unsorted[boundary];

			// move the boundary
			boundary--;
		}

		return sorted;
	}

	/**
	 * #1 Find the largest value in an array of integers.
	 * 
	 * @param nums
	 *            an array of integers.
	 * @return the largest value in nums.
	 */
	public static double findMax(double[] nums) {
		// Use curMax to keep track of the largest value
		// that has been seen so far...
		double curMax = nums[0];

		// Examine each number in nums in turn and see
		// if it is larger than the largest number seen
		// so far.
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] > curMax) {
				curMax = nums[i];
			}
		}

		return curMax;
	}

	/**
	 * #2 Find the largest value in an array of Strings.
	 * 
	 * @param strs
	 *            an array of Strings.
	 * @return the largest String in strs.
	 */
	public static String findMax(String[] strs) {
		// Use curMax to keep track of the largest String
		// that has been seen so far...
		String curMax = strs[0];

		// Examine each String in strs in turn and see
		// if it is larger than the largest String seen
		// so far.
		for (int i = 1; i < strs.length; i++) {
			if (strs[i].compareTo(curMax) > 0) {
				curMax = strs[i];
			}
		}

		return curMax;
	}

	/**
	 * #3 Find the smallest value in an array of integers.
	 * 
	 * @param nums
	 *            an array of integers.
	 * @return the smallest value in nums.
	 */
	public static int findMin(int[] nums) {
		// Use curMin to keep track of the smallest value
		// that has been seen so far...
		int curMin = nums[0];

		// Examine each number in nums in turn and see
		// if it is smaller than the smallest number seen
		// so far.
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] < curMin) {
				curMin = nums[i];
			}
		}

		return curMin;
	}

	/**
	 * #4 Find the smallest value in an array of Strings.
	 * 
	 * @param strs
	 *            an array of Strings.
	 * @return the smallest String in strs.
	 */
	public static String findMin(String[] strs) {
		// Use curMin to keep track of the smallest String
		// that has been seen so far...
		String curMin = strs[0];

		// Examine each String in strs in turn and see
		// if it is smaller than the smallest String seen
		// so far.
		for (int i = 1; i < strs.length; i++) {
			if (strs[i].compareTo(curMin) < 0) {
				curMin = strs[i];
			}
		}

		return curMin;
	}

	/**
	 * #5 Find the index of a specific value in an array of integers. If the
	 * value occurs more than once the index of the first occurrence is
	 * returned.
	 * 
	 * @param nums
	 *            an array of doubles
	 * @param val
	 *            the value to search for in nums.
	 * @return the index of val in nums, or -1 if val does not appear in nums.
	 */
	public static int findIndex(double[] nums, double val) {
		// Examine each number in nums in turn, if
		// it is the value we are looking for return
		// the index where it was found.
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] == val) {
				return i;
			}
		}

		// Didn't find the val, otherwise the return i above
		// would have exectued and we would never have gotten
		// here, so return -1.
		return -1;
	}

	/**
	 * #6 Find the index of a specific value in an array of Strings. If the
	 * value occurs more than once the index of the first occurance is returned.
	 * 
	 * @param strs
	 *            an array of Strings.
	 * @param str
	 *            the value to search for in strs.
	 * @return the index of str in strs, or -1 if val does not appear in strs.
	 */
	public static int findVal(String[] strs, String str) {
		// Examine each String in strs in turn, if
		// it is the value we are looking for return
		// the index where it was found.
		for (int i = 0; i < strs.length; i++) {
			if (strs[i].equals(str)) {
				return i;
			}
		}

		// Didn't find the str, otherwise the return i above
		// would have exectued and we would never have gotten
		// here, so return -1.
		return -1;
	}

	/**
	 * #7 Compute and return the sum of all of the elements of nums.
	 * 
	 * @param nums
	 *            an array of integers.
	 * @return the sum of all of the elements of nums.
	 */
	public static int sum(int[] nums) {
		// Sum will be a running total of all of the numbers
		// in nums. So far we have considered 0 numbers
		// so the sum is 0!
		int sum = 0;

		// Now add each number in nums to the running total
		// stored in sums.
		for (int i = 0; i < nums.length; i++) {
			sum = sum + nums[i];
		}

		return sum;
	}

	/**
	 * #8 Compute and return the product of all of the elements of nums.
	 * 
	 * @param nums
	 *            an array of integers.
	 * @return the product of all of the elements of nums.
	 */
	public static int product(int[] nums) {
		// prod will be a running products of all of the numbers
		// in nums. We need to start the product at 1 instead of
		// zero!
		int product = 1;

		// Now multiply product by each number in nums
		// to keep the running product.
		for (int i = 0; i < nums.length; i++) {
			product = product * nums[i];
		}

		return product;
	}

	/**
	 * #9 Remove from nums the value at the specified index. All of the values
	 * in nums with indices greater than index must be shifted down by 1 index
	 * to fill in the empty space.
	 * 
	 * @param nums
	 *            an array of integers.
	 * @param index
	 *            the index of the value to remove from nums.
	 */
	public static void remove(int[] nums, int index) {
		// Move every value at an index greater than
		// the specified index one location to the
		// left.
		for (int i = index; i < nums.length - 1; i++) {
			nums[i] = nums[i + 1];
		}
	}

	/**
	 * #10 Insert val into nums at the specified index. All values in nums with
	 * index >= index must be shifted up by one index to make an empty space for
	 * val. If the array is full then the value at the maximum index will be
	 * lost.
	 * 
	 * @param nums
	 *            an array of integers.
	 * @param index
	 *            the index at which val is to be inserted.
	 * @param val
	 *            the value to be inserted into nums.
	 */
	public static void insert(int[] nums, int index, int val) {
		// Move every value at an index greater than or
		// equal to the specified index one location
		// to the right. Start at the right end of the
		// array!
		for (int i = nums.length - 1; i > index; i--) {
			nums[i + 1] = nums[i];
		}

		// Now set the specified index to hold the value.
		nums[index] = val;
	}

	/**
	 * #11 Swap the element at index i of nums with the element at index j.
	 * 
	 * @param nums
	 *            an array of integers.
	 * @param i
	 *            index of the first number to be swapped.
	 * @param j
	 *            index of the second number to be swapped.
	 */
	public static void swapElements(int[] nums, int i, int j) {
		int temp = nums[i]; // set aside nums[i].
		nums[i] = nums[j]; // copy nums[j] over nums[i]
		nums[j] = temp; // copy the original nums[i]
		// over nums[j].
	}

	/**
	 * #12 Make and return a copy of the array nums.
	 * 
	 * @param nums
	 *            an array of integers
	 * @return a new array that is a copy of nums.
	 */
	public static int[] copy(int[] nums) {
		// Create a new array that is the same size
		// as nums.
		int[] newNums = new int[nums.length];

		// Copy each element of nums into the corresponding
		// element of newNums.
		for (int i = 0; i < nums.length; i++) {
			newNums[i] = nums[i];
		}

		// Return the reference to the newNums array.
		return newNums;
	}
}
