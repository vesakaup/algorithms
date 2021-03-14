import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final int[][] board;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        int n = tiles.length;
        this.board = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.board[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        int n = dimension();
        StringBuilder result = new StringBuilder();
        result.append(n);
        result.append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result.append(this.board[i][j]);
                result.append(this.board[i][j] < 10 ? "  " : " ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    // board dimension n
    public int dimension() {
        return this.board.length;
    }

    // number of tiles out of place
    public  int hamming() {

        int hamming = 0;
        int n = dimension();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
              
                if (this.board[i][j] != 0) {
                    if (this.board[i][j] != i * n + j + 1) {
                        hamming++;
                    }
                }
            }
        }

        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {

        int n = dimension();
        int manhattan = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int result = this.board[i][j];

   
                if (result != 0) {
                    int row = (result - 1) / n;
                    int col = (result - 1) % n;

                    manhattan += Math.abs(row - i);
                    manhattan += Math.abs(col - j);
                }
            }
        }

        return manhattan;
    }

    // is this board the goal board
    public boolean isGoal() {
        return hamming() == 0;
    }

    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }

        if (y.getClass() != this.getClass()) {
            return false;
        }

        Board x = (Board) y;

        int n = dimension();

        if (n != x.dimension()) {
            return false;
        }


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.board[i][j] != x.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private Board exchange(int row, int col, int nextRow, int nextCol) {
        int n = dimension();
        int[][] newBoard = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newBoard[i][j] = this.board[i][j];
            }
        }

        int tmp = newBoard[row][col];
        newBoard[row][col] = newBoard[nextRow][nextCol];
        newBoard[nextRow][nextCol] = tmp;
        return new Board(newBoard);
    }

    private Board neighborDirections(int row, int col, String direction) {
        int n = dimension();

        switch (direction) {
            case "u":
                if (row > 0) {
                    return exchange(row, col, row - 1, col);
                }
                break;
            case "d":
                if (row + 1 < n) {
                    return exchange(row, col, row + 1, col);
                }
                break;
            case "l":
                if (col > 0) {
                    return exchange(row, col, row, col - 1);
                }
                break;
            case "r":
                if (col + 1 < n) {
                    return exchange(row, col, row, col + 1);
                }
                break;
        }
        return null;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        int n = dimension();


        int emptyRow = -1;
        int emptyCol = -1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.board[i][j] == 0) {
                    emptyRow = i;
                    emptyCol = j;
                }
            }
        }

        List<Board> neighbors = new ArrayList<>();

        // top
        Board top = neighborDirections(emptyRow, emptyCol, "u");
        if (top != null) {
            neighbors.add(top);
        }

        // bottom
        Board bottom = neighborDirections(emptyRow, emptyCol, "d");
        if (bottom != null) {
            neighbors.add(bottom);
        }

        // left
        Board left = neighborDirections(emptyRow, emptyCol, "l");
        if (left != null) {
            neighbors.add(left);
        }


        // right
        Board right = neighborDirections(emptyRow, emptyCol, "r");
        if (right != null) {
            neighbors.add(right);
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        int n = dimension();

        int row1 = 0;
        int col1 = 0;
        int row2 = n - 1;
        int col2 = 0;

        while (this.board[row1][col1] == 0) {
            col1++;
        }
        while (this.board[row2][col2] == 0) {
            col2++;
        }

        return exchange(row1, col1, row2, col2);
    }

    public static void main(String[] args) {
        In in = new In(args[0]);

        int n = in.readInt();

        int[][] tiles = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        Board board1 = new Board(tiles);
        Board board2 = new Board(tiles);

        StdOut.println(board1.equals(board2));
    }
}