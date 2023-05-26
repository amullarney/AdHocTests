package components;


import components.test.testentities.Border;
import components.test.testentities.BorderSet;
import components.test.testentities.Boy;
import components.test.testentities.BoySet;
import components.test.testentities.City;
import components.test.testentities.CitySet;
import components.test.testentities.Licence;
import components.test.testentities.LicenceSet;
import components.test.testentities.Person;
import components.test.testentities.PersonSet;
import components.test.testentities.Route;
import components.test.testentities.RouteSet;
import components.test.testentities.State;
import components.test.testentities.StateSet;
import components.test.testentities.Territory;
import components.test.testentities.TerritorySet;
import components.test.testentities.impl.BorderImpl;
import components.test.testentities.impl.BorderSetImpl;
import components.test.testentities.impl.BoyImpl;
import components.test.testentities.impl.BoySetImpl;
import components.test.testentities.impl.CityImpl;
import components.test.testentities.impl.CitySetImpl;
import components.test.testentities.impl.LicenceImpl;
import components.test.testentities.impl.LicenceSetImpl;
import components.test.testentities.impl.PersonImpl;
import components.test.testentities.impl.PersonSetImpl;
import components.test.testentities.impl.RouteImpl;
import components.test.testentities.impl.RouteSetImpl;
import components.test.testentities.impl.StateImpl;
import components.test.testentities.impl.StateSetImpl;
import components.test.testentities.impl.TerritoryImpl;
import components.test.testentities.impl.TerritorySetImpl;

import io.ciera.runtime.summit.application.IApplication;
import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.classes.IModelInstance;
import io.ciera.runtime.summit.classes.IRelationshipSet;
import io.ciera.runtime.summit.classes.Relationship;
import io.ciera.runtime.summit.classes.RelationshipSet;
import io.ciera.runtime.summit.components.Component;
import io.ciera.runtime.summit.exceptions.BadArgumentException;
import io.ciera.runtime.summit.exceptions.EmptyInstanceException;
import io.ciera.runtime.summit.exceptions.ModelIntegrityException;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.StringUtil;
import io.ciera.runtime.summit.util.LOG;
import io.ciera.runtime.summit.util.impl.LOGImpl;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;


public class Test extends Component<Test> {

    private Map<String, Class<?>> classDirectory;

    // Note: valid only if a deployment does not contain multiple instances of this component.
    private static Test singleton;
    public static Test Singleton() {
        return singleton;
    }
    
public Test(IApplication app, IRunContext runContext, int populationId) {
        super(app, runContext, populationId);
        singleton = this;
        Border_extent = new BorderSetImpl();
        Boy_extent = new BoySetImpl();
        City_extent = new CitySetImpl();
        Licence_extent = new LicenceSetImpl();
        Person_extent = new PersonSetImpl();
        Route_extent = new RouteSetImpl();
        State_extent = new StateSetImpl();
        Territory_extent = new TerritorySetImpl();
        R10_Boy_plays_with_Boy_extent = new RelationshipSet();
        R1_Territory_is_adjacent_to_Territory_extent = new RelationshipSet();
        R7_Licence_is_wed_to_Person_extent = new RelationshipSet();
        R8_Route_is_reachable_from_City_extent = new RelationshipSet();
        R9_Border_is_adjacent_to_State_extent = new RelationshipSet();
        LOG = null;
        classDirectory = new TreeMap<>();
        classDirectory.put("border", BorderImpl.class);
        classDirectory.put("boy", BoyImpl.class);
        classDirectory.put("city", CityImpl.class);
        classDirectory.put("Licence", LicenceImpl.class);
        classDirectory.put("person", PersonImpl.class);
        classDirectory.put("route", RouteImpl.class);
        classDirectory.put("state", StateImpl.class);
        classDirectory.put("Territory", TerritoryImpl.class);
    }

