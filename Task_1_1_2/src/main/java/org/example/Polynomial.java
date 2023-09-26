package org.example;

public class Polynomial {
    int[] coeffs;

    public Polynomial(int[] ints) {
        this.coeffs = ints.clone();
    }

    public Polynomial plus(Polynomial that) {
        int[] newCoeffs = new int[Math.max(this.coeffs.length, that.coeffs.length)];
        Polynomial result = new Polynomial(newCoeffs);

        for (int i = 0; i < result.coeffs.length; i++) {
            if (i >= this.coeffs.length) {
                result.coeffs[i] = that.coeffs[i];
            } else if (i >= that.coeffs.length) {
                result.coeffs[i] = this.coeffs[i];
            } else {
                result.coeffs[i] = this.coeffs[i] + that.coeffs[i];
            }
        }
        return result;
    }

    public Polynomial minus(Polynomial that) {
        int[] newCoeffs = new int[Math.max(this.coeffs.length, that.coeffs.length)];
        Polynomial result = new Polynomial(newCoeffs);

        for (int i = 0; i < result.coeffs.length; i++) {
            if (i >= this.coeffs.length) {
                result.coeffs[i] = -that.coeffs[i];
            } else if (i >= that.coeffs.length) {
                result.coeffs[i] = this.coeffs[i];
            } else {
                result.coeffs[i] = this.coeffs[i] - that.coeffs[i];
            }
        }
        return result;
    }

    public Polynomial times(Polynomial that) {
        int[] newCoeffs = new int[this.coeffs.length + that.coeffs.length - 1];
        Polynomial result = new Polynomial(newCoeffs);

        for (int i = 0; i < that.coeffs.length; i++) {
            for (int j = 0; j < this.coeffs.length; j++) {
                result.coeffs[i + j] += this.coeffs[j] * that.coeffs[i];
            }
        }
        return result;
    }


    public int evaluate(int x){
        int result = 0;
        for(int i = 0; i < this.coeffs.length; i++){
            result += (int) (this.coeffs[i]*Math.pow(x,i));
        }
        return result;
    }

    public Polynomial differentiate(int order) { //order > 0

        Polynomial result;
        if (this.coeffs.length > 1) {
            result = new Polynomial(new int[this.coeffs.length - 1]);
        } else {
            return new Polynomial(new int[1]);
        }
        for (int i = 0; i < result.coeffs.length; i++) {
            result.coeffs[i] = this.coeffs[i + 1] * (i + 1);
        }
        if (order > 1) {
            result = result.differentiate(order - 1);
        }
        return result;
    }

    public boolean equals(Polynomial that) {
        if (this.coeffs.length != that.coeffs.length) {
            return false;
        }
        for (int i = 0; i < this.coeffs.length; i++) {
            if (this.coeffs[i] != that.coeffs[i]) {
                return false;
            }
        }
        return true;
    }


    public String toString() {
        String res = "";

        for (int i = coeffs.length - 1; i > 1; i--) {
            res = res.concat(coeffs[i] + "x^" + i + " + ");
        }

        if (coeffs.length > 1) {
            res = res.concat(coeffs[1] + "x + ");
        }

        res = res.concat(String.valueOf(coeffs[0]));

        return res;
    }

}
