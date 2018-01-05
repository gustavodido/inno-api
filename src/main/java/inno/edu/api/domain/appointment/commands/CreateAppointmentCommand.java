package inno.edu.api.domain.appointment.commands;

import inno.edu.api.domain.appointment.commands.dtos.CalculateAppointmentFeeRequest;
import inno.edu.api.domain.appointment.commands.dtos.CreateAppointmentRequest;
import inno.edu.api.domain.appointment.commands.mappers.CalculateAppointmentFeeRequestMapper;
import inno.edu.api.domain.appointment.commands.mappers.CreateAppointmentRequestMapper;
import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;

import java.math.BigDecimal;

import static inno.edu.api.domain.appointment.models.AppointmentStatus.PROPOSED;

@Command
public class CreateAppointmentCommand {
    private final UUIDGeneratorService uuidGeneratorService;

    private final CreateAppointmentRequestMapper createAppointmentRequestMapper;
    private final CalculateAppointmentFeeRequestMapper calculateAppointmentFeeRequestMapper;

    private final AppointmentRepository appointmentRepository;

    private final ProfileExistsAssertion profileExistsAssertion;

    private final CalculateAppointmentFeeCommand calculateAppointmentFeeCommand;

    public CreateAppointmentCommand(UUIDGeneratorService uuidGeneratorService, CreateAppointmentRequestMapper createAppointmentRequestMapper, CalculateAppointmentFeeRequestMapper calculateAppointmentFeeRequestMapper, AppointmentRepository appointmentRepository, ProfileExistsAssertion profileExistsAssertion, CalculateAppointmentFeeCommand calculateAppointmentFeeCommand) {
        this.uuidGeneratorService = uuidGeneratorService;
        this.createAppointmentRequestMapper = createAppointmentRequestMapper;
        this.calculateAppointmentFeeRequestMapper = calculateAppointmentFeeRequestMapper;
        this.appointmentRepository = appointmentRepository;
        this.profileExistsAssertion = profileExistsAssertion;
        this.calculateAppointmentFeeCommand = calculateAppointmentFeeCommand;
    }

    public Appointment run(CreateAppointmentRequest createAppointmentRequest) {
        profileExistsAssertion.run(createAppointmentRequest.getMenteeProfileId());
        profileExistsAssertion.run(createAppointmentRequest.getMentorProfileId());

        Appointment appointment = createAppointmentRequestMapper.toAppointment(createAppointmentRequest);

        appointment.setId(uuidGeneratorService.generate());
        appointment.setStatus(PROPOSED);
        appointment.setFee(getAppointmentFee(appointment));

        return appointmentRepository.save(appointment);
    }

    private BigDecimal getAppointmentFee(Appointment appointment) {
        CalculateAppointmentFeeRequest calculateAppointmentFeeRequest =
                calculateAppointmentFeeRequestMapper.toAppointmentFeeRequest(appointment);

       return calculateAppointmentFeeCommand.run(calculateAppointmentFeeRequest);
    }
}
