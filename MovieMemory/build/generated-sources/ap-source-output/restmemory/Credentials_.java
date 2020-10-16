package restmemory;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import restmemory.Person;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-30T15:50:07")
@StaticMetamodel(Credentials.class)
public class Credentials_ { 

    public static volatile SingularAttribute<Credentials, Date> signupdate;
    public static volatile SingularAttribute<Credentials, String> password;
    public static volatile SingularAttribute<Credentials, Person> personid;
    public static volatile SingularAttribute<Credentials, String> username;

}