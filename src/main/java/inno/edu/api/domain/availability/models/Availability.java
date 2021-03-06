package inno.edu.api.domain.availability.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Availability {
    @Id
    private UUID id;

    private UUID mentorProfileId;

    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;
}
