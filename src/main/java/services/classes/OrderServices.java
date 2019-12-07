package services.classes;


import models.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.interfaces.IOrderService;
import utils.CreateList;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class OrderServices implements IOrderService {
    private static List<Order> orderList;

    @Override
    public List<Order> findAll(Session session, CriteriaBuilder builder) {
        List<Order> orderList = CreateList.genericListCreator(builder, session, Order.class);
        if(OrderServices.orderList == null)
            OrderServices.orderList = orderList;
        return orderList;
    }

    @Override
    public Order getOne(String Id) {
        if (orderList == null) {
            return null;
        } else {
            for (Order account : orderList) {
                if (account.getOrderId() == Integer.parseInt(Id))
                    return account;
            }
            //todo make exception
            return null;
        }
    }

    @Override
    public Order createOne(Order entity, Session session) {
        if (orderList == null) {
            return null;
        }
        try {
            Transaction tx = session.beginTransaction();

            session.save(entity);

            if (tx.isActive())
                session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
            entity = null;
        }
        return entity;
    }

    @Override
    public void deleteByID(String ID, Session session) {
        if (orderList == null) {
            return;
        }
        try {
            Transaction tx = session.beginTransaction();

            Order entity = getOne(ID);
            session.delete(entity);

            if (tx.isActive())
                session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public Order updateByID(Order update, Session session) {
        if (orderList == null) {
            return null;
        }
        try {
            Transaction tx = session.beginTransaction();

            session.update(update);

            if (tx.isActive())
                session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
            update = null;
        }
        return update;
    }
}
