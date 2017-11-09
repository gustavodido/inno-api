package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.controllers.resources.UserResource;
import inno.edu.api.domain.user.commands.CreateUserCommand;
import inno.edu.api.domain.user.commands.UpdateUserCommand;
import inno.edu.api.domain.user.exceptions.UserNotFoundException;
import inno.edu.api.domain.user.models.User;
import inno.edu.api.domain.user.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.Resources;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static inno.edu.api.common.ModelFactories.user;
import static inno.edu.api.common.ModelFactories.users;
import static inno.edu.api.common.ModelFactories.usersResource;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    @Mock
    private ResourceBuilder resourceBuilder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CreateUserCommand createUserCommand;

    @Mock
    private UpdateUserCommand updateUserCommand;

    @InjectMocks
    private UserController userController;

    @Before
    public void setup() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldGetUserUsingId() {
        when(userRepository.findOne(eq(user().getId()))).thenReturn(user());

        UserResource userResource = userController.get(user().getId());

        assertThat(userResource.getUser(), is(user()));
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldRaiseExceptionIfUserNotFound() {
        when(userRepository.findOne(any())).thenReturn(null);

        userController.get(user().getId());
    }

    @Test
    public void shouldListAllUsers() {
        when(userRepository.findAll()).thenReturn(users());
        when(resourceBuilder.fromResources(anyListOf(UserResource.class))).thenReturn(usersResource());

        Resources<UserResource> allResources = userController.all();

        assertThat(allResources, is(usersResource()));
    }

    @Test
    public void shouldCreateNewUser() {
        ArgumentCaptor<User> argumentCaptor = forClass(User.class);
        when(createUserCommand.run(argumentCaptor.capture())).thenReturn(user());

        userController.post(user());

        verify(createUserCommand).run(argumentCaptor.capture());
    }

    @Test
    public void shouldUpdateUser() {
        when(updateUserCommand.run(user().getId(), user())).thenReturn(user());

        userController.put(user().getId(), user());

        verify(updateUserCommand).run(user().getId(), user());
    }

    @Test
    public void shouldUDeleteUser() {
        when(userRepository.exists(user().getId())).thenReturn(true);

        userController.delete(user().getId());

        verify(userRepository).delete(user().getId());
    }

}