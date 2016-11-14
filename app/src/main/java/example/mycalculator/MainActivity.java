package example.mycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * MainActivity.java
 * @author  Chris Szendrovits
 */
public class MainActivity extends AppCompatActivity
{
    protected LinkedList<ExpressionPart> listExpression = new LinkedList<ExpressionPart>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnNumberOnClick(View view)
    {
        if (view instanceof Button) {
            Button btn = (Button)view;
            String value = btn.getContentDescription().toString();

            ExpressionPart expPart = listExpression.peekLast();

            if (expPart == null || expPart.getPartType() != ExpressionPartType.Operand) {
                listExpression.add(new ExpressionPart(value, ExpressionPartType.Operand));
            }
            else
            {
                boolean bAppend = true;

                if (value.equalsIgnoreCase(".") && expPart.getPart().contains("."))
                {
                    bAppend = false;
                }

                if (bAppend) {
                    expPart.appendPart(value);
                }
            }
            DisplayExpression(listExpression);
        }
    }

    public void btnOperatorOnClick(View view)
    {
        if (view instanceof Button) {
            Button btn = (Button)view;
            String op = btn.getContentDescription().toString();

            ExpressionPart expPart = listExpression.peekLast();

            if (expPart != null && expPart.getPartType() == ExpressionPartType.Operand)
            {
                ExpressionPartType type = ExpressionPartType.fromString(op);
                listExpression.add(new ExpressionPart(op, type));
                DisplayExpression(listExpression);
            }
        }
    }

    public void btnDeleteOnClick(View view)
    {
        if (view instanceof Button)
        {
            ExpressionPart expPart = listExpression.peekLast();

            if (expPart != null)
            {
                listExpression.removeLast();
                DisplayExpression(listExpression);
            }
        }
    }

    public void btnClearOnClick(View view)
    {
        if (view instanceof Button)
        {
            listExpression.clear();
            DisplayExpression(listExpression);
        }
    }

    protected void DisplayExpression(LinkedList<ExpressionPart> lstExpression)
    {
        TextView tv = (TextView)findViewById(R.id.tvOutput);

        String output = "";

        for (ExpressionPart expPart : lstExpression) {
            output += ((ExpressionPart)expPart).getDisplayExpressionPart();
        }
        tv.setText(output);
    }

    public void btnEvaluateOnClick(View view)
    {
        if (view instanceof Button && isExpressionValid(listExpression))
        {
            ExpressionTree tree = new ExpressionTree();

            for (ExpressionPart expPart : listExpression)
            {
                tree.addPart(expPart);
            }

            ExpressionPart result;

            try {
                result = tree.evaluate(tree.getRoot());
            }
            catch (Exception ex) {
                result = new ExpressionPart("Error", ExpressionPartType.Other);
            }

            listExpression.clear();
            listExpression.add(result);

            DisplayExpression(listExpression);
        }
    }

    protected boolean isExpressionValid(LinkedList<ExpressionPart> lstExpression)
    {
        boolean isValid = true;

        ExpressionPart lastPart = lstExpression.peekLast();

        if (lstExpression.size() == 0)
        {
            isValid = false;
        }
        else if (lastPart.getPart().endsWith("."))
        {
            isValid = false;
        }
        else if (lastPart.getPartType() != ExpressionPartType.Operand)
        {
            isValid = false;
        }
        return isValid;
    }
}