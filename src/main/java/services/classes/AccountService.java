package services.classes;

import models.Account;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.interfaces.IAccountService;
import utils.CreateList;


import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class AccountService implements IAccountService {
    private static List<Account> accountList;


    @Override
    public List<Account> findAll(Session session, CriteriaBuilder builder) {
        List<Account> accountList = CreateList.genericListCreator(builder, session, Account.class);
        if(AccountService.accountList == null)
            AccountService.accountList = accountList;
        return accountList;
    }

    @Override
    public Account getOne(String username) {
        if (accountList == null) {
            return null;
        } else {
            for (Account account : accountList) {
                if (account.getEmail().equals(username))
                    return account;
            }
            //todo make exception
            return null;
        }
    }

    @Override
    public void deleteByID(String ID, Session session) {
        if (accountList == null) {
            return;
        }
        try {
            Transaction tx = session.beginTransaction();

            Account entity = getOne(ID);
            session.delete(entity);

            if (tx.isActive())
                session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public Account createOne(Account entity, Session session) {
        if (accountList == null) {
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
    public Account updateByID(Account update, Session session) {
        if (accountList == null) {
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
