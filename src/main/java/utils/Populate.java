package utils;


import com.google.common.base.Strings;
import dtos.BoatDTO;
import entities.Boat;
import entities.Harbour;
import entities.Owner;
import facades.BoatFacade;
import facades.HarbourFacade;
import facades.OwnerFacade;
import facades.UserFacade;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Populate {
    private final EntityManagerFactory emf;


    public static void main(String[] args) {
        new Populate(EMF_Creator.createEntityManagerFactory()).populateAll();
    }

    public Populate(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List<String> populateAll() {
        List<String> populated = new ArrayList<>();
        if(populateUsers())
            populated.add("users");
        if(populateHarbours())
            populated.add("harbours");
        if(populateOwners())
            populated.add("owners");
        if(populateBoats())
            populated.add("boats");

        return populated;
    }

    /**
     *
     * @return Boolean regarding table being populated or not.
     *
     * */
    public boolean populateUsers() throws IllegalArgumentException {
        UserFacade userFacade = UserFacade.getInstance(this.emf);

        if (!userFacade.getAllPrivate().isEmpty()) return false;

        // NOTICE: Always set your password as environment variables.
        String password_admin = "test";
        String password_user = "test";

        boolean isDeployed = System.getenv("DEPLOYED") != null;
        if(isDeployed) {
            password_user = System.getenv("PASSWORD_DEFAULT_USER");
            password_admin = System.getenv("PASSWORD_DEFAULT_ADMIN");

            // Do not allow "empty" passwords in production.
            if(Strings.isNullOrEmpty(password_admin) || password_admin.trim().length() < 3 || Strings.isNullOrEmpty(password_user) || password_user.trim().length() < 3)
                throw new IllegalArgumentException("FAILED POPULATE OF USERS: Passwords were empty or less than 3 characters? Are environment variables: [PASSWORD_DEFAULT_USER, PASSWORD_DEFAULT_ADMIN] set?");
        }

        userFacade._create("user", password_user, new ArrayList<>());
        userFacade._create("admin", password_admin, Collections.singletonList("admin"));
        return true;
    }
    
   public boolean populateHarbours() throws IllegalArgumentException {
        HarbourFacade harbourFacade = HarbourFacade.getInstance(this.emf);
        
        if (!harbourFacade.getAllHarbours().isEmpty()) return false;
        
        harbourFacade._create("Rodby", "Rødby Færge", 25);
        harbourFacade._create("Kobenhavn", "Island Brygge", 200);
        harbourFacade._create("Skagen", "Havnevagtvej", 10);
        return true;
    }
    
    public boolean populateOwners() {
        OwnerFacade ownerFacade = OwnerFacade.getInstance(this.emf);
        
        if (!ownerFacade.getAllOwners().isEmpty()) return false;
        
        ownerFacade._create("user", "Kalvebod Fælled", "60600600");
        ownerFacade._create("Morten", "Spasservej", "88888888");
        ownerFacade._create("Peter", "Palandvej", "87654321");
        return true;
    }
    
    public boolean populateBoats() {
        BoatFacade boatFacade = BoatFacade.getInstance(emf);
        OwnerFacade ownerFacade = OwnerFacade.getInstance(emf);
        HarbourFacade harbourFacade = HarbourFacade.getInstance(emf);
        
        if (!boatFacade.getAllBoats().isEmpty()) return false;
        
        int _owner_id = ownerFacade.getOwnerId("Morten");
        int _harbour_id = harbourFacade.getHarbourId("Rodby");
        Boat boat = new Boat("Lady Marmelade", "Ford", "WWI-spec Liberty V12");
        BoatDTO boatDTO = new BoatDTO(boat);
        
        boatFacade._create(boatDTO);
        boatFacade.connectHarbour(boatDTO, _harbour_id);
        boatFacade.setOwner(boatDTO, _owner_id);
        return true;
    }
}
