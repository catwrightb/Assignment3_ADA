package Archive;

/**
 *
 * @author Seth
 */

//Testing class IGNORE
public class ExpNode {
    public ExpNode leftChild;
    public ExpNode rightChild;
    protected String symbol;

    public ExpNode(String value)
    {
        this.symbol = value;
        leftChild = null;
        rightChild = null;
    }

    //public abstract double evaluate() throws ArithmeticException;

    @Override
    public String toString()
    {
        return symbol;
    }

}
