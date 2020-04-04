public class IntList {
    public int first;
    public IntList rest;

    public static void main(String[] args) {
        IntList L = new IntList(5, null);
        L.rest = new IntList(10, null);
        L.rest.rest = new IntList(15, null);
        System.out.println(L.Get(2));
    }
    public IntList(int f, IntList r) {
        /** Return the size of the list using recursion */
        first = f;
        rest = r;
    }

    public int iterativeSize() {
        IntList p = this;
        int n = 0;
        while (p != null) {
           n += 1;
           p = p.rest;
        }
        return n;
    }

    public int Size() {
        if (this.rest == null) {
            return 1;
        } else {
            return 1 + this.rest.Size();
        }
    }

    //* returns the nth item of an intList */
    public int Get(int n) {
        if (n == 0) {
            return this.first;
        } else {
           return this.rest.Get(n - 1);
        }
    }
}
