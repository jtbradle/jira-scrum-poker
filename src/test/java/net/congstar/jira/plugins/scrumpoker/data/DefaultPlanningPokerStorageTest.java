package net.congstar.jira.plugins.scrumpoker.data;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import net.congstar.jira.plugins.scrumpoker.model.ScrumPokerSession;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DefaultPlanningPokerStorageTest {

    private static final DateTime TWO_DAYS_AGO = DateTime.now().minusDays(2);
    private static final DateTime FOURTEEN_HOURS_AGO = DateTime.now().minusHours(14);

    private static final String SOME_ISSUE = "Issue-1";
    private static final String YOUNG_ISSUE = "Young Issue";
    private static final String OLD_ISSUE = "Old Issue";

    private DefaultPlanningPokerStorage storage;

    @Before
    public void before() {
        storage = new DefaultPlanningPokerStorage();
    }

    @After
    public void after() {
        DateTimeUtils.setCurrentMillisSystem();
    }

    @Test
    public void shouldProduceNewSessionIfNonExists() {
        assertThat(storage.sessionForIssue(SOME_ISSUE), is(not(nullValue())));
    }

    @Test
    public void shouldReturnExistingSessionIfExists() {
        ScrumPokerSession sessionForIssue = storage.sessionForIssue(SOME_ISSUE);
        assertThat(storage.sessionForIssue(SOME_ISSUE), is(equalTo(sessionForIssue)));
    }

    @Test
    public void shouldRemoveSessionsOlderThanOneDay() {
        createScrumPokerSessionWithCreationOn(OLD_ISSUE, TWO_DAYS_AGO.getMillis());
        assertThat(storage.sessionForIssue(OLD_ISSUE).getStartedOn(), is(equalTo(DateTime.now())));
    }

    @Test
    public void shouldKeepSessionsYoungerThanOneDay() {
        createScrumPokerSessionWithCreationOn(YOUNG_ISSUE, FOURTEEN_HOURS_AGO.getMillis());
        assertThat(storage.sessionForIssue(YOUNG_ISSUE).getStartedOn(), is(equalTo(FOURTEEN_HOURS_AGO)));
    }

    private void createScrumPokerSessionWithCreationOn(String issueKey, long creationDate) {
        DateTimeUtils.setCurrentMillisFixed(creationDate);
        storage.sessionForIssue(issueKey);
        DateTimeUtils.setCurrentMillisSystem();
    }

}