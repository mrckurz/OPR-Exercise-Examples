package expressions;

public class Num extends Expression {

    private final double value;

    public Num(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
