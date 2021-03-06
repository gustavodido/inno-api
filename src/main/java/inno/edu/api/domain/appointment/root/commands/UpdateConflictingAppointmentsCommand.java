package inno.edu.api.domain.appointment.root.commands;

import inno.edu.api.domain.appointment.root.models.Appointment;
import inno.edu.api.domain.appointment.root.queries.GetAppointmentsByMentorProfileIdQuery;
import inno.edu.api.domain.appointment.root.repositories.AppointmentRepository;
import inno.edu.api.domain.user.transaction.commands.CreateTransactionForAppointmentCommand;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.google.common.collect.Streams.stream;
import static inno.edu.api.domain.appointment.root.models.AppointmentStatus.PROPOSED;
import static inno.edu.api.domain.appointment.root.models.AppointmentStatus.UNAVAILABLE;
import static java.util.stream.Collectors.toList;

@Command
public class UpdateConflictingAppointmentsCommand {
    private final AppointmentRepository appointmentRepository;
    private final GetAppointmentsByMentorProfileIdQuery getAppointmentsByMentorProfileIdQuery;
    private final CreateTransactionForAppointmentCommand createTransactionForAppointmentCommand;

    public UpdateConflictingAppointmentsCommand(AppointmentRepository appointmentRepository, GetAppointmentsByMentorProfileIdQuery getAppointmentsByMentorProfileIdQuery, CreateTransactionForAppointmentCommand createTransactionForAppointmentCommand) {
        this.appointmentRepository = appointmentRepository;
        this.getAppointmentsByMentorProfileIdQuery = getAppointmentsByMentorProfileIdQuery;
        this.createTransactionForAppointmentCommand = createTransactionForAppointmentCommand;
    }

    public List<Appointment> run(Appointment driverAppointment) {
        List<Appointment> otherAppointments = getAppointmentsByMentorProfileIdQuery
                .run(driverAppointment.getMentorProfileId(), PROPOSED)
                .stream()
                .filter(isConflicting(driverAppointment))
                .map(appointment -> {
                    appointment.setStatus(UNAVAILABLE);
                    return appointment;
                })
                .collect(toList());

        return stream(appointmentRepository
                .save(otherAppointments))
                .map(this::createTransactionForConflictingAppointment)
                .collect(Collectors.toList());
    }

    private Appointment createTransactionForConflictingAppointment(Appointment appointment) {
        createTransactionForAppointmentCommand.run(appointment.getId());
        return appointment;
    }

    private Predicate<Appointment> isConflicting(Appointment driverAppointment) {
        Predicate<Appointment> other = appointment -> !appointment.getId().equals(driverAppointment.getId());

        Predicate<Appointment> firstRange = appointment ->
                appointment.getToDateTime().isAfter(driverAppointment.getFromDateTime());

        Predicate<Appointment> secondRange = appointment ->
                appointment.getFromDateTime().isBefore(driverAppointment.getToDateTime());

        return other
                .and(firstRange)
                .and(secondRange);
    }
}
