package inno.edu.api.domain.user.root.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    @NotNull
    @Size(max = 50)
    private String firstName;

    @NotNull
    @Size(max = 50)
    private String lastName;

    @NotNull
    @Size(max = 30)
    private String username;

    @NotNull
    @Size(max = 255)
    @Email
    private String email;

    @NotNull
    @Size(max = 30)
    private String password;

    @NotNull
    @Size(max = 30)
    private String confirmPassword;

    @Size(max = 255)
    private String deviceId;
}
