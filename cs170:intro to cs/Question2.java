public class Question2 {
public static void main(String[] args) {
Parent p = new Parent(3);
System.out.println(p.test());
p = new Child (2,1);
System.out.println(p.test());
p = new Grandchild(2,2,1);
System.out.println(p.test());
}
}

class Parent {
private int var1;
public Parent (int x) {
var1 = x;
}
public int method1() {
var1 = 2 + var1;
return var1;
}
public int method2() {
var1 = var1 - 1;
return var1;
}
public int test() {
return method1() + method2();
}
}
class Child extends Parent {
private int var2;
public Child (int x, int y) {
super(x);
var2 = y;
}
public int method1() {
var2 = super.method1() - var2;
return var2;
}
}
class Grandchild extends Child {
private int var3;
public Grandchild (int x, int y, int z) {
super(x, y);
this.var3 = z;
}
public int method2() {
return method1() - var3;
}
}