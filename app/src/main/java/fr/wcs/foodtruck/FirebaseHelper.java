package fr.wcs.foodtruck;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by sam on 17/10/17.
 */

public class FirebaseHelper {

    private static FirebaseDatabase mDatabase;

    /**
     * PersistenceEnabled must be called only once, at the first Instance's call.
     * Method to handle unique Firebase Database instance and set Persistance on first call.
     * @return the Firebase Instance.
     */
    public static FirebaseDatabase getDatabase(){
        if(mDatabase == null) {

            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);

            // utilie en cas de coupure du reseau, ça permet d'avoir une sauvegarde de la BDD en cache

            // Si activé,il faut utiliser cet appel partout
        }
        return mDatabase;
    }
}

