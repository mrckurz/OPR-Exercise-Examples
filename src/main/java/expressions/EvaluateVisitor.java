package expressions;

public class EvaluateVisitor implements ExpressionVisitor<Double> {

    @Override
    public Double visit(Num num) {
        return num.getValue();
    }

    @Override
    public Double visit(Add add) {
        return add.getLeft().accept(this) + add.getRight().accept(this);
    }

    @Override
    public Double visit(Mul mul) {
        return mul.getLeft().accept(this) * mul.getRight().accept(this);
    }
}
