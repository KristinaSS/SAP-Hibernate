package services.classes;

import models.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.interfaces.IProductService;
import utils.CreateList;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class ProductService implements IProductService {
    private static List<Product> productList;

    @Override
    public List<Product> findAll(Session session, CriteriaBuilder builder) {
        List<Product> productList = CreateList.genericListCreator(builder, session, Product.class);
        if(ProductService.productList == null)
            ProductService.productList = productList;
        return productList;
    }

    @Override
    public Product getOne(String Id) {
        if (productList == null) {
            return null;
        } else {
            for (Product account : productList) {
                if (account.getProductId() == Integer.parseInt(Id))
                    return account;
            }
            //todo make exception
            return null;
        }
    }

    @Override
    public Product createOne(Product entity, Session session) {
        if (productList == null) {
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
        if (productList == null) {
            return;
        }
        try {
            Transaction tx = session.beginTransaction();

            Product entity = getOne(ID);
            session.delete(entity);

            if (tx.isActive())
                session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public Product updateByID(Product update, Session session) {
        if (productList == null) {
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
