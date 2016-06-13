/*
 * Merge sort
 *
 * http://www.mmatsubara.com/developer/sort/
 *
 * Copyright (c) 2016 matsubara masakazu
 * Released under the MIT license
 * https://github.com/m-matsubara/sort/blob/master/LICENSE.txt
 */
package mmsort;

import java.util.Comparator;

public class No7Sort implements ISortAlgorithm {
	private static final int MODE_123 = 0;
	private static final int MODE_132 = 1;
	private static final int MODE_213 = 2;
	private static final int MODE_231 = 3;
	private static final int MODE_312 = 4;
	private static final int MODE_321 = 5;
	private static final int MODE_12 = 6;
	private static final int MODE_13 = 7;
	private static final int MODE_21 = 8;
	private static final int MODE_23 = 9;
	private static final int MODE_31 = 10;
	private static final int MODE_32 = 11;
	private static final int MODE_1 = 12;
	private static final int MODE_2 = 13;
	private static final int MODE_3 = 14;

	public static final <T> void merge(final T[] array, int p1, int p2, int p3, final int to, final T[] workArray, final Comparator<? super T> comparator) {
		int mode;
		if (comparator.compare(array[p1], array[p2]) <= 0) {
			// array[p1] <= array[p2]
			if (comparator.compare(array[p2], array[p3]) <= 0) {
				// array[p1] <= array[p2] <= array[p3]
				mode = MODE_123;
			} else if (comparator.compare(array[p1], array[p3]) <= 0) {
				// array[p1] <= array[p3] <= array[p2]
				mode = MODE_132;
			} else {
				// array[p3] < array[p1] <= array[p2]
				mode = MODE_312;
			}
		} else {
			// array[p2] < array[p1]
			if (comparator.compare(array[p1], array[p3]) <= 0) {
				// array[p2] < array[p1] <= array[p3]
				mode = MODE_213;
			} else if (comparator.compare(array[p2], array[p3]) <= 0) {
				// array[p2] <= array[p3] < array[p1]
				mode = MODE_231;
			} else {
				// array[p3] < array[p2] < array[p1]
				mode = MODE_321;
			}
		}

		System.arraycopy(array, p1, workArray, 0, p3 - p1);
		int idx = p1;
		final int p1to = p2 - idx;
		final int p2to = p3 - idx;
		final int p3to = to;
		p1 = 0;
		p2 -= idx;

		ThreeLane:
		for (; idx < to; idx++) {
			switch (mode) {
			case MODE_123:
				array[idx] = workArray[p1++];
				if (p1 >= p1to) {
					mode = MODE_23;
					idx++;
					break ThreeLane;
				} else if (comparator.compare(workArray[p1], workArray[p2]) <= 0)
					; // モード変更なし
				else if (comparator.compare(workArray[p1], array[p3]) <= 0)
					mode = MODE_213;
				else
					mode = MODE_231;
				break;
			case MODE_132:
				array[idx] = workArray[p1++];
				if (p1 >= p1to) {
					mode = MODE_32;
					idx++;
					break ThreeLane;
				} else if (comparator.compare(workArray[p1], array[p3]) <= 0)
					; // モード変更なし
				else if (comparator.compare(workArray[p1], workArray[p2]) <= 0)
					mode = MODE_312;
				else
					mode = MODE_321;
				break;
			case MODE_213:
				array[idx] = workArray[p2++];
				//if (array[idx] == null)
				//	throw new RuntimeException("3");
				if (p2 >= p2to) {
					mode = MODE_13;
					idx++;
					break ThreeLane;
				} else if (comparator.compare(workArray[p2], workArray[p1]) < 0)
					; // モード変更なし
				else if (comparator.compare(workArray[p2], array[p3]) <= 0)
					mode = MODE_123;
				else
					mode = MODE_132;
				break;
			case MODE_231:
				array[idx] = workArray[p2++];
				if (p2 >= p2to) {
					mode = MODE_31;
					idx++;
					break ThreeLane;
				} else if (comparator.compare(workArray[p2], array[p3]) <= 0)
					; // モード変更なし
				else if (comparator.compare(workArray[p2], workArray[p1]) < 0)
					mode = MODE_321;
				else
					mode = MODE_312;
				break;
			case MODE_312:
				array[idx] = array[p3++];
				if (p3 >= p3to) {
					mode = MODE_12;
					idx++;
					break ThreeLane;
				} else if (comparator.compare(array[p3], workArray[p1]) < 0)
					; // モード変更なし
				else if (comparator.compare(array[p3], workArray[p2]) < 0)
					mode = MODE_132;
				else
					mode = MODE_123;
				break;
			case MODE_321:
				array[idx] = array[p3++];
				if (p3 >= p3to) {
					mode = MODE_21;
					idx++;
					break ThreeLane;
				} else if (comparator.compare(array[p3], workArray[p2]) < 0)
					; // モード変更なし
				else if (comparator.compare(array[p3], workArray[p1]) < 0)
					mode = MODE_231;
				else
					mode = MODE_213;
				break;
			}
		}
		TwoLane:
		for (; idx < to; idx++) {
			switch (mode) {
			case MODE_12:
				array[idx] = workArray[p1++];
				if (p1 >= p1to) {
					mode = MODE_2;
					idx++;
					break TwoLane;
				} else if (comparator.compare(workArray[p1], workArray[p2]) <= 0)
					; // モード変更なし
				else
					mode = MODE_21;
				break;
			case MODE_13:
				array[idx] = workArray[p1++];
				if (p1 >= p1to) {
					mode = MODE_3;
					idx++;
					break TwoLane;
				} else if (comparator.compare(workArray[p1], array[p3]) <= 0)
					; // モード変更なし
				else
					mode = MODE_31;
				break;
			case MODE_21:
				array[idx] = workArray[p2++];
				if (p2 >= p2to) {
					mode = MODE_1;
					idx++;
					break TwoLane;
				} else if (comparator.compare(workArray[p2], workArray[p1]) < 0)
					; // モード変更なし
				else
					mode = MODE_12;
				break;
			case MODE_23:
				array[idx] = workArray[p2++];
				if (p2 >= p2to) {
					mode = MODE_3;
					idx++;
					break TwoLane;
				} else if (comparator.compare(workArray[p2], array[p3]) <= 0)
					; // モード変更なし
				else
					mode = MODE_32;
				break;
			case MODE_31:
				array[idx] = array[p3++];
				if (p3 >= p3to) {
					mode = MODE_1;
					idx++;
					break TwoLane;
				} else if (comparator.compare(array[p3], workArray[p1]) < 0)
					; // モード変更なし
				else
					mode = MODE_13;
				break;
			case MODE_32:
				array[idx] = array[p3++];
				if (p3 >= p3to) {
					mode = MODE_2;
					idx++;
					break TwoLane;
				} else if (comparator.compare(array[p3], workArray[p2]) < 0)
					; // モード変更なし
				else
					mode = MODE_23;
				break;
			}
		}
		if (mode == MODE_1) {
			System.arraycopy(workArray, p1, array, idx, p1to - p1);
		} else if (mode == MODE_2) {
			System.arraycopy(workArray, p2, array, idx, p2to - p2);
		}
	}


