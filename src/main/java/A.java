/**
 * @author Alexey Katsman
 * @since 2019-01-29
 */

import java.util.*;

public class A {

    public void a() {
        int x = 2;
        int y = x | 0;
        int z = y | 0;
        z = z;
    }

    public void b() {
        Boolean a = null;
        Boolean b = null;

        if (a && b) {
            if (a) {

            }
        }
    }

    public void c() {
        Boolean a = null;
        Boolean b = null;
        Boolean c = null;
        Boolean d = null;
        Boolean e = null;
        if ((a || b) && (c && d) && !e) {
            if ((!c && e) || d) {

            }
        }
    }
}
