package com.example.model.controller;

import com.example.hibernate.demo.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class QueryStudentDemo {

    public static void main(String[] args) {

        //create session factory
        SessionFactory factory = new Configuration()
                                    .configure("hibernate.cfg.xml")
                                    .addAnnotatedClass(Student.class)
                                    .buildSessionFactory();

        //create session
        Session session = factory.getCurrentSession();

        try {

            //start a transaction
            session.beginTransaction();

            //query all students
            List<Student> theStudents = session.createQuery("from Student").getResultList();

            displayStudents(theStudents);

            //query students where email like somewhere
            theStudents = session.createQuery("from Student where email like '%somewhere%'").getResultList();

            //display the students
            displayStudents(theStudents);

            //commit transaction
            session.getTransaction().commit();

        }
        finally {
            factory.close();
            System.out.println("Done");
        }
    }

    private static void displayStudents(List<Student> theStudents) {
        for (Student stu : theStudents) {
            System.out.println(stu);
        }
    }
}
