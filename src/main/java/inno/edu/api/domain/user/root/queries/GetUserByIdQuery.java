package inno.edu.api.domain.user.root.queries;

import inno.edu.api.domain.user.root.exceptions.UserNotFoundException;
import inno.edu.api.domain.user.root.models.ApplicationUser;
import inno.edu.api.domain.user.root.repositories.UserRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Query
public class GetUserByIdQuery {
    private final UserRepository userRepository;

    public GetUserByIdQuery(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ApplicationUser run(UUID id) {
        return ofNullable(userRepository.findOne(id))
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
