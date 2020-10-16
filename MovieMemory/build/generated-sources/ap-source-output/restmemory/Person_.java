package restmemory;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import restmemory.Credentials;
import restmemory.Memoir;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-30T15:50:07")
@StaticMetamodel(Person.class)
public class Person_ { 

    public static volatile SingularAttribute<Person, String> firstname;
    public static volatile SingularAttribute<Person, String> gender;
    public static volatile CollectionAttribute<Person, Credentials> credentialsCollection;
    public static volatile SingularAttribute<Person, String> streetname;
    public static volatile SingularAttribute<Person, String> surname;
    public static volatile SingularAttribute<Person, Date> dob;
    public static volatile CollectionAttribute<Person, Memoir> memoirCollection;
    public static volatile SingularAttribute<Person, String> streetnumber;
    public static volatile SingularAttribute<Person, String> postcode;
    public static volatile SingularAttribute<Person, Integer> personid;
    public static volatile SingularAttribute<Person, String> state;

}