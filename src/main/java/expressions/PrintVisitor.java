package expressions;

public class PrintVisitor implements ExpressionVisitor<String> {

    @Override
    public String visit(Num num) {
        double v = num.getValue();
        if (v == Math.floor(v) && !Double.isInfinite(v)) {
            return Long.toString((long) v);
        }
        return Double.toString(v);
    }

    @Override
    public String visit(Add add) {
        return "(" + add.getLeft().accept(this) + " + " + add.getRight().accept(this) + ")";
    }

    @Override
    public String visit(Mul mul) {
        return "(" + mul.getLeft().accept(this) + " * " + mul.getRight().accept(this) + ")";
    }
}