    // domain functions
    public void testSym() throws XtumlException {
        Person man = PersonImpl.create( context() );
        man.setName("Jack");
        Person wife = PersonImpl.create( context() );
        wife.setName("Jill");
        Licence lic = LicenceImpl.create( context() );
        context().relate_R7_Licence_is_wed_to_Person( lic, man );
        context().relate_R7_Licence_is_wed_to_Person( lic, wife );
        Person woman = man.R7_is_wed_to_Person();
        if ( !woman.isEmpty() ) {
            context().LOG().LogInfo( "woman is: " + woman.getName() );
        }
        else {
            context().LOG().LogFailure( "hmm.." );
        }
        Licence l = woman.R7_is_wed_to_Licence();
        Person body = l.R7_is_wed_to_Person().any();
        if ( !body.isEmpty() ) {
            context().LOG().LogInfo( "from licence we got: " + body.getName() );
        }
        Person spouse = lic.R7_is_wed_to_Person().any();
        Person other = lic.R7_is_wed_to_Person().anyWhere(selected -> StringUtil.inequality(((Person)selected).getName(), spouse.getName()));
        context().LOG().LogInfo( "spouse is: " + spouse.getName() );
        context().LOG().LogInfo( "other is: " + other.getName() );
        PersonSet people = lic.R7_is_wed_to_Person();
        context().LOG().LogInfo( "select many.." );
        Person person;
        for ( Iterator<Person> _person_iter = people.elements().iterator(); _person_iter.hasNext(); ) {
            person = _person_iter.next();
            context().LOG().LogInfo( "person is: " + person.getName() );
        }
        Boy b1 = BoyImpl.create( context() );
        Boy b2 = BoyImpl.create( context() );
        Boy b3 = BoyImpl.create( context() );
        Boy b4 = BoyImpl.create( context() );
        context().relate_R10_Boy_plays_with_Boy( b1, b2 );
        context().relate_R10_Boy_plays_with_Boy( b4, b3 );
        Boy playmate = b1.R10_plays_with_Boy();
        context().unrelate_R10_Boy_plays_with_Boy( b2, b1 );
        context().unrelate_R10_Boy_plays_with_Boy( b3, b4 );
        context().unrelate_R7_Licence_is_wed_to_Person( lic, man );
        context().unrelate_R7_Licence_is_wed_to_Person( lic, wife );
        Person ex = man.R7_is_wed_to_Person();
        if ( !ex.isEmpty() ) {
            context().LOG().LogFailure( "hmm.." );
        }
        Territory USA = TerritoryImpl.create( context() );
        USA.setName("USA");
        Territory Mexico = TerritoryImpl.create( context() );
        Mexico.setName("Mexico");
        context().relate_R1_Territory_is_adjacent_to_Territory( USA, Mexico );
        Territory t = Mexico.R1_is_adjacent_to_Territory().any();
        context().LOG().LogInfo( t.getName() );
        context().unrelate_R1_Territory_is_adjacent_to_Territory( USA, Mexico );
        City x = CityImpl.create( context() );
        x.setName("Dublin");
        City y = CityImpl.create( context() );
        y.setName("Cork");
        Route r1 = RouteImpl.create( context() );
        r1.setToll(true);
        context().relate_R8_Route_is_reachable_from_City( r1, x );
        context().relate_R8_Route_is_reachable_from_City( r1, y );
        Route r2 = RouteImpl.create( context() );
        context().relate_R8_Route_is_reachable_from_City( r2, x );
        context().relate_R8_Route_is_reachable_from_City( r2, y );
        City z = x.R8_is_reachable_from_City().any();
        context().LOG().LogInfo( "reached: " + z.getName() );
        RouteSet routes = x.R8_is_reachable_from_Route();
        context().LOG().LogInteger( routes.size() );
        CitySet cities = r1.R8_is_reachable_from_City();
        context().LOG().LogInfo( "r1 joins cities" );
        context().LOG().LogInteger( cities.size() );
        State az = StateImpl.create( context() );
        az.setName("AZ");
        State nm = StateImpl.create( context() );
        nm.setName("NM");
        State ca = StateImpl.create( context() );
        ca.setName("CA");
        Border aznm = BorderImpl.create( context() );
        Border azca = BorderImpl.create( context() );
        context().relate_R9_Border_is_adjacent_to_State( azca, az );
        context().relate_R9_Border_is_adjacent_to_State( azca, ca );
        context().relate_R9_Border_is_adjacent_to_State( aznm, az );
        context().relate_R9_Border_is_adjacent_to_State( aznm, nm );
        StateSet states = az.R9_is_adjacent_to_State();
        context().LOG().LogInfo( "AZ borders" );
        context().LOG().LogInteger( states.size() );
    }



    // relates and unrelates
    public void relate_R10_Boy_plays_with_Boy( Boy form, Boy part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot relate empty instances." );
        // TODO cardinality check
        if ( true) {
            part.setR10_plays_with_Boy(form);
            form.setR10_plays_with_Boy(part);
        }
        else throw new ModelIntegrityException( "Instances could not be related." );
    }

