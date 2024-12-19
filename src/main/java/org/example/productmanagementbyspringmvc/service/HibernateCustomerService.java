package org.example.productmanagementbyspringmvc.service;

import org.example.productmanagementbyspringmvc.model.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.List;


public class HibernateCustomerService implements IHibernateICustomerService {

    private static SessionFactory sessionFactory;
    private static EntityManager entityManager;


    static {
        try {
            // Cấu hình và tạo SessionFactory từ Hibernate
            sessionFactory = new Configuration()
                    .configure("hibernate.conf.xml")  // Tên tệp cấu hình Hibernate
                    .buildSessionFactory();

            // Tạo EntityManager từ EntityManagerFactory
            entityManager = sessionFactory.createEntityManager();

        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Customer> findAll() {
        String queryStr = "SELECT c FROM Customer AS c";
        try {
            TypedQuery<Customer> query = entityManager.createQuery(queryStr, Customer.class);
            return query.getResultList();
        } catch (IllegalArgumentException e) {
            // Xử lý lỗi cú pháp hoặc tham số không hợp lệ
            System.err.println("Cú pháp JPQL hoặc thực thể không hợp lệ: " + e.getMessage());
            throw new RuntimeException("Lỗi truy vấn JPQL", e);
        } catch (PersistenceException e) {
            // Xử lý lỗi liên quan đến cơ sở dữ liệu
            System.err.println("Lỗi khi thực thi truy vấn: " + e.getMessage());
            throw new RuntimeException("Lỗi truy cập cơ sở dữ liệu", e);
        }
    }
    @Override
    public void save(Customer customer) {
        Transaction transaction = null;
        Customer origin;
        if (customer.getId() == 0) {
            origin = new Customer();
        } else {
            origin = findById(customer.getId());
        }
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            origin.setName(customer.getName());
            origin.setEmail(customer.getEmail());
            origin.setAddress(customer.getAddress());
            session.saveOrUpdate(origin);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public Customer findById(int id) {
        String queryStr = "SELECT c FROM Customer AS c WHERE c.id = :id";
        TypedQuery<Customer> query = entityManager.createQuery(queryStr, Customer.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public void remove(int id) {
        Customer customer = findById(id);
        if (customer != null) {
            Transaction transaction = null;
            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                session.remove(customer);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }



}
