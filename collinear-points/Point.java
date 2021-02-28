import java.util.Comparator;

public class Point implements Comparable<Point>{
	
	private int x;
	private int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void draw() {
		
	}
	
	public void drawTo(Point that) {
		
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	 
	 public int compareTo(Point that) {
	        if (this.y < that.y) {
	            return -1;
	        } 
	        else if (this.y > that.y) {
	            return 1;
	        } 
	        else if (this.x < that.x) {
	            return -1;
	        } 
	        else if (this.x > that.x) {
	            return 1;
	        } 
	        else {
	            return 0;
	        }
	            
	    }

	public double slopeTo(Point that) { // the slope between this point and that point
		if (that == null) {
			throw new NullPointerException ();
		}
		
		if (this.x == that.x) {
            if (this.y == that.y) {
                return Double.NEGATIVE_INFINITY;
            } else {
                return Double.POSITIVE_INFINITY;
            }
        } else {
            if (this.y == that.y) {
                return 0.0;
            } else {
                return 1.0 * (that.y - this.y) / (that.x - this.x);
            }
        }
	}
	
	public Comparator<Point> slopeOrder() { // compare two points by slopes they make with this point
        return new Comparator<Point>() {
        
            public int compare(Point a, Point b) {
                
            	double slope1 = slopeTo(a);
                double slope2 = slopeTo(b);
                
                if (slope1 < slope2) {
                    return -1;
                } else if (slope1 > slope2) {
                    return 1;
                } else {
                    return 0;
                }
            }
        };
	}
}
