package inno.edu.api.domain.user.root.commands;

import inno.edu.api.domain.user.root.models.dtos.LoginRequest;
import inno.edu.api.domain.user.root.models.dtos.LoginResponse;
import inno.edu.api.domain.user.root.exceptions.InvalidUsernameOrPasswordException;
import inno.edu.api.domain.user.root.models.ApplicationUser;
import inno.edu.api.domain.user.root.repositories.UserRepository;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.security.services.TokenGeneratorService;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@Command
public class LoginCommand {
    private final UserRepository userRepository;
    private final TokenGeneratorService tokenGeneratorService;

    public LoginCommand(UserRepository userRepository, TokenGeneratorService tokenGeneratorService) {
        this.userRepository = userRepository;
        this.tokenGeneratorService = tokenGeneratorService;
    }

    public LoginResponse run(LoginRequest credentials) {
        ApplicationUser user = ofNullable(userRepository.findOneByUsernameAndPassword(credentials.getUsername(), credentials.getPassword()))
                .orElseThrow(InvalidUsernameOrPasswordException::new);

        String token = tokenGeneratorService.generate(user.getUsername(), emptyList());
        return LoginResponse.builder()
                .user(user)
                .token(token)
                .build();
    }


}
