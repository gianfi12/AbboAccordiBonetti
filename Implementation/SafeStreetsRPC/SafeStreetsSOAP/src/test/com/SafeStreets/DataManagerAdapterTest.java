package com.SafeStreets;

import com.SafeStreets.model.Vehicle;

import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DataManagerAdapterTest {

    //@PersistenceContext
    //EntityManager em;

    //@Inject
    //UserTransaction utx;



    /*
    @Before
    public void preparePersistenceTest() throws Exception {
        clearData();
        insertData();
        startTransaction();
    }

    private void clearData() throws Exception {
        //utx.begin();
        em.joinTransaction();
        System.out.println("Dumping old records...");
        em.createQuery("delete from Game").executeUpdate();
        //utx.commit();
    }

    private void insertData() throws Exception {
        utx.begin();
        em.joinTransaction();
        System.out.println("Inserting records...");
        for (String title : GAME_TITLES) {
            Game game = new Game(title);
            em.persist(game);
        }
        utx.commit();
        // clear the persistence context (first-level cache)
        em.clear();
    }

    private void startTransaction() throws Exception {
        //utx.begin();
        //em.joinTransaction();
    }

    @After
    public void commitTransaction() throws Exception {
        //utx.commit();
    }*/

    //@PersistenceContext
    //EntityManager em;

    @Test
    public void getVehicleTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();
        Vehicle v=em.find(Vehicle.class, "AP234IJ");

    }


    //@PersistenceContext
    //private EntityManager em;
/*
    @Test
    public void getUserTest() throws WrongPasswordException {
        DataManagerAdapter dataManagerAdapter=new DataManagerAdapter();
        User user=dataManagerAdapter.getUser("jak4", "jak");

        System.out.println("end");
    }

    @Test
    public void getVehicleTest() {
        Vehicle v=em.find(Vehicle.class, "AP234IJ");

        System.out.println(v);
    }*/

}