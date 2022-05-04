package ru.tutorial.algos;

import java.util.ArrayList;


public class LeeAlgorithm {

    public static void printMap(boolean[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] ? "#" : ".");
            }
            System.out.println();
        }
    }

    public static void printMap(int[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.printf("%3s", map[i][j] == -1 ? "##" : String.valueOf(map[i][j]));
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        boolean map[][] = {
                {false, false, true, false, false, false, false, true, false, false},
                {false, false, true, false, false, false, false, true, false, false},
                {false, false, true, false, false, false, false, true, false, false},
                {false, false, true, false, true, false, false, false, false, false},
                {false, false, true, false, true, false, false, false, false, false},
                {false, false, true, false, true, true, false, true, true, true},
                {false, false, false, false, true, false, false, true, false, false},
                {false, false, false, false, true, false, true, true, false, false},
                {false, false, false, false, true, false, false, false, false, false}
        };
        printMap(map);
        var path = findPath(map, new int[]{0, 0}, new int[]{8, 9});
        for (int[] ij : path) {
            System.out.print(ij[0] + " " + ij[1] + " -> ");
        }
        System.out.println("END");
    }

    public static ArrayList<int[]> neighbours(boolean[][] map, int[] cell) {
        ArrayList<int[]> res = new ArrayList<>();
        if (cell[0] > 0 && !(map[cell[0] - 1][cell[1]])) res.add(new int[]{cell[0] - 1, cell[1]});
        if (cell[0] < map.length - 1 && !(map[cell[0] + 1][cell[1]])) res.add(new int[]{cell[0] + 1, cell[1]});
        if (cell[1] > 0 && !(map[cell[0]][cell[1] - 1])) res.add(new int[]{cell[0], cell[1] - 1});
        if (cell[1] < map[0].length - 1 && !(map[cell[0]][cell[1] + 1])) res.add(new int[]{cell[0], cell[1] + 1});
        return res;
    }

    public static ArrayList<int[]> findPath(boolean[][] map, int[] from, int[] to) {
        int[][] fill = new int[map.length][map[0].length];
        int wave = 1;
        fill[from[0]][from[1]] = wave;
        ArrayList<int[]> prevWaveCells = new ArrayList<>();
        prevWaveCells.add(from);
        boolean canFind = true;
        while (fill[to[0]][to[1]] == 0 && canFind) {
            ArrayList<int[]> curFilledCells = new ArrayList<>();
            for (int[] prevCell : prevWaveCells) {
                for (int[] n : neighbours(map, prevCell)) {
                    if (fill[n[0]][n[1]] == 0) {
                        fill[n[0]][n[1]] = wave + 1;
                        curFilledCells.add(n);
                    }
                }
            }
            prevWaveCells = curFilledCells;
            canFind = prevWaveCells.size() > 0;
            wave++;
            printMap(fill);
            System.out.println();
        }
        if(canFind) {
            ArrayList<int[]> res = new ArrayList<>();
            int cur = wave;
            System.out.println(cur);
            int[] curCell = to;
            res.add(curCell);
            while (cur > 1) {
                int[] found = null;
                for (int[] n : neighbours(map, curCell)) {
                    if (fill[n[0]][n[1]] == cur - 1) found = n;
                }
                cur--;
                curCell = found;
                res.add(0, curCell);
            }
            return res;
        } return  null;

    }
}










