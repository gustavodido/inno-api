package inno.edu.api.domain.user.queries;

import inno.edu.api.domain.user.models.MentorProfile;
import inno.edu.api.domain.user.repositories.MentorProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static inno.edu.api.factories.UniversityFactory.stanford;
import static inno.edu.api.factories.UserFactory.mentorProfiles;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetMentorProfilesByUniversityIdQueryTest {
    @Mock
    private MentorProfileRepository mentorProfileRepository;

    @InjectMocks
    private GetMentorProfilesByUniversityIdQuery getMentorProfilesByUniversityIdQuery;

    @Test
    public void shouldGetUniversityMentorProfiles() {
        when(mentorProfileRepository.findByUniversityIdAndIsActiveIsTrue(stanford().getId())).thenReturn(mentorProfiles());

        List<MentorProfile> mentorProfiles = getMentorProfilesByUniversityIdQuery.run(stanford().getId());

        assertThat(mentorProfiles, is(mentorProfiles()));
    }
}