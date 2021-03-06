package inno.edu.api.controllers.profile;

import inno.edu.api.domain.profile.root.commands.CreateProfileCommand;
import inno.edu.api.domain.profile.root.commands.DeleteProfileCommand;
import inno.edu.api.domain.profile.root.commands.UpdateProfileCommand;
import inno.edu.api.domain.profile.root.models.Profile;
import inno.edu.api.domain.profile.root.models.dtos.CreateProfileRequest;
import inno.edu.api.domain.profile.root.models.dtos.UpdateProfileRequest;
import inno.edu.api.domain.profile.root.models.projections.mappers.ProfileProjectionMapper;
import inno.edu.api.domain.profile.root.models.resources.ProfileProjectionResource;
import inno.edu.api.domain.profile.root.models.resources.ProfileResource;
import inno.edu.api.domain.profile.root.queries.GetProfileByIdQuery;
import inno.edu.api.domain.profile.root.queries.GetProfilesQuery;
import inno.edu.api.infrastructure.web.ResourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static inno.edu.api.infrastructure.configuration.StaticConstants.APPLICATION_HAL_JSON;
import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/api/profiles", produces = APPLICATION_HAL_JSON)
public class ProfileController {

    private final ResourceBuilder resourceBuilder;

    private final GetProfilesQuery getProfilesQuery;
    private final GetProfileByIdQuery getProfileByIdQuery;

    private final UpdateProfileCommand updateProfileCommand;
    private final CreateProfileCommand createProfileCommand;
    private final DeleteProfileCommand deleteProfileCommand;

    private final ProfileProjectionMapper profileProjectionMapper;

    @Autowired
    public ProfileController(ResourceBuilder resourceBuilder, GetProfilesQuery getProfilesQuery, GetProfileByIdQuery getProfileByIdQuery, UpdateProfileCommand updateProfileCommand, CreateProfileCommand createProfileCommand, DeleteProfileCommand deleteProfileCommand, ProfileProjectionMapper profileProjectionMapper) {
        this.resourceBuilder = resourceBuilder;
        this.getProfilesQuery = getProfilesQuery;
        this.getProfileByIdQuery = getProfileByIdQuery;
        this.updateProfileCommand = updateProfileCommand;
        this.createProfileCommand = createProfileCommand;
        this.deleteProfileCommand = deleteProfileCommand;
        this.profileProjectionMapper = profileProjectionMapper;
    }

    @GetMapping
    public Resources<Object> all() {
        List<Profile> profiles = getProfilesQuery.run();
        return resourceBuilder.wrappedFrom(profiles, ProfileResource::new, ProfileResource.class);
    }

    @GetMapping("/{id}")
    public ProfileProjectionResource get(@PathVariable UUID id) {
        return profileProjectionMapper.toProfileProjectionResource(getProfileByIdQuery.run(id));
    }

    @PostMapping
    public ResponseEntity<Profile> post(@Valid @RequestBody CreateProfileRequest request) {
        ProfileResource profileResource = new ProfileResource(createProfileCommand.run(request));
        return profileResource.toCreated();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profile> put(@PathVariable UUID id, @Valid @RequestBody UpdateProfileRequest request) {
        ProfileResource profileResource = new ProfileResource(updateProfileCommand.run(id, request));
        return profileResource.toUpdated();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        deleteProfileCommand.run(id);
        return noContent().build();
    }
}
