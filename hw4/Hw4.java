import java.util.*;
import java.io.*;

public class Hw4 {
  public static int[] byX, byY;
  public static boolean sortX = true;
  public static void main (String[] args) {
    List<Point> points = new ArrayList<Point>();
    File file = new File(args[0]);
    String temp;
    double minDist;
    String[] tempArr = new String[2];
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      int n = Integer.parseInt(br.readLine());
      while (n != 0) {
            for (int i=0; i<n; i++) {
              temp = br.readLine();
              tempArr = temp.split(" ");
              points.add(new Point(tempArr[0], tempArr[1]));
            }
            //System.out.println("****************");
            process(points);
            points = new ArrayList<Point>();
            n = Integer.parseInt(br.readLine());
      }
    } catch (Exception e) {
      e.printStackTrace(); 
    }
  }


 public static double findMinDist(List<Point> points, int beg, int end) {
  double minR, minL, d;
  if ((end - beg)<4) {
    return minSquaredDist(points, beg, end);
  }
  minR = findMinDist(points, (beg+end)/2, end);
  minL = findMinDist(points, beg, (beg+end)/2);
  d = Math.min(minR, minL);
  return Math.min(d, findMidMin(points, beg, end, d));
 }

 public static double findMidMin(List<Point> points, int beg, int end, double d) { 
    Set<Point> stripSet = new TreeSet<Point>();
    int mid = (beg + end)/2;
    int start = mid;
    int finish = mid;
    sortX = false;
    while ((start > 0) && (d > Math.abs(points.get(mid).x - points.get(start).x))) stripSet.add(points.get(start--)); 
    while ((finish < points.size()-1) && (d > Math.abs(points.get(mid).x - points.get(finish).x))) stripSet.add(points.get(finish++));
    Point[] strip = stripSet.toArray(new Point[stripSet.size()]);
    for (int i=0; i<=strip.length; i++) {
      for (int j=i+1; j<=Math.min(i+7,strip.length-1); j++) {
        d = Math.min(d, distance(strip[i], strip[j]));
      }
    }
    return d;
 }

 public static double minSquaredDist(List<Point> points, int beg, int end) {
    double min = distance(points.get(beg), points.get(beg+1));
    if (end - beg == 3) {
      min = Math.min(min, distance(points.get(beg), points.get(beg+2)));
      min = Math.min(min, distance(points.get(beg+1), points.get(beg+2)));
    }
    return min;
 }

 public static double distance (Point a, Point b) {
  return Math.sqrt((a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y));
 }

 public static void process(List<Point> points) {
     sortX = true;
     Collections.sort(points);
     for (Point p:points) {
        //System.out.println(p);
     }
     cleanOutput(findMinDist(points,0,points.size()));
     }
 
 public static void cleanOutput(double output) {
  if (output>1000.0) System.out.println("infinity");
  else System.out.printf("%.4f\n",output);
 }

static class Point implements Comparable<Point>{
  public double x,y;
  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }
  public Point(String x, String y) {
    this.x = Double.parseDouble(x);
    this.y = Double.parseDouble(y);
  }
  public String toString() {
    return "(" + this.x + " " + this.y + ")";
  }
  public int compareTo(Point p) {
    if (Hw4.sortX) {
      return (int)Math.floor(this.x - p.x);
    } else {
      return (int)Math.floor(this.y - p.y);
    }
  }
}
}
