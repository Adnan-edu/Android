package restmemory;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import restmemory.Cinema;
import restmemory.Person;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-30T15:50:07")
@StaticMetamodel(Memoir.class)
public class Memoir_ { 

    public static volatile SingularAttribute<Memoir, Cinema> cinemaid;
    public static volatile SingularAttribute<Memoir, Date> moviereldate;
    public static volatile SingularAttribute<Memoir, Float> ratingscore;
    public static volatile SingularAttribute<Memoir, String> comment;
    public static volatile SingularAttribute<Memoir, Person> personid;
    public static volatile SingularAttribute<Memoir, Date> watchingdatetime;
    public static volatile SingularAttribute<Memoir, Integer> memoirid;
    public static volatile SingularAttribute<Memoir, String> moviename;

}