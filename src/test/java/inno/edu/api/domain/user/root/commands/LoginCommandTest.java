package inno.edu.api.domain.user.root.commands;

import inno.edu.api.domain.user.root.models.dtos.LoginResponse;
import inno.edu.api.domain.user.root.exceptions.InvalidUsernameOrPasswordException;
import inno.edu.api.domain.user.root.repositories.UserRepository;
import inno.edu.api.infrastructure.security.services.TokenGeneratorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.UserFactory.fei;
import static inno.edu.api.support.UserFactory.feiCredentials;
import static inno.edu.api.support.UserFactory.feiLoginResponse;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginCommandTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenGeneratorService tokenGeneratorService;

    @InjectMocks
    private LoginCommand loginCommand;

    @Test
    public void shouldReturnUserIfCredentialsAreValid() {
        when(userRepository.findOneByUsernameAndPassword(fei().getUsername(), fei().getPassword()))
                .thenReturn(fei());

        LoginResponse loginResponse = loginCommand.run(feiCredentials());
        assertThat(loginResponse.getUser(), is(fei()));
    }

    @Test
    public void shouldGenerateTokenIfCredentialsAreValid() {
        when(userRepository.findOneByUsernameAndPassword(fei().getUsername(), fei().getPassword()))
                .thenReturn(fei());

        when(tokenGeneratorService.generate(fei().getUsername(), emptyList()))
                .thenReturn(feiLoginResponse().getToken());

        LoginResponse loginResponse = loginCommand.run(feiCredentials());

        assertThat(loginResponse, is(feiLoginResponse()));
    }

    @Test(expected = InvalidUsernameOrPasswordException.class)
    public void shouldThrowExceptionIfCredentialsAreNotValid() {
        when(userRepository.findOneByUsernameAndPassword(fei().getUsername(), fei().getPassword()))
                .thenReturn(null);

        loginCommand.run(feiCredentials());
    }
}