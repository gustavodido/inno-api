package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.AppointmentResource;
import inno.edu.api.controllers.resources.EstimationResource;
import inno.edu.api.controllers.resources.FeedbackResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.appointment.commands.CalculateAppointmentFeeCommand;
import inno.edu.api.domain.appointment.commands.CreateAppointmentCommand;
import inno.edu.api.domain.appointment.commands.CreateFeedbackCommand;
import inno.edu.api.domain.appointment.commands.DeleteAppointmentCommand;
import inno.edu.api.domain.appointment.commands.DeleteFeedbackCommand;
import inno.edu.api.domain.appointment.commands.UpdateAppointmentCommand;
import inno.edu.api.domain.appointment.commands.UpdateAppointmentStatusCommand;
import inno.edu.api.domain.appointment.commands.dtos.AppointmentReason;
import inno.edu.api.domain.appointment.commands.dtos.CalculateAppointmentFeeRequest;
import inno.edu.api.domain.appointment.commands.dtos.CreateAppointmentRequest;
import inno.edu.api.domain.appointment.commands.dtos.CreateFeedbackRequest;
import inno.edu.api.domain.appointment.commands.dtos.UpdateAppointmentRequest;
import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.models.AppointmentStatus;
import inno.edu.api.domain.appointment.models.Feedback;
import inno.edu.api.domain.appointment.queries.GetAppointmentByIdQuery;
import inno.edu.api.domain.appointment.queries.GetAppointmentsByMenteeIdQuery;
import inno.edu.api.domain.appointment.queries.GetAppointmentsByMentorIdQuery;
import inno.edu.api.domain.appointment.queries.GetFeedbacksByAppointmentByIdQuery;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static inno.edu.api.domain.appointment.models.AppointmentStatus.ACCEPTED;
import static inno.edu.api.domain.appointment.models.AppointmentStatus.CANCELED;
import static inno.edu.api.domain.appointment.models.AppointmentStatus.DECLINED;
import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/api/appointments", produces = "application/hal+json")
public class AppointmentController {
    private final ResourceBuilder resourceBuilder;

    private final AppointmentRepository appointmentRepository;

    private final CreateAppointmentCommand createAppointmentCommand;
    private final UpdateAppointmentCommand updateAppointmentCommand;
    private final DeleteAppointmentCommand deleteAppointmentCommand;
    private final UpdateAppointmentStatusCommand updateAppointmentStatusCommand;
    private final CalculateAppointmentFeeCommand calculateAppointmentFeeCommand;

    private final GetAppointmentByIdQuery getAppointmentByIdQuery;
    private final GetAppointmentsByMentorIdQuery getAppointmentsByMentorIdQuery;
    private final GetAppointmentsByMenteeIdQuery getAppointmentsByMenteeIdQuery;

    private final CreateFeedbackCommand createFeedbackCommand;
    private final DeleteFeedbackCommand deleteFeedbackCommand;
    private final GetFeedbacksByAppointmentByIdQuery getFeedbacksByAppointmentByIdQuery;

    public AppointmentController(AppointmentRepository appointmentRepository, ResourceBuilder resourceBuilder, CreateAppointmentCommand createAppointmentCommand, UpdateAppointmentCommand updateAppointmentCommand, DeleteAppointmentCommand deleteAppointmentCommand, GetAppointmentByIdQuery getAppointmentByIdQuery, GetAppointmentsByMentorIdQuery getAppointmentsByMentorIdQuery, GetAppointmentsByMenteeIdQuery getAppointmentsByMenteeIdQuery, UpdateAppointmentStatusCommand updateAppointmentStatusCommand, CalculateAppointmentFeeCommand calculateAppointmentFeeCommand, CreateFeedbackCommand createFeedbackCommand, DeleteFeedbackCommand deleteFeedbackCommand, GetFeedbacksByAppointmentByIdQuery getFeedbacksByAppointmentByIdQuery) {
        this.appointmentRepository = appointmentRepository;
        this.resourceBuilder = resourceBuilder;
        this.createAppointmentCommand = createAppointmentCommand;
        this.updateAppointmentCommand = updateAppointmentCommand;
        this.deleteAppointmentCommand = deleteAppointmentCommand;
        this.getAppointmentByIdQuery = getAppointmentByIdQuery;
        this.getAppointmentsByMentorIdQuery = getAppointmentsByMentorIdQuery;
        this.getAppointmentsByMenteeIdQuery = getAppointmentsByMenteeIdQuery;
        this.updateAppointmentStatusCommand = updateAppointmentStatusCommand;
        this.calculateAppointmentFeeCommand = calculateAppointmentFeeCommand;
        this.createFeedbackCommand = createFeedbackCommand;
        this.deleteFeedbackCommand = deleteFeedbackCommand;
        this.getFeedbacksByAppointmentByIdQuery = getFeedbacksByAppointmentByIdQuery;
    }

