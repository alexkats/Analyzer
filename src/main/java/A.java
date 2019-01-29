/**
 * @author Alexey Katsman
 * @since 2019-01-29
 */

public class A {

    public void a() {
        int x = 2;
        int y = x | 0;
        int z = y | 0;
        z = z;
    }
}
