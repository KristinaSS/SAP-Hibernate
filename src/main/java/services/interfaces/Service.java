package services.interfaces;


import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;


public interface Service<T> {

    List<T> findAll(Session session, CriteriaBuilder builder);

    T getOne(String Id);

    void deleteByID(String id, Session session);

    T createOne(T entity, Session session);

    T updateByID(T entity,Session session);

}
