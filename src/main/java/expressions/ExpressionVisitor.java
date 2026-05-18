package expressions;

public interface ExpressionVisitor<R> {

    R visit(Num num);

    R visit(Add add);

    R visit(Mul mul);
}
