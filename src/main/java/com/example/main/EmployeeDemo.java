package com.example.main;

import com.example.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.text.ParseException;
import java.util.List;

public class EmployeeDemo {

    public static void main(String[] args) {

        //create session factory
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Employee.class)
                .buildSessionFactory();

        //create session
        Session session = factory.getCurrentSession();

        try {
            int id = 1;

            //begin transaction
            session.beginTransaction();

            List<Employee> employees = session.createQuery("from Employee where id=" + id).getResultList();

            if (employees.isEmpty()) {
                try {
                    session.save(new Employee("Darth", "Vader", "Imperials", DateUtils.parseDate("01/01/1929")));
                    session.save(new Employee("Launch", "Pad", "Duck Tales", DateUtils.parseDate("11/11/2011")));
                    session.save(new Employee("Snake", "Eyes", "GI Joe", DateUtils.parseDate("09/09/2009")));
                }
                catch (ParseException p) {
                    System.out.println(p);
                }
            }

            //commit changes, end transaction
            session.getTransaction().commit();

            for(Employee emp : employees) {
                System.out.println("\n" + emp.toString() + "\n");
            }

            session = factory.getCurrentSession();
            session.beginTransaction();

            employees = session.createQuery("from Employee where company='Duck Tales'").getResultList();
            if (!employees.isEmpty()) {
                session.delete(employees.get(0));
            }
            session.getTransaction().commit();

            for(Employee emp : employees) {
                System.out.println("\n" + emp.toString() + "\n");
            }
        }
        finally {

            //close session
            session.close();
        }

    }
}
