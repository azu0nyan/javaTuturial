package ru.tutorial.algos;

public class DynamicRods {
    /*
    Задача: дан стержень длинны n и массив цен за отрезок длинны i, придумать самый
    выгодный способ разрезать стержень

        p1 p2 p3  p4  p5
        1  2   5  6   7

        6
        1 + 1 + 1 + 1 + 1 + 1 -> 6
        2 + 2 + 2 -> 6
        1 + 3 + 2 -> 8
        3 + 3 -> 10
     */
    static int p[] = {1, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18};

    static int bestNaiveWay(int l) {
        if (l == 0) return 0;
        if (l == 1) return p[0];
        int max = p[l - 1];
        for (int i = 1; i < l; i++) {
            max = Math.max(max, bestNaiveWay(i) + bestNaiveWay(l - i));
        }
        return max;

    }

    static int table[] = new int[p.length];

    static int best(int l) {
        if (l == 0) return 0;
        if (table[l - 1] > 0) return table[l - 1];
        if (l == 1) return p[0];
        int max = p[l - 1];
        for (int i = 1; i < l; i++) {
            max = Math.max(max, bestNaiveWay(i) + bestNaiveWay(l - i));
        }
        table[l - 1] = max;
        return max;

    }

    static int bestNoRecursion(int l) {
        int t[] = new int[p.length + 1];
        for (int i = 1; i <= l; i++) {
            t[i] = p[i - 1];
            for (int j = 1; j < l; j++) {
                t[i] = Math.max(t[i], t[j] + t[l - j]);
            }
        }
        return t[l];

    }

    public static void main(String[] args) {
        System.out.println(best(12));
        System.out.println(bestNoRecursion(12));
    }


}
