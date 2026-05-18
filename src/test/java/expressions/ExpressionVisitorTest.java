package expressions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExpressionVisitorTest {

    // tree = (2 + 3) * (4 + 5)
    private Expression tree;

    @BeforeEach
    void setUp() {
        tree = new Mul(
                new Add(new Num(2), new Num(3)),
                new Add(new Num(4), new Num(5)));
    }

    @Test
    void printVisitorProducesInfixNotationWithParentheses() {
        String text = tree.accept(new PrintVisitor());
        assertEquals("((2 + 3) * (4 + 5))", text);
    }

    @Test
    void evaluateVisitorComputesNumericValue() {
        double value = tree.accept(new EvaluateVisitor());
        assertEquals(45.0, value, 1e-9);
    }

    @Test
    void countNodesIncludingLeavesCountsEveryNode() {
        CountNodesVisitor visitor = new CountNodesVisitor(true);
        tree.accept(visitor);
        // 4 Num + 2 Add + 1 Mul = 7
        assertEquals(7, visitor.getCount());
    }

    @Test
    void countNodesExcludingLeavesCountsOnlyComposites() {
        CountNodesVisitor visitor = new CountNodesVisitor(false);
        tree.accept(visitor);
        // 2 Add + 1 Mul = 3
        assertEquals(3, visitor.getCount());
    }

    @Test
    void evaluateOfPrintedTreeMatchesOriginalValue() {
        // Konsistenz-Check: derselbe Baum, zwei verschiedene Sichten.
        String text = tree.accept(new PrintVisitor());
        double value = tree.accept(new EvaluateVisitor());
        assertEquals("((2 + 3) * (4 + 5))", text);
        assertEquals(45.0, value, 1e-9);
    }

    @Test
    void singleLeafIsHandledCorrectly() {
        Expression leaf = new Num(42);
        assertEquals("42", leaf.accept(new PrintVisitor()));
        assertEquals(42.0, leaf.accept(new EvaluateVisitor()), 1e-9);

        CountNodesVisitor counter = new CountNodesVisitor(true);
        leaf.accept(counter);
        assertEquals(1, counter.getCount());
    }
}
