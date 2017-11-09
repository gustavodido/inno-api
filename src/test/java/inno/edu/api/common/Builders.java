package inno.edu.api.common;

import inno.edu.api.controllers.resources.UserResource;
import inno.edu.api.domain.user.models.User;
import org.springframework.hateoas.Resources;

import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.UUID.fromString;

public class Builders {
    public static User user() {
        return User.builder().id(fromString("841b43e1-08be-4401-968f-6ee45370a973")).build();
    }

    private static UserResource userResource() {
        return new UserResource(user());
    }

    public static List<User> users() {
        return singletonList(user());
    }

    public static Resources<UserResource> usersResource() {
        return new Resources<>(singletonList(userResource()));
    }

}