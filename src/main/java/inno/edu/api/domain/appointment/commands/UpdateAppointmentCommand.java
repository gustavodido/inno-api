package inno.edu.api.domain.appointment.commands;

import inno.edu.api.domain.appointment.commands.dtos.CalculateAppointmentFeeRequest;
import inno.edu.api.domain.appointment.commands.dtos.UpdateAppointmentRequest;
import inno.edu.api.domain.appointment.commands.mappers.CalculateAppointmentFeeRequestMapper;
import inno.edu.api.domain.appointment.commands.mappers.UpdateAppointmentRequestMapper;
import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.queries.GetAppointmentByIdQuery;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.math.BigDecimal;
import java.util.UUID;

@Command
public class UpdateAppointmentCommand {
    private final UpdateAppointmentRequestMapper updateAppointmentRequestMapper;
    private final CalculateAppointmentFeeRequestMapper calculateAppointmentFeeRequestMapper;

    private final GetAppointmentByIdQuery getAppointmentByIdQuery;
    private final AppointmentRepository appointmentRepository;

    private final CalculateAppointmentFeeCommand calculateAppointmentFeeCommand;

    public UpdateAppointmentCommand(UpdateAppointmentRequestMapper updateAppointmentRequestMapper, CalculateAppointmentFeeRequestMapper calculateAppointmentFeeRequestMapper, GetAppointmentByIdQuery getAppointmentByIdQuery, AppointmentRepository appointmentRepository, CalculateAppointmentFeeCommand calculateAppointmentFeeCommand) {
        this.updateAppointmentRequestMapper = updateAppointmentRequestMapper;
        this.calculateAppointmentFeeRequestMapper = calculateAppointmentFeeRequestMapper;
        this.getAppointmentByIdQuery = getAppointmentByIdQuery;
        this.appointmentRepository = appointmentRepository;
        this.calculateAppointmentFeeCommand = calculateAppointmentFeeCommand;
    }

    public Appointment run(UUID id, UpdateAppointmentRequest updateAppointmentRequest) {
        Appointment currentAppointment = getAppointmentByIdQuery.run(id);
        updateAppointmentRequestMapper.setAppointment(updateAppointmentRequest, currentAppointment);

        currentAppointment.setFee(getAppointmentFee(currentAppointment));

        return appointmentRepository.save(currentAppointment);
    }

    private BigDecimal getAppointmentFee(Appointment appointment) {
        CalculateAppointmentFeeRequest calculateAppointmentFeeRequest =
                calculateAppointmentFeeRequestMapper.toAppointmentFeeRequest(appointment);

        return calculateAppointmentFeeCommand.run(calculateAppointmentFeeRequest);
    }
}
