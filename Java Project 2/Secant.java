/*
 * PROJECT II: Secant.java
 *
 * This file contains a template for the class Secant. Not all methods are
 * implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file.
 *
 * In this class, you will create a basic Java object responsible for
 * performing the Secant root finding method on a given polynomial
 * f(z) with complex co-efficients. The formulation outlines the method, as
 * well as the basic class structure, details of the instance variables and
 * how the class should operate.
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file! You should also test this class using the main()
 * function.
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 * 
 * Tasks:
 *
 * 1) Complete this class with the indicated methods and instance variables.
 *
 * 2) Fill in the following fields:
 *
 * NAME: Alan Thanickal
 * UNIVERSITY ID: 2103793
 * DEPARTMENT: Mathematics
 */

public class Secant {
    /**
     * The maximum number of iterations that should be used when applying
     * Secant. Ensure this is *small* (e.g. at most 50) otherwise your
     * program may appear to freeze.
     */
    public static final int MAXITER = 20;

    /**
     * The tolerance that should be used throughout this project. Note that
     * you should reference this variable and _not_ explicity write out
     * 1.0e-10 in your solution code. Other classes can access this tolerance
     * by using Secant.TOL.
     */
    public static final double TOL = 1.0e-10;

    /**
     * The polynomial we wish to apply the Secant method to.
     */
    private Polynomial f;


    /**
     * A root of the polynomial f corresponding to the root found by the
     * iterate() function below.
     */
    private Complex root;
    
    /**
     * The number of iterations required to reach within TOL of the root.
     */
    private int numIterations;

    /**
     * An enumeration that signifies errors that may occur in the root finding
     * process.
     *
     * Possible values are:
     *   OK: Nothing went wrong.
     *   ZERO: Difference went to zero during the algorithm.
     *   DNF: Reached MAXITER iterations (did not finish)
     */
    enum Error { OK, ZERO, DNF };
    private Error err = Error.OK;

    // ========================================================
    // Constructor functions.
    // ========================================================

    /**
     * Basic constructor.
     *
     * @param p  The polynomial used for Secant.
     */
    public Secant(Polynomial p) {
        this.f = p;
    }

    // ========================================================
    // Accessor methods.
    // ========================================================
    
    /**
     * Returns the current value of the err instance variable.
     */
    public Error getError() {
        return err;
    }

    /**
     * Returns the current value of the numIterations instance variable.
     */
    public int getNumIterations() { 
        return numIterations;
    }
    
    /**
     * Returns the current value of the root instance variable.
     */
    public Complex getRoot() {
        return root;
    }

    /**
     * Returns the polynomial associated with this object.
     */
    public Polynomial getF() {
        return f;
    }

    // ========================================================
    // Secant method (check the comment)
    // ========================================================
    
    /**
     * Given two complex numbers z0 and z1, apply Secant to the polynomial f in
     * order to find a root within tolerance TOL.
     *
     * One of three things may occur:
     *
     *   - The root is found, in which case, set root to the end result of the
     *     algorithm, numIterations to the number of iterations required to
     *     reach it and err to OK.
     *   - At some point the absolute difference between f(zn) and f(zn-1) becomes zero. 
     *     In this case, set err to ZERO and return.
     *   - After MAXITER iterations the algorithm has not converged. In this 
     *     case set err to DNF and return.
     *
     * @param z0,z1  The initial starting points for the algorithm.
     */
    public void iterate(Complex z0, Complex z1) {
        int k = 1;
        numIterations = 0;
        while (k <= MAXITER) {
            if (((z0.subtract(z1).abs())< TOL) && ((f.evaluate(z1).abs()< TOL))) {
                err = Error.OK;
                root = z1;
                numIterations = k;
                break;
            }

            k++;
            
            Complex denominator = (f.evaluate(z1)).subtract(f.evaluate(z0));
            if (denominator.getReal() == 0.0 && denominator.getImag() == 0.0) {
                err = Error.ZERO;
                numIterations = k;
                break;
            }

            Complex ratio = (z1.subtract(z0)).divide(denominator);
            Complex first = (f.evaluate(z1)).multiply(ratio);
            Complex z2 = z1.subtract(first);
            z0 = z1;
            z1 = z2;
        }
        
        k--;

        if ((err != Error.ZERO) && (((z0.subtract(z1).abs())>= TOL) || ((f.evaluate(z1).abs()>= TOL)))) {
            err = Error.DNF;
            numIterations = k;
        } else if (err != Error.ZERO) {
            err = Error.OK;
            numIterations = k;
            root = z1;
        }
    }
    // ========================================================
    // Tester function.
    // ========================================================
    public static void main(String[] args) {
        // Basic tester: find a root of f(z) = z^3-1.
        Complex[] coeff = new Complex[] {new Complex(1.0,1.0),
            new Complex(),
            new Complex(),
            new Complex(),
            new Complex(1.0,0.0)};
Polynomial p    = new Polynomial(coeff);
Secant     s    = new Secant(p);

Complex z0 = new Complex();
Complex z1 = new Complex(1.0, 0.0);
s.iterate(z0, z1);
System.out.println("root = " + s.getRoot());
System.out.println("numIterations = " + s.getNumIterations());   // 12
System.out.println("err = " + s.getError());           // OK
System.out.println();  // blank line

    }
}
