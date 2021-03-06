package inno.edu.api.domain.skill.models.dtos;

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
public class UpdateSkillRequest {
    @NotNull
    @Size(max = 50)
    private String title;

    @NotNull
    @Size(max = 255)
    private String description;
}
