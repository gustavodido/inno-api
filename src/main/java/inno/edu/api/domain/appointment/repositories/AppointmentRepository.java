package inno.edu.api.domain.appointment.repositories;

import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.models.AppointmentStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends CrudRepository<Appointment, UUID> {
    List<Appointment> findAppointmentByUniversityId(UUID universityId);
    List<Appointment> findAppointmentByUniversityIdAndStatus(UUID universityId, AppointmentStatus status);

    List<Appointment> findAppointmentByMentorId(UUID universityId);
    List<Appointment> findAppointmentByMentorIdAndStatus(UUID universityId, AppointmentStatus status);

    List<Appointment> findAppointmentByMenteeId(UUID universityId);
    List<Appointment> findAppointmentByMenteeIdAndStatus(UUID universityId, AppointmentStatus status);

}
