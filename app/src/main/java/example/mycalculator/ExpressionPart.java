package example.mycalculator;

/**
 * ExpressionPart.java - Used to represent an expression part, or node, in an ExpressionTree.
 * @author  Chris Szendrovits
 */
public class ExpressionPart
{
    /**
     * Data members.
     */
    protected String part = "";
    protected ExpressionPartType type;

    protected ExpressionPart parentPart;
    protected ExpressionPart leftPart;
    protected ExpressionPart rightPart;

    /**
     * ExpressionPart class constructor, used to instantiate a new object.
     * @param part The value of the expression. Can be an operand or an operator.
     * @param type The type of the expression part.
     */
    public ExpressionPart(String part, ExpressionPartType type)
    {
        this.part = part;
        this.type = type;
    }

    /**
     * Accessors and modifiers.
     */
    public String getPart() { return part; }

    public void appendPart(String value) { part += value; }

    public ExpressionPartType getPartType() { return type; }

    public void setLeft(ExpressionPart expPart) { leftPart = expPart; }

    public void setRight(ExpressionPart expPart) { rightPart = expPart; }

    public void setParent(ExpressionPart expPart) { parentPart = expPart; }

    public ExpressionPart getLeft() { return leftPart; }

    public ExpressionPart getRight() { return rightPart; }

    public ExpressionPart getParent() { return parentPart; }

    public String getDisplayExpressionPart()
    {
        if (type == ExpressionPartType.Operand)
        {
            return part;
        }
        else
        {
            return " " + part + " ";
        }
    }

    /**
     * Add an ExpressionPart node, as a child of the current node.
     * @param expPart The child node, or part, to add.
     * @return Returns true if the node was added, false otherwise.
     */
    public boolean addChild(ExpressionPart expPart)
    {
        boolean bAdded = false;

        if (this.leftPart == null)
        {
            this.leftPart = expPart;
            bAdded = true;
        }
        else if (this.rightPart == null)
        {
            this.rightPart = expPart;
            bAdded = true;
        }

        if (bAdded) {
            ExpressionPart parent = expPart.getParent();

            if (parent != null) {
                if (parent.getLeft() == expPart) {
                    parent.setLeft(this);
                } else if (parent.getRight() == expPart) {
                    parent.setRight(this);
                }
            }

            expPart.setParent(this);
        }
        return bAdded;
    }

    /**
     * A comparison method, used to determine the order in which a node is placed
     * in a binary expression tree.
     * @param compPart The expression part to compare to.
     * @return Returns -1 if the node should be a child, 1 if it should be a right child and 0
     * if the node should be a parent.
     */
    public int compareTo(ExpressionPart compPart)
    {
        int result = 0;

        ExpressionPartType partType = this.getPartType();
        ExpressionPartType compPartType = compPart.getPartType();

        if (compPartType == ExpressionPartType.Operand)
        {
            return -1;
        }
        else if (partType == ExpressionPartType.Operand && compPartType != ExpressionPartType.Operand)
        {
            return 0;
        }
        else
        {
            if ((compPartType == ExpressionPartType.Multiplication || compPartType == ExpressionPartType.Division) &&
                (partType == ExpressionPartType.Addition || partType == ExpressionPartType.Subtraction))
            {
                return 1;
            }
            else if ((compPartType == ExpressionPartType.Addition || compPartType == ExpressionPartType.Subtraction) &&
                    (partType == ExpressionPartType.Multiplication || partType == ExpressionPartType.Division))
            {
                return 0;
            }
            else if ((compPartType == ExpressionPartType.Addition || compPartType == ExpressionPartType.Subtraction) &&
                    (partType == ExpressionPartType.Addition || partType == ExpressionPartType.Subtraction))
            {
                return 0;
            }
            else if ((compPartType == ExpressionPartType.Multiplication || compPartType == ExpressionPartType.Division) &&
                    (partType == ExpressionPartType.Multiplication || partType == ExpressionPartType.Division))
            {
                return 0;
            }
        }

        return result;
    }
}
