package services.classes;

import models.Catagory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.interfaces.ICatagoryService;
import utils.CreateList;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class CatagoryService implements ICatagoryService {
    private static List<Catagory> catagoryList;

    @Override
    public List<Catagory> findAll(Session session, CriteriaBuilder builder) {
        List<Catagory> catagoryList = CreateList.genericListCreator(builder, session, Catagory.class);
        if(CatagoryService.catagoryList == null)
            CatagoryService.catagoryList = catagoryList;
        return catagoryList;
    }

    @Override
    public Catagory getOne(String Id) {
        if (catagoryList == null) {
            return null;
        } else {
            for (Catagory account : catagoryList) {
                if (account.getCategoryId() == Integer.parseInt(Id))
                    return account;
            }
            //todo make exception
            return null;
        }
    }

    @Override
    public Catagory createOne(Catagory entity, Session session) {
        if (catagoryList == null) {
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
        if (catagoryList == null) {
            return;
        }
        try {
            Transaction tx = session.beginTransaction();

            Catagory entity = getOne(ID);
            session.delete(entity);

            if (tx.isActive())
                session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public Catagory updateByID(Catagory update, Session session) {
        if (catagoryList == null) {
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
