
public interface ListADT<I> {
    void insertFirst(I x);
    void deleteFirst();
    I firstItem();
    boolean isEmpty();
    boolean isFull();
}
