package inno.edu.api.domain.school.root.commands;

import inno.edu.api.domain.school.root.models.dtos.mappers.UpdateSchoolRequestMapper;
import inno.edu.api.domain.school.root.models.School;
import inno.edu.api.domain.school.root.queries.GetSchoolByIdQuery;
import inno.edu.api.domain.school.root.repositories.SchoolRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.SchoolFactory.stanford;
import static inno.edu.api.support.SchoolFactory.updateStanfordRequest;
import static inno.edu.api.support.SchoolFactory.updatedStanford;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateSchoolCommandTest {
    @Mock
    private UpdateSchoolRequestMapper updateSchoolRequestMapper;

    @Mock
    private SchoolRepository schoolRepository;

    @Mock
    private GetSchoolByIdQuery getSchoolByIdQuery;

    @InjectMocks
    private UpdateSchoolCommand updateSchoolCommand;

    @Test
    public void shouldReturnUpdatedSchool() {
        when(getSchoolByIdQuery.run(stanford().getId())).thenReturn(stanford());
        when(schoolRepository.save(stanford())).thenReturn(updatedStanford());

        School school = updateSchoolCommand.run(stanford().getId(), updateStanfordRequest());

        verify(updateSchoolRequestMapper).setSchool(updateStanfordRequest(), stanford());

        assertThat(school, is(updatedStanford()));
    }
}