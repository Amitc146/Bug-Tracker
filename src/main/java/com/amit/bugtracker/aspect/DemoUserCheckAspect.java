package com.amit.bugtracker.aspect;

import com.amit.bugtracker.entity.User;
import com.amit.bugtracker.exception.DemoUserException;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(1)
@Component
public class DemoUserCheckAspect {

    private User currentUser;

    @AfterReturning(pointcut = "execution(* com.amit.bugtracker.controller.GlobalControllerAdvice.getCurrentUser(..))",
            returning = "user")
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @Pointcut("execution(* com.amit.bugtracker.controller.ProjectController.saveProject(..))")
    public void forSaveProject() {
    }

    @Pointcut("execution(* com.amit.bugtracker.controller.ProjectController.deleteProject(..))")
    public void forDeleteProject() {
    }

    @Pointcut("execution(* com.amit.bugtracker.controller.TicketController.saveTicket(..))")
    public void forSaveTicket() {
    }

    @Pointcut("execution(* com.amit.bugtracker.controller.TicketController.deleteTicket(..))")
    public void forDeleteTicket() {
    }

    @Pointcut("execution(* com.amit.bugtracker.controller.TicketController.saveComment(..))")
    public void forSaveComment() {
    }

    @Pointcut("execution(* com.amit.bugtracker.controller.TicketController.deleteComment(..))")
    public void forDeleteComment() {
    }

    @Pointcut("execution(* com.amit.bugtracker.controller.UserController.deleteUser(..))")
    public void forDeleteUser() {
    }

    @Pointcut("execution(* com.amit.bugtracker.controller.UserController.saveUser(..))")
    public void forSaveUser() {
    }


    @Before("forSaveProject() || forDeleteProject() || forSaveTicket() || " +
            "forDeleteTicket() || forSaveComment() || forDeleteComment() || " +
            "forSaveUser() || forDeleteUser()")
    public void checkIfDemoUser() {
        if (isDemoUser(currentUser))
            throw new DemoUserException();
    }


    private boolean isDemoUser(User user) {
        return user.getUserName().startsWith("Demo");
    }


}
