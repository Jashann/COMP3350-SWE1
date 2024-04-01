package com.example.simplicook.application;


import com.example.simplicook.persistence.PlanningPersistence;
import com.example.simplicook.persistence.RecipePersistence;
import com.example.simplicook.persistence.hsqldb.PlanningPersistenceHSQLDB;
import com.example.simplicook.persistence.hsqldb.RecipePersistenceHSQLDB;
import com.example.simplicook.persistence.stubs.RecipePersistenceStub;

public class Services
{
    private static RecipePersistence recipeDB;
    private static PlanningPersistence planningDB;

    public static synchronized RecipePersistence getRecipePersistence(boolean forProduction){
        if(recipeDB == null) {
            if (forProduction)
                recipeDB = new RecipePersistenceHSQLDB(Main.getDBPathName());
            else
                recipeDB = new RecipePersistenceStub();
        }
        return recipeDB;
    }

    public static synchronized PlanningPersistence getPlanningPersistence(boolean forProduction){
        if(planningDB == null) {
            if (forProduction)
                planningDB = new PlanningPersistenceHSQLDB(Main.getDBPathName());
            // TODO: We don't have Stub database
        }
        return planningDB;
    }

    public static synchronized void clean() {
        recipeDB = null;
        planningDB = null;
    }
}


