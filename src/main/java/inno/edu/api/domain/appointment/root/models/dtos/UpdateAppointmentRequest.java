package inno.edu.api.domain.appointment.root.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAppointmentRequest {
    @NotNull
    private LocalDateTime fromDateTime;

    @NotNull
    @DateTimeFormat(iso = DATE_TIME)
    private LocalDateTime toDateTime;

    @NotNull
    @DateTimeFormat(iso = DATE_TIME)
    private String description;
}
