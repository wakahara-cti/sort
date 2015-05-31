/*
 * Many Pivot Sort (Simple implementation for education)
 *
 * メニー・ピボット・ソート（勉強用のシンプルな実装版）
 *
 * 事前にピボット値をたくさん確定することで高速化を図った改良版クイックソート
 *
 * ご注意
 *   このコードは学習用に実装されたシンプルな実装版です。
 *   実用コードは ManyPivotSort クラスを利用してください。
 *
 * Copyright (c) 2015 masakazu matsubara
 * Released under the MIT license
 * https://github.com/m-matsubara/sort/blob/master/LICENSE.txt
 */package mmsort;

import java.util.Comparator;

public class Simple_ManyPivotSort implements ISortAlgorithm {
	protected static final int PIVOTS_SIZE = 31;							//	ピボットリストのサイズ。大きすぎなければ何でもよいが、2のベぎ乗 - 1が無駄がなくてよい。

	/**
	 * Many pivot sort
	 *
	 * メニー・ピボット・ソート（シンプル実装）
	 *
	 * 内部的に呼び出される。ピボットの配列（ピボット候補）を引数にもつ
	 *
	 * @param array ソート対象
	 * @param from ソート対象の添え字の最小値
	 * @param to ソート対象の添え字の最大値 + 1
	 * @param pivots ピボットの配列
	 * @param fromPivots 使用対象となる pivots 配列の添え字の最小値
	 * @param toPivots 使用対象となる pivots 配列の添え字の最大値 + 1
	 * @param comparator 比較器
	 */
	public static final <T> void mpSort(final T[] array, final int from, final int to, final T[] pivots, final int fromPivots, final int toPivots, final Comparator<? super T> comparator)
	{
		final int range = to - from;		//	ソート範囲サイズ

		//	ソート対象配列サイズが３以下のときは特別扱い
		if (range <= 1) {
			return;
		} else if (range == 2) {
			if (comparator.compare(array[from], array[from + 1]) > 0) {
				final T work = array[from];
				array[from] = array[from + 1];
				array[from + 1] = work;
			}
			return;
		} else if (range == 3) {
			if (comparator.compare(array[from], array[from + 1]) > 0) {
				final T work = array[from];
				array[from] = array[from + 1];
				array[from + 1] = work;
			}
			if (comparator.compare(array[from + 1], array[from + 2]) > 0) {
				final T work = array[from + 1];
				array[from + 1] = array[from + 2];
				array[from + 2] = work;
				if (comparator.compare(array[from], array[from + 1]) > 0) {
					final T work2 = array[from];
					array[from] = array[from + 1];
					array[from + 1] = work2;
				}
			}
			return;
		}
/*
		if (range < 50) {
			combSort(array, from, to, comparator);
			return;
		}
*/

		final int pivotIdx = (fromPivots + toPivots) / 2;		//	pivots配列の中で、今回使うべき要素の添え字
		final T pivot = pivots[pivotIdx];						//	ピボット値

		int curFrom = from;			//	現在処理中位置の小さい方の位置
		int curTo = to - 1;		//	現在処理中位置の大きい方の位置
		while (true) {
			//	このあたりは割と普通のクイックソートのまま。
			int comp1;
			while ((comp1 = comparator.compare(array[curFrom], pivot)) < 0) {
				curFrom++;
			}
			int comp2;
			while ((comp2 = comparator.compare(array[curTo], pivot)) > 0) {
				curTo--;
			}
			if (curFrom <= curTo) {
				if (comp1 != comp2) {	//	実質的には array[curFrom]とarray[curTo]の位置の両方の値がピボット値と同じでない場合という意味
					final T work = array[curFrom];
					array[curFrom] = array[curTo];
					array[curTo] = work;
				}
				curFrom++;
				curTo--;
			} else {
				break;
			}
		}

		if (from < curTo + 1) {
			if (fromPivots >= pivotIdx - 3)	//	pivotsの残りが３つを切ったらpivotsを作り直す。（最後まで使い切らないのは、最後の１個は範囲内の中間値に近いとは言えないので）
				mpSort(array, from, curTo + 1, comparator);
			else
				mpSort(array, from, curTo + 1, pivots, fromPivots, pivotIdx, comparator);
		}

		if (curFrom < to - 1) {
			if (pivotIdx + 1 >= toPivots - 3)	//	pivotsの残りが３つを切ったらpivotsを作り直す。（最後まで使い切らないのは、最後の１個は範囲内の中間値に近いとは言えないので）
				mpSort(array, curFrom, to, comparator);
			else
				mpSort(array, curFrom, to, pivots, pivotIdx + 1, toPivots, comparator);
		}
	}

	/**
	 * メニー・ピボット・ソート（シンプル実装）
	 * @param array ソート対象
	 * @param from ソート対象の添え字の最小値
	 * @param to ソート対象の添え字の最大値 + 1
	 * @param comparator 比較器
	 */
	public static final <T> void mpSort(final T[] array, final int from, final int to, final Comparator<? super T> comparator)
	{
		final int range = to - from;		//	ソート範囲サイズ

		//	ソート対象配列サイズが３以下のときは特別扱い
		if (range <= 1) {
			return;
		} else if (range == 2) {
			if (comparator.compare(array[from], array[from + 1]) > 0) {
				final T work = array[from];
				array[from] = array[from + 1];
				array[from + 1] = work;
			}
			return;
		} else if (range == 3) {
			if (comparator.compare(array[from], array[from + 1]) > 0) {
				final T work = array[from];
				array[from] = array[from + 1];
				array[from + 1] = work;
			}
			if (comparator.compare(array[from + 1], array[from + 2]) > 0) {
				final T work = array[from + 1];
				array[from + 1] = array[from + 2];
				array[from + 2] = work;
				if (comparator.compare(array[from], array[from + 1]) > 0) {
					final T work2 = array[from];
					array[from] = array[from + 1];
					array[from + 1] = work2;
				}
			}
			return;
		}
/*
		if (range < 50) {
			combSort(array, min, max, comparator);
			return;
		}
*/

		if (range < PIVOTS_SIZE * 100) {
			QuickSortM3.quickSortMedian3(array, from, to, comparator);
			return;
		}

		int pivotsSize = 127;
		@SuppressWarnings("unchecked")
		final T[] pivots = (T[])new Object[pivotsSize];		//	ピボット候補の配列

		//	ピボット（複数）の選出
		for (int i = 0; i < pivots.length; i++) {
			pivots[i] = array[(int)(from + (long)range * i / pivots.length + range / 2 / pivots.length)];
		}
		//	ピボット値のみをソート
		BinInsertionSort.binInsertionSort(pivots, 0, pivots.length, comparator);
		//	ソート対象本体のソート
		mpSort(array, from, to, pivots, 0, pivots.length, comparator);
	}

	@Override
	public <T> void sort(final T[] array, final int from, final int to, final Comparator<? super T> comparator)
	{
		mpSort(array, from, to, comparator);
	}

	@Override
	public boolean isStable()
	{
		return false;
	}

	@Override
	public String getName()
	{
		return "Many Pivot Sort (Simple implementation)";
	}
}
