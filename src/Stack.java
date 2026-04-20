public class Stack {
    private int maxSize;
    private int[] stackArray;
    private int top;

    public Stack(int s) {
        maxSize = s;
        stackArray = new int[maxSize];
        top = -1;
    }

    public void push(int j) throws StackFullException {
        if(isFull()) {
            throw new StackFullException("Value " + j + " not added to Stack --> full");
        }
        stackArray[++top] = j;
    }

    public int pop() throws StackEmptyException {
        if(isEmpty()) {
            throw new StackEmptyException("Stack is empty", maxSize);
        }
        return stackArray[top--];
    }

    public int peek() {
        return stackArray[top];
    }

    private boolean isEmpty() {
        return (top == -1);
    }

    private boolean isFull() {
        return (top == maxSize - 1);
    }
    
}
