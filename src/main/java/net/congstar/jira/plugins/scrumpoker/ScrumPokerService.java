package net.congstar.jira.plugins.scrumpoker;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import net.congstar.jira.plugins.scrumpoker.data.PlanningPokerStorage;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class ScrumPokerService {

    private PlanningPokerStorage planningPokerStorage;

    public ScrumPokerService(PlanningPokerStorage planningPokerStorage) {
        this.planningPokerStorage = planningPokerStorage;
    }

    @GET
    @Path("session/{issueKey}")
    public Response getSession(@PathParam("issueKey") String issueKey) {
        // FIXME: Wir liefern den Usernamen und nicht den Displaynamen hoch... was wollen wir hochreichen?
        return Response.ok(planningPokerStorage.sessionForIssue(issueKey)).build();
    }

}