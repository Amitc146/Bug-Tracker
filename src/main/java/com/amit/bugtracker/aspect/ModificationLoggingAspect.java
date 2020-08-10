package com.amit.bugtracker.aspect;

import com.amit.bugtracker.entity.*;
import com.amit.bugtracker.service.LogService;
import com.amit.bugtracker.service.ProjectService;
import com.amit.bugtracker.service.TicketService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Order(3)
@Component
public class ModificationLoggingAspect {

    private User currentUser;
    private final LogService logService;
    private final TicketService ticketService;
    private final ProjectService projectService;

    public ModificationLoggingAspect(LogService logService, TicketService ticketService, ProjectService projectService) {
        this.logService = logService;
        this.ticketService = ticketService;
        this.projectService = projectService;
    }

    @AfterReturning(pointcut = "execution(* com.amit.bugtracker.controller.GlobalControllerAdvice.getCurrentUser(..))",
            returning = "user")
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @Pointcut("execution(* com.amit.bugtracker.controller.TicketController.saveTicket(..))")
    public void forSaveTicket() {
    }

    @Pointcut("execution(* com.amit.bugtracker.controller.ProjectController.saveProject(..))")
    public void forSaveProject() {
    }

    @Before("forSaveTicket()")
    public void logTicketModifications(JoinPoint joinPoint) {
        Ticket ticket = getTicket(joinPoint);
        if (ticket.getId() == null)
            return;

        HashMap<String, Boolean> modifications = getTicketModifications(ticket);
        for (Map.Entry<String, Boolean> entry : modifications.entrySet()) {
            if (entry.getValue()) {
                TicketLog log = new TicketLog(entry.getKey(), getCurrentDate(), ticket, currentUser);
                logService.saveTicketLog(log);
            }
        }
    }

    private Ticket getTicket(JoinPoint joinPoint) {
        for (Object o : joinPoint.getArgs()) {
            if (o instanceof Ticket)
                return (Ticket) o;
        }
        throw new RuntimeException("No ticket found");
    }

    private HashMap<String, Boolean> getTicketModifications(Ticket ticket) {
        Ticket oldTicket = ticketService.findById(ticket.getId());
        HashMap<String, Boolean> modifications = new HashMap<>();
        modifications.put("Title changed to " + ticket.getTitle(),
                !oldTicket.getTitle().equals(ticket.getTitle()));
        modifications.put("Description changed",
                !oldTicket.getDescription().equals(ticket.getDescription()));
        modifications.put("Status changed to " + ticket.getStatus().getDisplayValue(),
                !(oldTicket.getStatus() == ticket.getStatus()));
        modifications.put("Priority changed to " + ticket.getPriority().getDisplayValue(),
                !(oldTicket.getPriority() == ticket.getPriority()));
        modifications.put("Project Changed",
                !oldTicket.getProject().equals(ticket.getProject()));
        return modifications;
    }

    @Before("forSaveProject()")
    public void logProjectModifications(JoinPoint joinPoint) {
        Project project = getProject(joinPoint);
        if (project.getId() == null)
            return;

        HashMap<String, Boolean> modifications = getProjectModifications(project);
        for (Map.Entry<String, Boolean> entry : modifications.entrySet()) {
            if (entry.getValue()) {
                ProjectLog log = new ProjectLog(entry.getKey(), getCurrentDate(), project, currentUser);
                logService.saveProjectLog(log);
            }
        }
    }

    private HashMap<String, Boolean> getProjectModifications(Project project) {
        Project oldProject = projectService.findById(project.getId());
        HashMap<String, Boolean> modifications = new HashMap<>();
        modifications.put("Name changed to " + project.getName(),
                !oldProject.getName().equals(project.getName()));
        modifications.put("Description changed",
                !oldProject.getDescription().equals(project.getDescription()));
        return modifications;
    }

    private Project getProject(JoinPoint joinPoint) {
        for (Object o : joinPoint.getArgs()) {
            if (o instanceof Project)
                return (Project) o;
        }
        throw new RuntimeException("No project found");
    }


    private String getCurrentDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

}
