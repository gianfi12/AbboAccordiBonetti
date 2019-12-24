package com.SafeStreets;

import com.SafeStreets.modelEntities.VehicleEntity;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class DataManagerAdapterTest {

    @Test
    public void getVehicleTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("manager1");
        EntityManager em = emf.createEntityManager();
        VehicleEntity v=em.find(VehicleEntity.class, "AP234IJ");

        System.out.println(v.getLicensePlate());

    }

}