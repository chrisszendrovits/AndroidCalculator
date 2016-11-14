package example.mycalculator;

/**
 * ExpressionPartType.java - Enumeration for the various types of expression parts.
 * @author  Chris Szendrovits
 */
public enum ExpressionPartType
{
    Other (0, "Other"),
    Operand (1, "Operand"),
    Open_Parenthesis (2, "("),
    Close_Parenthesis (3, ")"),
    Multiplication (4, "x"),
    Division (5, "÷"),
    Addition (6, "+"),
    Subtraction (7, "-");

    public int code;
    public String name;

    private ExpressionPartType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * Converts an expression part code to an enumeration type.
     * @param code The code of the expression part.
     * @return Returns the enumeration type of the converted expression part.
     */
    public static ExpressionPartType fromInt(int code) {
        switch (code) {
            case 1:
                return Operand;
            case 2:
                return Open_Parenthesis;
            case 3:
                return Close_Parenthesis;
            case 4:
                return Multiplication;
            case 5:
                return Division;
            case 6:
                return Addition;
            case 7:
                return Subtraction;
            default:
                return Other;
        }
    }

    /**
     * Converts an expression part name to an enumeration type.
     * @param name The name of the expression part.
     * @return Returns the enumeration type of the converted expression part.
     */
    public static ExpressionPartType fromString(String name) {
        switch (name) {
            case "Operand":
                return Operand;
            case "(":
                return Open_Parenthesis;
            case ")":
                return Close_Parenthesis;
            case "x":
                return Multiplication;
            case "÷":
                return Division;
            case "+":
                return Addition;
            case "-":
                return Subtraction;
            default:
                return Other;
        }
    }
}
