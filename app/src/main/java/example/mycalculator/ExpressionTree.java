package example.mycalculator;

/**
 * ExpressionTree.java - a binary tree used for evaluating arithmetic expressions.
 * @author  Chris Szendrovits
 */
public class ExpressionTree
{
    ExpressionPart rootPart;

    /**
     * Returns the root part in the expression tree.
     * @return ExpressionPart The root part, or node.
     */
    public ExpressionPart getRoot() { return rootPart; }

    /**
     * Adds a new part of the expression to the binary tree.
     * @param newPart A new part of the expression.
     */
    public void addPart(ExpressionPart newPart)
    {
        if (rootPart == null) {
            rootPart = newPart;
        }
        else
        {
            ExpressionPart currentPart = rootPart;
            boolean bAdded = false;

            do
            {
                int result = currentPart.compareTo(newPart);

                if (result == -1)
                {
                    if ((bAdded = currentPart.addChild(newPart)) == false)
                    {
                        newPart.setParent(currentPart);
                        currentPart = currentPart.getRight();
                    }
                }
                else if (result == 0)
                {
                    bAdded = newPart.addChild(currentPart);
                }
                else if (result == 1)
                {
                    newPart.setParent(currentPart);
                    currentPart = currentPart.getRight();
                }

                if (newPart.getParent() == null)
                {
                    rootPart = newPart;
                }

            } while (!bAdded);
        }
    }

    /**
     * Evalulate all parts of the expression tree and return result.
     * @param expPart The node, or part, to begin the evaluation.
     * @return An ExpressionPart containing the result of the evaluation.
     */
    public ExpressionPart evaluate(ExpressionPart expPart)
    {
        ExpressionPart childPart = expPart.getLeft();

        if (childPart == null) {
            return expPart;
        }

        ExpressionPart leftPart = evaluate(expPart.getLeft());
        ExpressionPart rightPart = evaluate(expPart.getRight());

        return executeOperation(leftPart, expPart, rightPart);
    }

    /**
     * Execute an operator on its child nodes, and return the result.
     * @param leftPart The left part, or child node, of the operator.
     * @param rightPart The right part, or child node, of the operator.
     * @return An ExpressionPart containing the result of the operation.
     */
    protected ExpressionPart executeOperation(ExpressionPart leftPart,
                                              ExpressionPart operator,
                                              ExpressionPart rightPart)
    {
        double dblLeft = Double.parseDouble(leftPart.getPart());
        double dblRight = Double.parseDouble(rightPart.getPart());
        ExpressionPartType opType = operator.getPartType();

        double result = 0.0;

        if (opType == ExpressionPartType.Multiplication) {
            result = dblLeft * dblRight;
        }
        else if (opType == ExpressionPartType.Division) {
            result = dblLeft / dblRight;
        }
        else if (opType == ExpressionPartType.Addition) {
            result = dblLeft + dblRight;
        }
        else {
            result = dblLeft - dblRight;
        }

        if (result % 1 == 0) {
            return new ExpressionPart(String.valueOf((int)result), ExpressionPartType.Operand);
        }
        else {
            return new ExpressionPart(String.valueOf(result), ExpressionPartType.Operand);
        }
    }
}