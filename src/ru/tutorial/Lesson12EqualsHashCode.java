package ru.tutorial;

import java.util.Objects;

public class Lesson12EqualsHashCode {
    class A{
        int b;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            A a = (A) o;
            return b == a.b;
        }

        @Override
        public int hashCode() {
            return Objects.hash(b);
        }
    }
}
