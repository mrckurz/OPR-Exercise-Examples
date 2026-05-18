package expressions;

public class App {

    public static void main(String[] args) {
        // Baum fuer:  (2 + 3) * (4 + 5)
        Expression tree = new Mul(
                new Add(new Num(2), new Num(3)),
                new Add(new Num(4), new Num(5)));

        String text = tree.accept(new PrintVisitor());
        double value = tree.accept(new EvaluateVisitor());

        CountNodesVisitor allNodes = new CountNodesVisitor(true);
        tree.accept(allNodes);

        CountNodesVisitor innerNodes = new CountNodesVisitor(false);
        tree.accept(innerNodes);

        System.out.println("Ausdruck       = " + text);
        System.out.println("Ergebnis       = " + value);
        System.out.println("# Knoten ges.  = " + allNodes.getCount());
        System.out.println("# Knoten innen = " + innerNodes.getCount());
    }
}
