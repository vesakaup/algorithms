import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {
	
	private final TreeSet<Point2D> points;
	
	// construct an empty set of points 
	public PointSET() {
	 points = new TreeSet<Point2D>();
	}
	
	// is the set empty?
	public boolean isEmpty() {
		return points.isEmpty();
	}
	
	 // number of points in the set 
	public int size() {
		return points.size();
	}
	
	// add the point to the set (if it is not already in the set)
	public void insert (Point2D p) {
		if (p == null) throw new IllegalArgumentException("null argument");
		points.add(p);
	}
	
	// does the set contain point p?
	public boolean contains(Point2D p) {
		if (p == null) throw new IllegalArgumentException("null argument");
		return points.contains(p);
	}
	
	// draw all points to standard draw 
	public void draw() {
		
		for (Point2D p : points) {
			p.draw();
		}
	}
	
	// all points that are inside the rectangle (or on the boundary)
	public Iterable<Point2D> range(RectHV rect){
		  if (rect == null) throw new IllegalArgumentException("null argument");

	        List<Point2D> list = new ArrayList<Point2D>();

	        for (Point2D p: points) {
	            if (rect.contains(p)) {
	                list.add(p);
	            }
	        }

	        return list;
	}
	
	// a nearest neighbor in the set to point p; null if the set is empty
	 public Point2D nearest(Point2D p) {
		 if (p == null) throw new IllegalArgumentException("null argument");
		 
		   Point2D result = null;
	        double nearest = Double.POSITIVE_INFINITY;

	        if (isEmpty()) {
	            return result;
	        }

	        for (Point2D other: points) {
	            double distance = p.distanceSquaredTo(other);

	            if (distance < nearest) {
	                nearest = distance;
	                result = other;
	            }
	        }

	        return result;
	    }
	 
	 public static void main(String[] args) {
	   // unit testing
		
	 }
}

