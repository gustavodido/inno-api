package inno.edu.api.domain.appointment.commands;

import inno.edu.api.domain.appointment.assertions.AppointmentExistsAssertion;
import inno.edu.api.domain.appointment.commands.mappers.CreateAppointmentFeedbackRequestMapper;
import inno.edu.api.domain.appointment.models.Feedback;
import inno.edu.api.domain.appointment.repositories.FeedbackRepository;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AppointmentFactory.appointment;
import static inno.edu.api.support.AppointmentFactory.createAppointmentFeedbackRequest;
import static inno.edu.api.support.AppointmentFactory.newFeedback;
import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateAppointmentFeedbackCommandTest {
    @Mock
    private UUIDGeneratorService uuidGeneratorService;

    @Mock
    private CreateAppointmentFeedbackRequestMapper createAppointmentFeedbackRequestMapper;

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private AppointmentExistsAssertion appointmentExistsAssertion;

    @InjectMocks
    private CreateAppointmentFeedbackCommand createAppointmentFeedbackCommand;

    @Before
    public void setUp() {
        when(uuidGeneratorService.generate()).thenReturn(randomUUID());

        when(createAppointmentFeedbackRequestMapper.toFeedback(createAppointmentFeedbackRequest()))
          .thenReturn(newFeedback(null, appointment().getId()));
    }

    @Test
    public void shouldSaveNewAppointment() {
        Feedback newFeedback = newFeedback(uuidGeneratorService.generate(), appointment().getId());

        when(feedbackRepository.save(newFeedback)).thenReturn(newFeedback);

        Feedback feedback = createAppointmentFeedbackCommand.run(appointment().getId(), createAppointmentFeedbackRequest());
        assertThat(feedback, is(newFeedback));
    }

    @Test
    public void shouldRunAllAssertions() {
        createAppointmentFeedbackCommand.run(appointment().getId(), createAppointmentFeedbackRequest());
        verify(appointmentExistsAssertion).run(appointment().getId());
    }
}