    public void unrelate_R10_Boy_plays_with_Boy( Boy form, Boy part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot unrelate empty instances." );
        if ( true ) {
            part.setR10_plays_with_Boy(BoyImpl.EMPTY_BOY);
            form.setR10_plays_with_Boy(BoyImpl.EMPTY_BOY);
        }
        else throw new ModelIntegrityException( "Instances could not be unrelated." );
    }
    public void relate_R1_Territory_is_adjacent_to_Territory( Territory form, Territory part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot relate empty instances." );
        // TODO cardinality check
        if ( R1_Territory_is_adjacent_to_Territory_extent.add( new Relationship( form.getInstanceId(), part.getInstanceId() ) ) ) {
            part.addR1_is_adjacent_to_Territory(form);
            form.addR1_is_adjacent_to_Territory(part);
        }
        else throw new ModelIntegrityException( "Instances could not be related." );
    }

    public void unrelate_R1_Territory_is_adjacent_to_Territory( Territory form, Territory part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot unrelate empty instances." );
        if (( R1_Territory_is_adjacent_to_Territory_extent.remove( R1_Territory_is_adjacent_to_Territory_extent.get( form.getInstanceId(), part.getInstanceId() ) ) )
           || ( R1_Territory_is_adjacent_to_Territory_extent.remove( R1_Territory_is_adjacent_to_Territory_extent.get( part.getInstanceId(), form.getInstanceId() ) ) )) {
            part.removeR1_is_adjacent_to_Territory(form);
            form.removeR1_is_adjacent_to_Territory(part);
        }
        else throw new ModelIntegrityException( "Instances could not be unrelated." );
    }
    public void relate_R7_Licence_is_wed_to_Person( Licence form, Person part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot relate empty instances." );
        // TODO cardinality check
        if ( R7_Licence_is_wed_to_Person_extent.add( new Relationship( form.getInstanceId(), part.getInstanceId() ) ) ) {
            part.setR7_is_wed_to_Licence(form);
            form.addR7_is_wed_to_Person(part);
        }
        else throw new ModelIntegrityException( "Instances could not be related." );
    }

    public void unrelate_R7_Licence_is_wed_to_Person( Licence form, Person part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot unrelate empty instances." );
        if ( R7_Licence_is_wed_to_Person_extent.remove( R7_Licence_is_wed_to_Person_extent.get( form.getInstanceId(), part.getInstanceId() ) ) ) {
            part.setR7_is_wed_to_Licence(LicenceImpl.EMPTY_LICENCE);
            form.removeR7_is_wed_to_Person(part);
        }
        else throw new ModelIntegrityException( "Instances could not be unrelated." );
    }
    public void relate_R8_Route_is_reachable_from_City( Route form, City part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot relate empty instances." );
        // TODO cardinality check
        if ( R8_Route_is_reachable_from_City_extent.add( new Relationship( form.getInstanceId(), part.getInstanceId() ) ) ) {
            part.addR8_is_reachable_from_Route(form);
            form.addR8_is_reachable_from_City(part);
        }
        else throw new ModelIntegrityException( "Instances could not be related." );
    }

    public void unrelate_R8_Route_is_reachable_from_City( Route form, City part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot unrelate empty instances." );
        if ( R8_Route_is_reachable_from_City_extent.remove( R8_Route_is_reachable_from_City_extent.get( form.getInstanceId(), part.getInstanceId() ) ) ) {
            part.removeR8_is_reachable_from_Route(form);
            form.removeR8_is_reachable_from_City(part);
        }
        else throw new ModelIntegrityException( "Instances could not be unrelated." );
    }
    public void relate_R9_Border_is_adjacent_to_State( Border form, State part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot relate empty instances." );
        // TODO cardinality check
        if ( R9_Border_is_adjacent_to_State_extent.add( new Relationship( form.getInstanceId(), part.getInstanceId() ) ) ) {
            part.addR9_is_adjacent_to_Border(form);
            form.addR9_is_adjacent_to_State(part);
        }
        else throw new ModelIntegrityException( "Instances could not be related." );
    }

    public void unrelate_R9_Border_is_adjacent_to_State( Border form, State part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot unrelate empty instances." );
        if ( R9_Border_is_adjacent_to_State_extent.remove( R9_Border_is_adjacent_to_State_extent.get( form.getInstanceId(), part.getInstanceId() ) ) ) {
            part.removeR9_is_adjacent_to_Border(form);
            form.removeR9_is_adjacent_to_State(part);
        }
        else throw new ModelIntegrityException( "Instances could not be unrelated." );
    }


