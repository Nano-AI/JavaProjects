/**
 * @author Aditya Bankoti
 * @date 09/25/2023
 * @descrption Meant to work as a reference similar to C++.
 * This fixes the "Variable used in lambda expression should be final or effectively final" error when attempting
 * to change values inside a lambda function.
 */
public class DoubleHolder extends Number {
    // instance var
    private double value;

    // function to set value of double
    public void setValue(double v) {
        this.value = v;
    }

    // constructor
    public DoubleHolder(double v) {
        this.value = v;
    }

    // returns the value
    public double getValue() {
        return this.value;
    }

    // returh value as string
    @Override
    public String toString() {
        return "" + this.value;
    }

    // return value as int
    @Override
    public int intValue() {
        return (int) value;
    }

    // rturn value as long
    @Override
    public long longValue() {
        return (long) value;
    }

    // return value as float
    @Override
    public float floatValue() {
        return (float) value;
    }

    // return value as double
    @Override
    public double doubleValue() {
        return value;
    }
}
