import org.hibernate.Session;
import utils.HibernateUtil;

import javax.persistence.criteria.CriteriaBuilder;

public class TempClass {


     public static void start (){
          Session session = HibernateUtil.getSession();
          CriteriaBuilder builder = session.getCriteriaBuilder();

     }


}
