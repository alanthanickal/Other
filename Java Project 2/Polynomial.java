/*
 * PROJECT II: Polynomial.java
 *
 * This file contains a template for the class Polynomial. Not all methods are
 * implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file.
 *
 * This class is designed to use Complex in order to represent polynomials
 * with complex co-efficients. It provides very basic functionality and there
 * are very few methods to implement! The project formulation contains a
 * complete description.
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
 * UNIVERSITY ID: U2103793
 * DEPARTMENT: Mathematics
 */

public class Polynomial {
    /**
     * An array storing the complex co-efficients of the polynomial.
     */
    Complex[] coeff;

    // ========================================================
    // Constructor functions.
    // ========================================================

    /**
     * General constructor: assigns this polynomial a given set of
     * co-efficients.
     *
     * @param coeff  The co-efficients to use for this polynomial.
     */
    public Polynomial(Complex[] coeff) {
        int deg = coeff.length - 1;
        for (int k=coeff.length - 1; k >= 0; k--) {
            if (coeff[k].getReal() == 0 && coeff[k].getImag() == 0) {
                deg -= 1;
            }
            else{
                break;
            }
        }
        this.coeff = new Complex[deg+1];
        if (deg == -1) {
            this.coeff = new Complex[]{new Complex(0.0, 0.0)};
        }
        else {
            for (int p=0; p <= deg; p++) 
             this.coeff[p] = coeff[p];
        }
    }
    /**
     * Default constructor: sets the Polynomial to the zero polynomial.
     */
    public Polynomial() {
        this.coeff = new Complex[]{new Complex(0.0, 0.0)};
    }

    // ========================================================
    // Operations and functions with polynomials.
    // ========================================================

    /**
     * Return the coefficients array.
     *
     * @return  The coefficients array.
     */
    public Complex[] getCoeff() {
        return coeff;
    }

    /**
     * Create a string representation of the polynomial.
     * Use z to represent the variable.  Include terms
     * with zero co-efficients up to the degree of the
     * polynomial.
     *
     * For example: (-5.000+5.000i) + (2.000-2.000i)z + (-1.000+0.000i)z^2
     */
    public String toString() {
        String X = "";
        for (int i=0; i < coeff.length; i++) {
            X += coeff[i].toString() + "z^" + i;
        }
        return X;
    }

    /**
     * Returns the degree of this polynomial.
     */
    public int degree() {
        return coeff.length - 1;
    }

    /**
     * Evaluates the polynomial at a given point z.
     *
     * @param z  The point at which to evaluate the polynomial
     * @return   The complex number P(z).
     */
    public Complex evaluate(Complex z) {
        Complex x = new Complex(0,0);
        for (int k=coeff.length - 1; k >= 1; k--) {
            x = x.add(coeff[k]);
            x = x.multiply(z);
        }
        x = x.add(coeff[0]);
        return x;
    }

    
    // ========================================================
    // Tester function.
    // ========================================================

    public static void main(String[] args) {
        // You can fill in this function with your own testing code.
        Complex a0 = new Complex(2, 3);
        Complex a1 = new Complex(-1.5, 2);
        Complex a2 = new Complex(3, 0);
        Complex a3 = new Complex(2, 1);


        Complex[] ls = new Complex[]{a0, a1, a2, a3};

        Polynomial y = new Polynomial(ls);

        Complex z = new Complex(2, 1);

        System.out.println(y);
    }
}