    @GetMapping
    public Resources<Object> all() {
        Iterable<Appointment> appointments = appointmentRepository.findAll();
        return resourceBuilder.wrappedFrom(appointments, AppointmentResource::new, AppointmentResource.class);
    }

    @GetMapping("/mentor/{mentorId}")
    public Resources<Object> allByMentor(@PathVariable UUID mentorId,
                                         @RequestParam(required = false) AppointmentStatus status) {
        List<Appointment> appointments = getAppointmentsByMentorIdQuery.run(mentorId, status);
        return resourceBuilder.wrappedFrom(appointments, AppointmentResource::new, AppointmentResource.class);
    }

    @GetMapping("/mentee/{menteeId}")
    public Resources<Object> allByMentee(@PathVariable UUID menteeId,
                                         @RequestParam(required = false) AppointmentStatus status) {
        List<Appointment> appointments = getAppointmentsByMenteeIdQuery.run(menteeId, status);
        return resourceBuilder.wrappedFrom(appointments, AppointmentResource::new, AppointmentResource.class);
    }

    @GetMapping("/{id}")
    public AppointmentResource get(@PathVariable UUID id) {
        return new AppointmentResource(getAppointmentByIdQuery.run(id));
    }

    @PostMapping
    public ResponseEntity<Appointment> post(@Valid @RequestBody CreateAppointmentRequest request) {
        AppointmentResource appointmentResource = new AppointmentResource(createAppointmentCommand.run(request));
        return appointmentResource.toCreated();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> put(@PathVariable UUID id, @Valid @RequestBody UpdateAppointmentRequest request) {
        AppointmentResource appointmentResource = new AppointmentResource(updateAppointmentCommand.run(id, request));
        return appointmentResource.toUpdated();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        deleteAppointmentCommand.run(id);
        return noContent().build();
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable UUID id, @Valid @RequestBody AppointmentReason reason) {
        updateAppointmentStatusCommand.run(id, reason, CANCELED);
        return noContent().build();
    }

    @PutMapping("/{id}/decline")
    public ResponseEntity<?> decline(@PathVariable UUID id, @Valid @RequestBody AppointmentReason reason) {
        updateAppointmentStatusCommand.run(id, reason, DECLINED);
        return noContent().build();
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<?> accept(@PathVariable UUID id) {
        updateAppointmentStatusCommand.run(id, new AppointmentReason(), ACCEPTED);
        return noContent().build();
    }

    @GetMapping("/estimate")
    public EstimationResource estimate(CalculateAppointmentFeeRequest request) {
        BigDecimal fee = calculateAppointmentFeeCommand.run(request);
        return new EstimationResource(fee);
    }

    @PostMapping("/{id}/feedbacks")
    public ResponseEntity<Feedback> postFeedback(@PathVariable UUID id, @Valid @RequestBody CreateFeedbackRequest request) {
        FeedbackResource feedbackResource = new FeedbackResource(createFeedbackCommand.run(id, request));
        return feedbackResource.toCreated();
    }

    @GetMapping("/{id}/feedbacks")
    public Resources<Object> allFeedbacks(@PathVariable UUID id) {
        List<Feedback> feedbacks = getFeedbacksByAppointmentByIdQuery.run(id);
        return resourceBuilder.wrappedFrom(feedbacks, FeedbackResource::new, FeedbackResource.class);
    }

    @DeleteMapping("/feedbacks/{id}")
    public ResponseEntity<?> deleteFeedback(@PathVariable UUID id) {
        deleteFeedbackCommand.run(id);
        return noContent().build();
    }

}
