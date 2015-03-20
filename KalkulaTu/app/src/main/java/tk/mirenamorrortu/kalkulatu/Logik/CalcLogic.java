package tk.mirenamorrortu.kalkulatu.Logik;

// File   : calc-ui-model/CalcLogic.java - The logic of a calculator.
// Description: This is the logic/model of a calculator.
//          It has no user interface, but could be called from
//          either a GUI or console user interface.
// Separating the model (logic) from the interface has advantages.
// In this program the model is small, so it may not be as obvious,
// but in larger programs the advantages can be substantial.
// 1. It is simpler for the developer to work with.
// 2. It can be used with many kinds of interfaces without changes.  Eg,
//    a GUI interface, a command-line interface, or a web-based interface.
// 3. The model can be changed (eg, to work with BigInteger) without
//    changing the user interface.  Of course, some changes require
//    interface changes, but the separation makes this easier.
//
// Author : Fred Swartz - 2004-11-17 + 2007-02-13 - Placed in public domain.

// Possible enhancements:
// * Change numbers to double, or BigInteger, or even Roman numerals!
//   This should be possible without the user interface knowing much
//   about the change (except perhaps to add a "." for floating-point input).
// * Add error checking (eg, division by zero checking.
//   How would you communicate an error to the caller? Ans: Exceptions.
// * Additional operations - change sign, mod, square root, ...

//////////////////////////////////////////////////////////////// class CalcLogic
public class CalcLogic {

    private double _currentTotal;

    /**
     * Constructor
     */
    public CalcLogic() {
        _currentTotal = 0;
    }

    public String getTotalString() {
        return String.valueOf(_currentTotal);
    }

    public void setTotal(double d) {
        _currentTotal = d;
    }

    public double add(String n) {
        _currentTotal += convertToNumber(n);

        return _currentTotal;
    }

    public double subtract(String n) {
        _currentTotal -= convertToNumber(n);

        return _currentTotal;
    }

    public double multiply(String n) {
        _currentTotal *= convertToNumber(n);

        return _currentTotal;
    }

    public double divide(String n) {
        _currentTotal /= convertToNumber(n);

        return _currentTotal;
    }

    private double convertToNumber(String n) {
        return Double.parseDouble(n);
    }
}
