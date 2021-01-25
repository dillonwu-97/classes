public class practice8 {
    public static void main(String[] args) {
        int[] x = {4, 6, 8};
        System.out.println(f(x, 0));
    }
    
    public static int f(int[] x, int i) {
        if (i == x.length) {
            System.out.println("stop");
            return -1;
        } else {
            if (i % 2 == 0) {
                System.out.println("even " + i + " " + x[i]);
                return x[i] / 2 + f(x, i + 1);
            } else {
                System.out.println("odd " + i + " " + x[i]);
                return x[i] * 2 + f(x, i + 1);
            }
        }
    }
}