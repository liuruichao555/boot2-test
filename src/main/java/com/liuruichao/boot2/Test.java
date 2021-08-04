package com.liuruichao.boot2;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Test
 *
 * @author ruichao.liu
 * Created on 2021-07-02 09:52
 */
public class Test {
    public static void main(String[] args) {

    }

    private static class Student {
        private int id;

        private String name;

        private int age;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            Student student = (Student) o;

            return new EqualsBuilder().append(id, student.id).append(age, student.age).append(name, student.name).isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37).append(id).append(name).append(age).toHashCode();
        }
    }
}
