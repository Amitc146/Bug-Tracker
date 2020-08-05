package com.amit.bugtracker.aspect;

import com.amit.bugtracker.entity.Comment;
import com.amit.bugtracker.entity.Project;
import com.amit.bugtracker.entity.Ticket;
import com.amit.bugtracker.entity.User;
import com.amit.bugtracker.exception.AccessDeniedException;
import com.amit.bugtracker.service.CommentService;
import com.amit.bugtracker.service.ProjectService;
import com.amit.bugtracker.service.TicketService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(2)
@Component
public class PermissionCheckAspect {

    private User currentUser;
    private final ProjectService projectService;
    private final TicketService ticketService;
    private final CommentService commentService;

    public PermissionCheckAspect(ProjectService projectService, TicketService ticketService, CommentService commentService) {
        this.projectService = projectService;
        this.ticketService = ticketService;
        this.commentService = commentService;
    }

    @AfterReturning(pointcut = "execution(* com.amit.bugtracker.controller.GlobalControllerAdvice.getCurrentUser(..))",
            returning = "user")
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @Pointcut("execution(* com.amit.bugtracker.controller.ProjectController.showProject(..))")
    public void forShowProject() {
    }

    @Pointcut("execution(* com.amit.bugtracker.controller.TicketController.createNewTicketWithProjectSelected(..))")
    public void forAddTicket() {
    }

    @Pointcut("execution(* com.amit.bugtracker.controller.TicketController.updateTicket(..))")
    public void forUpdateTicket() {
    }

    @Pointcut("execution(* com.amit.bugtracker.controller.TicketController.deleteTicket(..))")
    public void forDeleteTicket() {
    }

    @Pointcut("execution(* com.amit.bugtracker.controller.TicketController.showTicket(..))")
    public void forShowTicket() {
    }

    @Pointcut("execution(* com.amit.bugtracker.controller.TicketController.deleteComment(..))")
    public void forDeleteComment() {
    }

    @Before("forShowProject() || forAddTicket()")
    public void checkAccessToProject(JoinPoint joinPoint) {
        Integer projectId = getId(joinPoint);
        Project project = projectService.findById(projectId);
        checkAccessToProject(project);
    }

    @Before("forShowTicket()")
    public void checkIfAllowedToViewTicket(JoinPoint joinPoint) {
        Integer ticketId = getId(joinPoint);
        Ticket ticket = ticketService.findById(ticketId);
        checkAccessToProject(ticket.getProject());
    }

    private void checkAccessToProject(Project project) {
        if (!currentUser.isAllowedToView(project))
            throw new AccessDeniedException("User is not assigned to to project.");
    }

    @Before("forUpdateTicket() || forDeleteTicket()")
    public void checkAccessToTicketModification(JoinPoint joinPoint) {
        Integer ticketId = getId(joinPoint);
        Ticket ticket = ticketService.findById(ticketId);

        if (!currentUser.isAllowedToEditAndDelete(ticket))
            throw new AccessDeniedException("User is not allowed to modify ticket.");
    }

    @Before("forDeleteComment()")
    public void checkIfAllowedToDeleteComment(JoinPoint joinPoint) {
        Integer id = getId(joinPoint);
        Comment comment = commentService.findById(id);

        if (!currentUser.isAllowedToDeleteComment(comment))
            throw new AccessDeniedException("User is now allowed to delete comment.");
    }

    private Integer getId(JoinPoint joinPoint) {
        for (Object o : joinPoint.getArgs()) {
            if (o instanceof Integer)
                return (Integer) o;
        }
        return null;
    }

}