	/**
	 * No7Sort
	 * 作業用一時領域はソート対象の範囲サイズと同サイズ必要
	 * @param array sort target / ソート対象
	 * @param from index of first element / ソート対象の開始位置
	 * @param to index of last element (exclusive) / ソート対象の終了位置 + 1
	 * @param workArray work area / 作業用一時領域
	 * @param comparator comparator of array element / 比較器
	 */
	public static final <T> void no7Sort(final T[] array, final int from, final int to, final T[] workArray, final Comparator<? super T> comparator)
	{
		final int range = to - from;

		//	ソート対象配列サイズが３以下のときは特別扱い
		if (range <= 1) {
			return;
		} else if (range == 2) {
			if (comparator.compare(array[from + 1], array[from]) < 0) {
				T work = array[from];
				array[from] = array[from + 1];
				array[from + 1] = work;
			}
			return;
		} else if (range == 3) {
			if (comparator.compare(array[from + 1], array[from]) < 0) {
				T work = array[from];
				array[from] = array[from + 1];
				array[from + 1] = work;
			}
			if (comparator.compare(array[from + 2], array[from + 1]) < 0) {
				T work = array[from + 1];
				array[from + 1] = array[from + 2];
				array[from + 2] = work;
				if (comparator.compare(array[from + 1], array[from]) < 0) {
					work = array[from];
					array[from] = array[from + 1];
					array[from + 1] = work;
				}
			}
			return;
		}

		final int gap = range / 3;
		int p1 = from;
		int p2 = p1 + gap;
		int p3 = p2 + gap;
		no7Sort(array, from, p2, workArray, comparator);
		no7Sort(array, p2,   p3, workArray, comparator);
		no7Sort(array, p3,   to, workArray, comparator);

		merge(array, p1, p2, p3, to, workArray, comparator);
/*
		final int p1to = p2;
		final int p2to = p3;
		final int p3to = to;
		int mode;
		if (comparator.compare(array[p1], array[p2]) <= 0) {
			// array[p1] <= array[p2]
			if (comparator.compare(array[p2], array[p3]) <= 0) {
				// array[p1] <= array[p2] <= array[p3]
				mode = MODE_123;
			} else if (comparator.compare(array[p1], array[p3]) <= 0) {
				// array[p1] <= array[p3] <= array[p2]
				mode = MODE_132;
			} else {
				// array[p3] < array[p1] <= array[p2]
				mode = MODE_312;
			}
		} else {
			// array[p2] < array[p1]
			if (comparator.compare(array[p1], array[p3]) <= 0) {
				// array[p2] < array[p1] <= array[p3]
				mode = MODE_213;
			} else if (comparator.compare(array[p2], array[p3]) <= 0) {
				// array[p2] <= array[p3] < array[p1]
				mode = MODE_231;
			} else {
				// array[p3] < array[p2] < array[p1]
				mode = MODE_321;
			}
		}

		System.arraycopy(array, from, workArray, from, gap * 2);
		int idx = from;
		ThreeLean:
		for (; idx < to; idx++) {
			switch (mode) {
			case MODE_123:
				array[idx] = workArray[p1++];
				//if (array[idx] == null)
				//	throw new RuntimeException("1");
				if (p1 >= p1to) {
					mode = MODE_23;
					idx++;
					break ThreeLean;
				} else if (comparator.compare(workArray[p1], workArray[p2]) <= 0)
					; // モード変更なし
				else if (comparator.compare(workArray[p1], array[p3]) <= 0)
					mode = MODE_213;
				else
					mode = MODE_231;
				break;
			case MODE_132:
				array[idx] = workArray[p1++];
				//if (array[idx] == null)
				//	throw new RuntimeException("2");
				if (p1 >= p1to) {
					mode = MODE_32;
					idx++;
					break ThreeLean;
				} else if (comparator.compare(workArray[p1], array[p3]) <= 0)
					; // モード変更なし
				else if (comparator.compare(workArray[p1], workArray[p2]) <= 0)
					mode = MODE_312;
				else
					mode = MODE_321;
				break;
			case MODE_213:
				array[idx] = workArray[p2++];
				//if (array[idx] == null)
				//	throw new RuntimeException("3");
				if (p2 >= p2to) {
					mode = MODE_13;
					idx++;
					break ThreeLean;
				} else if (comparator.compare(workArray[p2], workArray[p1]) < 0)
					; // モード変更なし
				else if (comparator.compare(workArray[p2], array[p3]) <= 0)
					mode = MODE_123;
				else
					mode = MODE_132;
				break;
			case MODE_231:
				array[idx] = workArray[p2++];
				//if (array[idx] == null)
				//	throw new RuntimeException("4");
				if (p2 >= p2to) {
					mode = MODE_31;
					idx++;
					break ThreeLean;
				} else if (comparator.compare(workArray[p2], array[p3]) <= 0)
					; // モード変更なし
				else if (comparator.compare(workArray[p2], workArray[p1]) < 0)
					mode = MODE_321;
				else
					mode = MODE_312;
				break;
			case MODE_312:
				array[idx] = array[p3++];
				//if (array[idx] == null)
				//	throw new RuntimeException("5");
				if (p3 >= p3to) {
					mode = MODE_12;
					idx++;
					break ThreeLean;
				} else if (comparator.compare(array[p3], workArray[p1]) < 0)
					; // モード変更なし
				else if (comparator.compare(array[p3], workArray[p2]) < 0)
					mode = MODE_132;
				else
					mode = MODE_123;
				break;
			case MODE_321:
				array[idx] = array[p3++];
				//if (array[idx] == null)
				//	throw new RuntimeException("6");
				if (p3 >= p3to) {
					mode = MODE_21;
					idx++;
					break ThreeLean;
				} else if (comparator.compare(array[p3], workArray[p2]) < 0)
					; // モード変更なし
				else if (comparator.compare(array[p3], workArray[p1]) < 0)
					mode = MODE_231;
				else
					mode = MODE_213;
				break;
			}
		}
		TwoLean:
		for (; idx < to; idx++) {
			switch (mode) {
			case MODE_12:
				array[idx] = workArray[p1++];
				//if (array[idx] == null)
				//	throw new RuntimeException("7");
				if (p1 >= p1to) {
					mode = MODE_2;
					idx++;
					break TwoLean;
				} else if (comparator.compare(workArray[p1], workArray[p2]) <= 0)
					; // モード変更なし
				else
					mode = MODE_21;
				break;
			case MODE_13:
				array[idx] = workArray[p1++];
				//if (array[idx] == null)
				//	throw new RuntimeException("8");
				if (p1 >= p1to) {
					mode = MODE_3;
					idx++;
					break TwoLean;
				} else if (comparator.compare(workArray[p1], array[p3]) <= 0)
					; // モード変更なし
				else
					mode = MODE_31;
				break;
			case MODE_21:
				array[idx] = workArray[p2++];
				//if (array[idx] == null)
				//	throw new RuntimeException("9");
				if (p2 >= p2to) {
					mode = MODE_1;
					idx++;
					break TwoLean;
				} else if (comparator.compare(workArray[p2], workArray[p1]) < 0)
					; // モード変更なし
				else
					mode = MODE_12;
				break;
			case MODE_23:
				array[idx] = workArray[p2++];
				//if (array[idx] == null)
				//	throw new RuntimeException("10");
				if (p2 >= p2to) {
					mode = MODE_3;
					idx++;
					break TwoLean;
				} else if (comparator.compare(workArray[p2], array[p3]) <= 0)
					; // モード変更なし
				else
					mode = MODE_32;
				break;
			case MODE_31:
				array[idx] = array[p3++];
				//if (array[idx] == null)
				//	throw new RuntimeException("11");
				if (p3 >= p3to) {
					mode = MODE_1;
					idx++;
					break TwoLean;
				} else if (comparator.compare(array[p3], workArray[p1]) < 0)
					; // モード変更なし
				else
					mode = MODE_13;
				break;
			case MODE_32:
				array[idx] = array[p3++];
				//if (array[idx] == null)
				//	throw new RuntimeException("12");
				if (p3 >= p3to) {
					mode = MODE_2;
					idx++;
					break TwoLean;
				} else if (comparator.compare(array[p3], workArray[p2]) < 0)
					; // モード変更なし
				else
					mode = MODE_23;
				break;
			}
		}
		if (mode == MODE_1) {
			System.arraycopy(workArray, p1, array, idx, p1to - p1);
		} else if (mode == MODE_2) {
			System.arraycopy(workArray, p2, array, idx, p2to - p2);
		}
*/
	}


	/**
	 * No7Sort
	 * No7ソート
	 * @param array sort target / ソート対象
	 * @param from index of first element / ソート対象の開始位置
	 * @param to index of last element (exclusive) / ソート対象の終了位置 + 1
	 * @param comparator comparator of array element / 比較器
	 */
	public static final <T> void no7Sort(T[] array, int from, int to, Comparator<? super T> comparator)
	{
		@SuppressWarnings("unchecked")
		final T[] workArray = (T[])new Object[to - from];

		no7Sort(array, from, to, workArray, comparator);
	}

	@Override
	public <T> void sort(final T[] array, final int from, final int to, final Comparator<? super T> comparator)
	{
		no7Sort(array, from, to, comparator);
	}

	@Override
	public boolean isStable()
	{
		return true;
	}

	@Override
	public String getName()
	{
		return "No7Sort";
	}
}
