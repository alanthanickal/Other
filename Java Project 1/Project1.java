/*
 * PROJECT I: Project1.java
 *
 * As in project 0, this file - and the others you downloaded - form a
 * template which should be modified to be fully functional.
 *
 * This file is the *last* file you should implement, as it depends on both
 * Point and Circle. Thus your tasks are to:
 *
 * 1) Make sure you have carefully read the project formulation. It contains
 *    the descriptions of all of the functions and variables below.
 * 2) Write the class Point.
 * 3) Write the class Circle
 * 4) Write this class, Project1. The results() method will perform the tasks
 *    laid out in the project formulation.
 */

import java.util.*;
import java.io.*;

public class Project1 {
    // -----------------------------------------------------------------------
    // Do not modify the names or types of the instance variables below! This 
    // is where you will store the results generated in the Results() function.
    // -----------------------------------------------------------------------
    public int      circleCounter; // Number of non-singular circles in the file.
    public double[] aabb;          // The bounding rectangle for the first and 
                                   // last circles
    public double   Smax;          // Area of the largest circle (by area).
    public double   Smin;          // Area of the smallest circle (by area).
    public double   areaAverage;   // Average area of the circles.
    public double   areaSD;        // Standard deviation of area of the circles.
    public double   areaMedian;    // Median of the area.
    public int      stamp = 220209;
    ArrayList<Circle> Circles = new ArrayList<Circle>(); 
     ArrayList<Double> Areas = new ArrayList<Double>(); 
    // -----------------------------------------------------------------------
    // You should implement - but *not* change the types, names or parameters of
    // the variables and functions below.
    // -----------------------------------------------------------------------

    /**
     * Default constructor for Project1. You should leave it empty.
     */
    public Project1() {
        // This method is complete.
    }

