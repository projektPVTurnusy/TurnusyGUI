package Data;

public class FuzzyNumber
{
    private double left;

    private double main;
    private double right;

    public FuzzyNumber(double left, double main, double right)
    {

        this.left = left;
        this.main = main;
        this.right = right;
    }

    public double getLeft() {
        return left;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public double getMain() {
        return main;
    }

    public void setMain(double main) {
        this.main = main;
    }

    public double getRight() {
        return right;
    }

    public void setRight(double right) {
        this.right = right;
    }
}
