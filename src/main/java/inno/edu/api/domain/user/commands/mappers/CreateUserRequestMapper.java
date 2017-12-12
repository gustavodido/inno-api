package inno.edu.api.domain.user.commands.mappers;

import inno.edu.api.domain.user.commands.dtos.CreateUserRequest;
import inno.edu.api.domain.user.models.ApplicationUser;
import org.mapstruct.Mapper;

@Mapper
public interface CreateUserRequestMapper {
    ApplicationUser toUser(CreateUserRequest createUserRequest);
}