    /**
     * Results function. It should open the file called fileName (using
     * Scanner), and from it generate the statistics outlined in the project
     * formulation. These are then placed in the instance variables above.
     *
     * @param fileName  The name of the file containing the circle data.
     */
    public void results(String fileName){
      
    circleCounter = 0;
    double maxRAD = Double.MIN_VALUE;
    double minRAD = Double.MAX_VALUE;


      try (
      Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))
      ) {
      while(scanner.hasNext()) {
      
        //Read the three values on each line of the file
        double x = scanner.nextDouble();
        double y = scanner.nextDouble();
        double rad = scanner.nextDouble();

        if (rad > Point.GEOMTOL)
        {
            Circles.add(new Circle(x,y,rad));
            Areas.add((Math.PI * rad * rad));
            if (rad > maxRAD) {
          maxRAD = rad;
        }
        if (rad < minRAD) {
          minRAD = rad;
        }

            circleCounter++;
        }
    }
    Smax = Math.PI * maxRAD * maxRAD;
    Smin = Math.PI * minRAD * minRAD;
    Circle[] circlearray = Circles.toArray(new Circle[circleCounter]);
    areaAverage = averageCircleArea(circlearray);
    areaSD = areaStandardDeviation(circlearray);
    Collections.sort(Areas);
    Double[] areaarray = Areas.toArray(new Double[circleCounter]);
    if (circleCounter % 2 == 1){
        areaMedian = areaarray[(circleCounter - 1) /2];
    }
    else {
        areaMedian = 0.5 * (areaarray[(circleCounter - 1) /2] + areaarray[(circleCounter) /2]);
    }
    
    Circle[] selected = new Circle[]{circlearray[9], circlearray[19]};
    aabb = calculateAABB(selected);

    } catch(Exception e) {
      System.err.println("An error has occured. See below for details");
      e.printStackTrace();
    }
    }

    /**
     * A function to calculate the avarage area of circles in the array provided. 
     * This array may contain 0 or more circles.
     *
     * @param circles  An array of Circles
     */
    public double averageCircleArea(Circle[] circles) {
      double areasum, area, averagearea;
      areasum = 0;
      int number_nonsingular = 0;
    
      for (int i = 0; i < circles.length; i++) {
          double r = circles[i].getRadius();
          if (r > Point.GEOMTOL) {
               number_nonsingular++;
               area = circles[i].getRadius() * circles[i].getRadius() * Math.PI;
          areasum += area;
          }
      }
      if (number_nonsingular == 0) {
          return 0.0;
      }
        else {
        averagearea = areasum / number_nonsingular;
        return averagearea;
        }
    }
    
    /**
     * A function to calculate the standard deviation of areas in the circles in the array provided. 
     * This array may contain 0 or more circles.
     * 
     * @param circles  An array of Circles
     */
    public double areaStandardDeviation(Circle[] circles) {
        double sigmasquare = 0.0;
        double mu = averageCircleArea(circles);
        int number_nonsingular = 0;
        for (int i = 0; i < circles.length; i++) {
          double r = circles[i].getRadius();
          if (r > Point.GEOMTOL) {
               number_nonsingular++;
               double area = circles[i].getRadius() * circles[i].getRadius() * Math.PI;
                sigmasquare += area * area;
        }
        }
        if (number_nonsingular == 0) {
            return 0.0;
        }
        else {
            return Math.sqrt(sigmasquare / number_nonsingular - mu * mu);
          }
        
    }
    /**
     * Returns 4 values in an array [X1,Y1,X2,Y2] that define the rectangle
     * that surrounds the array of circles given.
     * This array may contain 0 or more circles.
     *
     * @param circles  An array of Circles
     * @return An array of doubles [X1,Y1,X2,Y2] that define the bounding rectangle with
     *         the origin (bottom left) at [X1,Y1] and opposite corner (top right)
     *         at [X2,Y2]
     */
    public double[] calculateAABB(Circle[] circles)
{
    double x1 = Double.MAX_VALUE;
    double x2 = Double.MIN_VALUE;
    double y1 = Double.MAX_VALUE;
    double y2 = Double.MIN_VALUE;

    int nonsc = 0;
    
    for (int k = 0; k < circles.length; k++) {
        double r = circles[k].getRadius(); 
        double X = circles[k].getCentre().getX();
        double Y = circles[k].getCentre().getY();
        if (r > Point.GEOMTOL) {
                nonsc++;
                if (X-r < x1) {
                    x1 = X-r;
                }
                if (Y-r < y1) {
                    y1 = Y-r;
                }
                if (X+r > x2) {
                    x2 = X+r;
                }
                if (Y+r > y2) {
                    y2 = Y+r;
                }
        }
        }
      if (nonsc == 0) {
        return new double[]{0.0,0.0,0.0,0.0};
      }
    else {
        return new double[]{x1,y1,x2,y2};
    }  
}


  
    // =======================================================
    // Tester - tests methods defined in this class
    // =======================================================

    /**
     * Your tester function should go here (see week 14 lecture notes if
     * you're confused). It is not tested by BOSS, but you will receive extra
     * credit if it is implemented in a sensible fashion.
     */
    public static void main(String args[]){
        Project1 myproject = new Project1();

        Circle A = new Circle(1,2,1);
        Circle B = new Circle(7,-3,3);
        Circle C = new Circle(5.3,1.3,1e-7);

        Circle[] array = new Circle[]{A,B,C};
        double avg = myproject.averageCircleArea(array);
        double sd = myproject.areaStandardDeviation(array);

        System.out.println(avg);
        System.out.println(sd);

        myproject.results("student.data");
        System.out.println(myproject.circleCounter);
        System.out.println(myproject.Smax);
        System.out.println(myproject.Smin);
        System.out.println(myproject.areaAverage);
        System.out.println(myproject.areaSD);
        System.out.println(myproject.areaMedian);
        System.out.println(Arrays.toString(myproject.aabb));



        

    }
}
