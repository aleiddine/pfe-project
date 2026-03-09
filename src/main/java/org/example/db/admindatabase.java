package org.example.db;
import org.example.model.Patient;
import org.example.model.admin;

import java.util.*;

public class admindatabase {
    private static List<admin> admins;

    private admindatabase(List<admin> admins) {
        admins= new ArrayList<>();
        // Add a mock patient for testing
        admins.add(new admin("A1", "Admin", "admin@example.com", "thisistheadmin"));
    }
}
