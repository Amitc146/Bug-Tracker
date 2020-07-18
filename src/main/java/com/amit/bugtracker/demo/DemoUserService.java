package com.amit.bugtracker.demo;

import com.amit.bugtracker.entity.User;
import com.amit.bugtracker.exception.DemoUserException;

public class DemoUserService {

    // Used to block demo users from changing stuff
    public static void demoCheck(User user) {
        if (isDemoUser(user))
            throw new DemoUserException("Access denied - Demo user");
    }


    private static boolean isDemoUser(User user) {
        return user.getFirstName().equals("Demo");
    }

}
