package inno.edu.api.domain.university.commands;

import inno.edu.api.domain.university.exceptions.UniversityNotFoundException;
import inno.edu.api.domain.university.models.University;
import inno.edu.api.domain.university.repositories.UniversityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.common.ModelFactories.university;
import static inno.edu.api.common.ModelFactories.updatedUniversity;
import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateUniversityCommandTest {
    @Mock
    private UniversityRepository universityRepository;

    @InjectMocks
    private UpdateUniversityCommand updateUniversityCommand;

    @Test
    public void shouldReturnUpdatedUniversity() {
        when(universityRepository.findOne(university().getId())).thenReturn(university());
        when(universityRepository.save(university())).thenReturn(updatedUniversity());

        University university = updateUniversityCommand.run(university().getId(), university());

        assertThat(university, is(updatedUniversity()));
    }

    @Test(expected = UniversityNotFoundException.class)
    public void shouldRaiseExceptionIfUniversityDoesNotExists() {
        when(universityRepository.findOne(university().getId())).thenThrow(new UniversityNotFoundException(university().getId()));

        updateUniversityCommand.run(randomUUID(), university());
    }
}