public class Practice06 {
	
	public static String pickAndRepeat(String s, int n, int r, int p) {
        char c = s.charAt(n);
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            if (i == p) {
                for (int j = 0; j < r; j++) {
                    result += c;
                } 
            }
            result += s.charAt(i);
        } 
        return result;
    }
    
    public static void main(String[] args) {
        System.out.println(pickAndRepeat("cart", 0, 3, 2));
        System.out.println(pickAndRepeat("cart", 1, 2, 3));
        System.out.println(pickAndRepeat("cart", 1, 1, 1));
        System.out.println(pickAndRepeat("cart", 3, 2, 1));
    }

}