    // instance selections
    private BorderSet Border_extent;
    public BorderSet Border_instances() {
        return Border_extent;
    }
    private BoySet Boy_extent;
    public BoySet Boy_instances() {
        return Boy_extent;
    }
    private CitySet City_extent;
    public CitySet City_instances() {
        return City_extent;
    }
    private LicenceSet Licence_extent;
    public LicenceSet Licence_instances() {
        return Licence_extent;
    }
    private PersonSet Person_extent;
    public PersonSet Person_instances() {
        return Person_extent;
    }
    private RouteSet Route_extent;
    public RouteSet Route_instances() {
        return Route_extent;
    }
    private StateSet State_extent;
    public StateSet State_instances() {
        return State_extent;
    }
    private TerritorySet Territory_extent;
    public TerritorySet Territory_instances() {
        return Territory_extent;
    }


    // relationship selections
    private IRelationshipSet R10_Boy_plays_with_Boy_extent;
    public IRelationshipSet R10_Boy_plays_with_Boys() throws XtumlException {
        return R10_Boy_plays_with_Boy_extent;
    }
    private IRelationshipSet R1_Territory_is_adjacent_to_Territory_extent;
    public IRelationshipSet R1_Territory_is_adjacent_to_Territorys() throws XtumlException {
        return R1_Territory_is_adjacent_to_Territory_extent;
    }
    private IRelationshipSet R7_Licence_is_wed_to_Person_extent;
    public IRelationshipSet R7_Licence_is_wed_to_Persons() throws XtumlException {
        return R7_Licence_is_wed_to_Person_extent;
    }
    private IRelationshipSet R8_Route_is_reachable_from_City_extent;
    public IRelationshipSet R8_Route_is_reachable_from_Citys() throws XtumlException {
        return R8_Route_is_reachable_from_City_extent;
    }
    private IRelationshipSet R9_Border_is_adjacent_to_State_extent;
    public IRelationshipSet R9_Border_is_adjacent_to_States() throws XtumlException {
        return R9_Border_is_adjacent_to_State_extent;
    }


    // ports


    // utilities
    private LOG LOG;
    public LOG LOG() {
        if ( null == LOG ) LOG = new LOGImpl<>( this );
        return LOG;
    }


    // component initialization function
    @Override
    public void initialize() throws XtumlException {
        testSym();
    }

    @Override
    public String getVersion() {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("TestProperties.properties"));
        } catch (IOException e) { /* do nothing */ }
        return prop.getProperty("version", "Unknown");
    }
    @Override
    public String getVersionDate() {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("TestProperties.properties"));
        } catch (IOException e) { /* do nothing */ }
        return prop.getProperty("version_date", "Unknown");
    }

    @Override
    public boolean addInstance( IModelInstance<?,?> instance ) throws XtumlException {
        if ( null == instance ) throw new BadArgumentException( "Null instance passed." );
        if ( instance.isEmpty() ) throw new EmptyInstanceException( "Cannot add empty instance to population." );
        if ( instance instanceof Border ) return Border_extent.add( (Border)instance );
        else if ( instance instanceof Boy ) return Boy_extent.add( (Boy)instance );
        else if ( instance instanceof City ) return City_extent.add( (City)instance );
        else if ( instance instanceof Licence ) return Licence_extent.add( (Licence)instance );
        else if ( instance instanceof Person ) return Person_extent.add( (Person)instance );
        else if ( instance instanceof Route ) return Route_extent.add( (Route)instance );
        else if ( instance instanceof State ) return State_extent.add( (State)instance );
        else if ( instance instanceof Territory ) return Territory_extent.add( (Territory)instance );
        return false;
    }

    @Override
    public boolean removeInstance( IModelInstance<?,?> instance ) throws XtumlException {
        if ( null == instance ) throw new BadArgumentException( "Null instance passed." );
        if ( instance.isEmpty() ) throw new EmptyInstanceException( "Cannot remove empty instance from population." );
        if ( instance instanceof Border ) return Border_extent.remove( (Border)instance );
        else if ( instance instanceof Boy ) return Boy_extent.remove( (Boy)instance );
        else if ( instance instanceof City ) return City_extent.remove( (City)instance );
        else if ( instance instanceof Licence ) return Licence_extent.remove( (Licence)instance );
        else if ( instance instanceof Person ) return Person_extent.remove( (Person)instance );
        else if ( instance instanceof Route ) return Route_extent.remove( (Route)instance );
        else if ( instance instanceof State ) return State_extent.remove( (State)instance );
        else if ( instance instanceof Territory ) return Territory_extent.remove( (Territory)instance );
        return false;
    }

    @Override
    public Test context() {
        return this;
    }

    @Override
    public Class<?> getClassByKeyLetters(String keyLetters) {
        return classDirectory.get(keyLetters);
    }

}