package services.classes;

import models.AccountType;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.interfaces.IAccountTypeService;
import utils.CreateList;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class AccountTypeService implements IAccountTypeService {
    private static List<AccountType> accountTypeList;

    @Override
    public List<AccountType> findAll(Session session, CriteriaBuilder builder) {
        List<AccountType> accountTypeList = CreateList.genericListCreator(builder, session, AccountType.class);
        if(AccountTypeService.accountTypeList == null)
            AccountTypeService.accountTypeList = accountTypeList;
        return accountTypeList;
    }

    @Override
    public AccountType getOne(String Id) {
        if (accountTypeList == null) {
            return null;
        } else {
            for (AccountType account : accountTypeList) {
                if (account.getAccountTypeID() == Integer.parseInt(Id))
                    return account;
            }
            //todo make exception
            return null;
        }
    }

    @Override
    public AccountType createOne(AccountType entity, Session session) {
        if (accountTypeList == null) {
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
        if (accountTypeList == null) {
            return;
        }
        try {
            Transaction tx = session.beginTransaction();

            AccountType entity = getOne(ID);
            session.delete(entity);

            if (tx.isActive())
                session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public AccountType updateByID(AccountType update, Session session) {
        if (accountTypeList == null) {
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
