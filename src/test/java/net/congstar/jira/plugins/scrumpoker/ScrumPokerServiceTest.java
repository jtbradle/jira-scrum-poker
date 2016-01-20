package net.congstar.jira.plugins.scrumpoker;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import javax.ws.rs.core.Response;

import net.congstar.jira.plugins.scrumpoker.data.PlanningPokerStorage;
import net.congstar.jira.plugins.scrumpoker.model.ScrumPokerSession;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class ScrumPokerServiceTest {

    private static final String ISSUE_KEY = "SP-18998";

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private PlanningPokerStorage planningPokerStorage;

    @InjectMocks
    private ScrumPokerService scrumPokerService;

    private ScrumPokerSession expectedScrumPokerSession;

    @Before
    public void before() {
        expectedScrumPokerSession = new ScrumPokerSession();
        when(planningPokerStorage.sessionForIssue(ISSUE_KEY)).thenReturn(expectedScrumPokerSession);
    }

    @Test
    public void getSessionShouldReturnTheSessionForTheGivenIssueKey() {
        Response response = scrumPokerService.getSession(ISSUE_KEY);
        assertThat(response.getStatus(), is(equalTo(Response.Status.OK.getStatusCode())));
        assertThat((ScrumPokerSession) response.getEntity(), is(equalTo(expectedScrumPokerSession)));
    }

}
