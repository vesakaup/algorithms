import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF bwuf;
    private final boolean[] isOpen;
    private final int top;
    private final int bottom;
    private final int n;
    private int openCount;

    
    // Create n-by-n grid, with all sites blocked
   
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0.");
        }
        this.n = n;
        top = 0;
        bottom = n * n + 1;
        bwuf = new WeightedQuickUnionUF(n * n + 2);
        uf = new WeightedQuickUnionUF(n * n + 1);  // wihout bottom index
        isOpen = new boolean[n * n + 2];
        isOpen[top] = true;
        isOpen[bottom] = true;
    }

    
    // Convert a 2D coordinate to 1D.
   
    private int indexOf(int row, int col) {
        // check bounds
        if (row < 1 || row > n) {
            throw new IllegalArgumentException("Row is out of bounds.");
        }
        if (col < 1 || col > n) {
            throw new IllegalArgumentException("Column is out of bounds.");
        }
        return (row - 1) * n + col;
    }

    
     // Open site (row, col) if it is not open already
   
    public void open(int row, int col) {
	int currIndex = indexOf(row, col);
	if (isOpen[currIndex]){
		return;
	}
        
        isOpen[currIndex] = true;
        openCount++;

        if (row == 1) {
            bwuf.union(currIndex, top);  // Top
            uf.union(currIndex, top);
        }
        if (row == n) {
            bwuf.union(currIndex, bottom);  // Bottom
        }
        tryUnion(row, col, row - 1, col);  // North 
        tryUnion(row, col, row + 1, col);  // South        
        tryUnion(row, col, row, col - 1);  // West
        tryUnion(row, col, row, col + 1);  // East
    }

    private void tryUnion(int rowA, int colA, int rowB, int colB) {
      
        if (0 < rowB && rowB <= n && 0 < colB && colB <= n
                && isOpen(rowB, colB)) {
            bwuf.union(indexOf(rowA, colA), indexOf(rowB, colB));
            uf.union(indexOf(rowA, colA), indexOf(rowB, colB));
        }
    }
    
    
    // Returns the number of open sites
    
    public int numberOfOpenSites(){
        return openCount;
    }

    
     //Is site (row, col) open?
    
     
    public boolean isOpen(int row, int col) {
        return isOpen[indexOf(row, col)];
    }

    // Is site (row, col) full?
   
    public boolean isFull(int row, int col) {
      
    	return uf.find(top)==uf.find(indexOf(row,col));
    }

    
    // Does the system percolate?
     
    public boolean percolates() {
       
    	return bwuf.find(top)==bwuf.find(bottom);
    }

    public static void main(String[] args) {
        // no body
    }
}