package services;

import models.Account;
import models.AccountType;
import org.hibernate.Session;
import services.classes.AccountSerice;
import services.classes.AccountTypeService;
import utils.MD5;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Scanner;
import java.util.regex.Pattern;

public class EntryService {
    private static Scanner scanner = new Scanner(System.in);

    private EntryService() {
    }

    public static boolean login(String username, String password) {
        AccountSerice serice = new AccountSerice();
        Account account = serice.getOne(username);

        return account != null && account.getPassword().equals(MD5.getHashString(password));
    }

    public static void signUp(Session session, CriteriaBuilder builder) {
        Account account = enterAccount(session, builder);
        //exception handling
        AccountSerice serice = new AccountSerice();
        serice.findAll(session,builder);
        serice.createOne(account,session);
    }

    private static Account enterAccount(Session session, CriteriaBuilder builder) {
        String name;
        String email;
        String password;
        String passwordCheck;
        AccountType accountType;
        boolean isValid = false;

        do {
            name = enterName();
            email = enterEmail();
            password = enterPassword();
            passwordCheck = enterPassword();
            accountType = enterAccountType(session, builder);

            if (password.equals(passwordCheck))
                isValid = true;
        } while (!isValid);
        return new Account(accountType, name, email, MD5.getHashString(password));
    }

    private static String enterName() {
        while (true) {
            System.out.println("Enter name or type EXIT to cancel:");
            String name = scanner.nextLine();

            if (name.matches("[A-Z][a-z]{2,20} [A-Z][a-z]{2,20}"))
                return name;
            else {
                if (name.toUpperCase().equals("EXIT"))
                    return "CANCELED";
            }
            //todo exception
        }

    }

    private static String enterEmail() {
        while (true) {
            System.out.println("Enter email or type EXIT to cancel:");
            String email = scanner.nextLine();

            if (email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}"))
                return email;
            else {
                if (email.toUpperCase().equals("EXIT"))
                    return "CANCELED";
            }
        }

    }

    private static String enterPassword() {
    /*Password matching expression. Password must be at least 4 characters, no more than 21 characters,
    and must include at least one upper case letter, one lower case letter, and one numeric digit.*/
        while (true) {
            System.out.println("Enter password or type EXIT to cancel:");
            String email = scanner.nextLine();

            if (email.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,20}$"))
                return email;
            else {
                if (email.toUpperCase().equals("EXIT"))
                    return "CANCELED";
            }
        }

    }

    private static AccountType enterAccountType(Session session, CriteriaBuilder builder){
        int index = 0;

        // todo fix new AccountType
        for(AccountType accountType : new AccountTypeService().findAll(session, builder)){
            System.out.println(index + " : "+ accountType.getName());
            index++;
        }
        System.out.println("Enter the index number or type EXIT to cancel: ");
        String choice = scanner.nextLine();
        if (choice.matches("\\d+"))
            return new AccountTypeService().getOne(choice);
        else {
            if (choice.toUpperCase().equals("EXIT"))
                return null;
        }
        return null;
    }
}
