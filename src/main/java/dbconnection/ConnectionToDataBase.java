package dbconnection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class ConnectionToDataBase {
    public static StandardServiceRegistry registry;
    public static Metadata metadata;
    public static SessionFactory sessionFactory;
    public static Session session;
    public static Transaction transaction;

    public static void setRegistry(StandardServiceRegistry registry) {
        ConnectionToDataBase.registry = registry;
    }

    public static void setMetadata(Metadata metadata) {
        ConnectionToDataBase.metadata = metadata;
    }

    public static void setSessionFactory(SessionFactory sessionFactory) {
        ConnectionToDataBase.sessionFactory = sessionFactory;
    }

    public static void setSession(Session session) {
        ConnectionToDataBase.session = session;
    }

    public static Session getSession() {
        registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        metadata = new MetadataSources(registry).getMetadataBuilder().build();
        sessionFactory = metadata.getSessionFactoryBuilder().build();
        session = sessionFactory.openSession();
        return session;
    }

    public static Transaction getTransaction() {
        transaction = session.beginTransaction();
        return transaction;
    }

    public static void setTransactionCommit() {
        transaction.commit();
    }

    public static void closeSessionFactory() {
        sessionFactory.close();
    }
}