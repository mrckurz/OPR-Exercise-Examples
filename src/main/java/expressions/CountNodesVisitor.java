package expressions;

public class CountNodesVisitor implements ExpressionVisitor<Void> {

    private final boolean includeLeaves;
    private int count = 0;

    public CountNodesVisitor(boolean includeLeaves) {
        this.includeLeaves = includeLeaves;
    }

    public int getCount() {
        return count;
    }

    @Override
    public Void visit(Num num) {
        if (includeLeaves) {
            count++;
        }
        return null;
    }

    @Override
    public Void visit(Add add) {
        count++;
        add.getLeft().accept(this);
        add.getRight().accept(this);
        return null;
    }

    @Override
    public Void visit(Mul mul) {
        count++;
        mul.getLeft().accept(this);
        mul.getRight().accept(this);
        return null;
    }
}
