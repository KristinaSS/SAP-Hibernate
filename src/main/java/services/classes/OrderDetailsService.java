package services.classes;

import models.OrderDetails;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.interfaces.IOrderDetailsService;
import utils.CreateList;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class OrderDetailsService implements IOrderDetailsService {
    private static List<OrderDetails> orderDetailsList;


    @Override
    public List<OrderDetails> findAll(Session session, CriteriaBuilder builder) {
        List<OrderDetails> orderDetailsList = CreateList.genericListCreator(builder, session, OrderDetails.class);
        if(OrderDetailsService.orderDetailsList == null)
            OrderDetailsService.orderDetailsList = orderDetailsList;
        return orderDetailsList;
    }

    @Override
    public OrderDetails getOne(String id) {
        if (orderDetailsList == null) {
            return null;
        } else {
            String[] ids = id.split(" ");
            for (OrderDetails account : orderDetailsList) {
                if (account.getProduct().getProductId() == Integer.parseInt(ids[0])
                        && account.getOrder().getOrderId() == Integer.parseInt(ids[1]))
                    return account;
            }
            //todo make exception
            return null;
        }
    }

    @Override
    public void deleteByID(String ID, Session session) {
        if (orderDetailsList == null) {
            return;
        }
        try {
            Transaction tx = session.beginTransaction();

            OrderDetails entity = getOne(ID);
            session.delete(entity);

            if (tx.isActive())
                session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public OrderDetails createOne(OrderDetails entity, Session session) {
        if (orderDetailsList == null) {
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
    public OrderDetails updateByID(OrderDetails update, Session session) {
        if (orderDetailsList == null) {
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
