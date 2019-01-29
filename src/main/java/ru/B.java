package ru;

/**
 * @author Alexey Katsman
 * @since 2019-01-29
 */

public class B {

    int b() {
        int a = 0;
        a = a;

        while (a != 0) {

        }

        a |= a;

        synchronized (this) {

        }

        return 0;
    }
}
