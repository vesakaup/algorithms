import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class KdTree {
    private static class KdNode {
        private Point2D p;
        private RectHV rect;
        private KdTree.KdNode left, right;
     

        public KdNode(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
            left = null;
            right = null;
        }
    }

    private KdNode root;
    private int size;
    private RectHV canvas = new RectHV(0, 0, 1, 1);

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private int compareTo(Point2D a, Point2D b, int isVertical) {
        if (a.equals(b)) return 0;
        if (isVertical == 1) {
            if (a.x() < b.x()) return -1;
            else return 1;
        } else {
            if (a.y() < b.y()) return -1;
            else return 1;
        }
    }

    public void insert(Point2D p) {
        root = insert(root, p, 1,
                canvas.xmin(), canvas.ymin(), canvas.xmax(), canvas.ymax());
    }

    private KdNode insert(KdNode node, Point2D p, int isVertical,
                         double xmin, double ymin, double xmax, double ymax) {
        if (node == null) {
            size++;
            return new KdNode(p, new RectHV(xmin, ymin, xmax, ymax));
        }
        int cmpResult = compareTo(p, node.p, isVertical);
        double xxmin = xmin, yymin = ymin, xxmax = xmax, yymax = ymax;
        if (cmpResult < 0) {
            if (isVertical == 1) xxmax = node.p.x();
            else yymax = node.p.y();
            node.left = insert(node.left, p, 1-isVertical, xxmin, yymin, xxmax, yymax);
        }
        if (cmpResult > 0) {
            if (isVertical == 1) xxmin = node.p.x();
            else yymin = node.p.y();
            node.right = insert(node.right, p, 1-isVertical, xxmin, yymin, xxmax, yymax);
        }
        return node;
    }

    public boolean contains(Point2D p) {
	if(p==null)new IllegalArgumentException("null argument");
        return contains(root, p, 1);
    }

    private boolean contains(KdNode node, Point2D p, int isVertical) {
	
        if (node == null) return false;
        int cmpResult = compareTo(p, node.p, isVertical);
        if (cmpResult < 0)
            return contains(node.left, p, 1-isVertical);
        if (cmpResult > 0)
            return  contains(node.right, p, 1-isVertical);
        return true;
    }

    public void draw() {
        StdDraw.setScale();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        canvas.draw();
        draw(root, 1);
    }

    private void draw(KdNode node, int isVertical) {
        if (node == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.p.draw();
        if (isVertical == 1) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }
        draw(node.left, 1-isVertical);
        draw(node.right, 1-isVertical);
    }

    public Iterable<Point2D> range(RectHV rect) {
	if(rect==null)new IllegalArgumentException("null argument");
        Queue<Point2D> inPointQ = new Queue<Point2D>();
        Queue<KdNode> needSearchArea = new Queue<KdNode>();
        if (root == null) return inPointQ;
        needSearchArea.enqueue(root);
        while (!needSearchArea.isEmpty()) {
            KdNode node = needSearchArea.dequeue();

            if (rect.contains(node.p)) inPointQ.enqueue(node.p);
            if (node.left != null && rect.intersects(node.left.rect))
                needSearchArea.enqueue(node.left);
            if (node.right != null && rect.intersects(node.right.rect))
                needSearchArea.enqueue(node.right);
        }
        return inPointQ;
    }

    public Point2D nearest(Point2D p) {
	if (p == null) throw new IllegalArgumentException("null argument");

        if (root == null) return null;
        Stack<KdNode> nodeStack = new Stack<KdNode>();
        Point2D nearestPoint = null;
        double minDistance = Double.POSITIVE_INFINITY;
        nodeStack.push(root);
        while (!nodeStack.isEmpty()) {
            KdNode node = nodeStack.pop();
            double distance = node.p.distanceSquaredTo(p);
            if (distance < minDistance) {
                minDistance = distance;
                nearestPoint = node.p;
            }
            if (node.left != null && node.left.rect.contains(p)) {
                if (node.right != null && node.right.rect.distanceSquaredTo(p) < minDistance)
                    nodeStack.push(node.right);
                nodeStack.push(node.left);
            } else if (node.right != null && node.right.rect.contains(p)) {
                if (node.left != null && node.left.rect.distanceSquaredTo(p) < minDistance)
                    nodeStack.push(node.left);
                nodeStack.push(node.right);
            } else {
                if (node.left != null && node.left.rect.distanceSquaredTo(p) < minDistance)
                    nodeStack.push(node.left);
                if (node.right != null && node.right.rect.distanceSquaredTo(p) < minDistance)
                    nodeStack.push(node.right);
            }
        }
        return nearestPoint;
    }

    public static void main(String[] args) {

    }
}