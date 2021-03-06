package inno.edu.api.domain.school.root.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSchoolRequest {
    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private String description;
}
