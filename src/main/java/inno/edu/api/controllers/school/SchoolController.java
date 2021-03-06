package inno.edu.api.controllers.school;

import inno.edu.api.domain.profile.root.models.projections.ProfileProjection;
import inno.edu.api.domain.profile.root.models.projections.mappers.ProfileProjectionMapper;
import inno.edu.api.domain.profile.root.models.resources.ProfileProjectionResource;
import inno.edu.api.domain.profile.root.queries.GetProfilesBySchoolIdQuery;
import inno.edu.api.domain.school.root.commands.CreateSchoolCommand;
import inno.edu.api.domain.school.root.commands.DeleteSchoolCommand;
import inno.edu.api.domain.school.root.commands.UpdateSchoolCommand;
import inno.edu.api.domain.school.root.models.School;
import inno.edu.api.domain.school.root.models.dtos.CreateSchoolRequest;
import inno.edu.api.domain.school.root.models.dtos.UpdateSchoolRequest;
import inno.edu.api.domain.school.root.models.resources.SchoolResource;
import inno.edu.api.domain.school.root.queries.GetSchoolByIdQuery;
import inno.edu.api.domain.school.root.queries.GetSchoolsQuery;
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
@RequestMapping(value = "/api/schools", produces = APPLICATION_HAL_JSON)
public class SchoolController {
    private final ResourceBuilder resourceBuilder;

    private final CreateSchoolCommand createSchoolCommand;
    private final UpdateSchoolCommand updateSchoolCommand;
    private final DeleteSchoolCommand deleteSchoolCommand;

    private final GetSchoolsQuery getSchoolsQuery;
    private final GetSchoolByIdQuery getSchoolByIdQuery;
    private final GetProfilesBySchoolIdQuery getProfilesBySchoolIdQuery;

    private final ProfileProjectionMapper profileProjectionMapper;

    @Autowired
    public SchoolController(CreateSchoolCommand createSchoolCommand, UpdateSchoolCommand updateSchoolCommand, DeleteSchoolCommand deleteSchoolCommand, GetSchoolsQuery getSchoolsQuery, GetSchoolByIdQuery getSchoolByIdQuery, GetProfilesBySchoolIdQuery getProfilesBySchoolIdQuery, ResourceBuilder resourceBuilder, ProfileProjectionMapper profileProjectionMapper) {
        this.getSchoolsQuery = getSchoolsQuery;
        this.createSchoolCommand = createSchoolCommand;
        this.updateSchoolCommand = updateSchoolCommand;
        this.deleteSchoolCommand = deleteSchoolCommand;
        this.getSchoolByIdQuery = getSchoolByIdQuery;
        this.getProfilesBySchoolIdQuery = getProfilesBySchoolIdQuery;
        this.resourceBuilder = resourceBuilder;
        this.profileProjectionMapper = profileProjectionMapper;
    }

    @GetMapping
    public Resources<Object> all() {
        List<School> schools = getSchoolsQuery.run();
        return resourceBuilder.wrappedFrom(schools, SchoolResource::new, SchoolResource.class);
    }

    @GetMapping("/{id}")
    public SchoolResource get(@PathVariable UUID id) {
        return new SchoolResource(getSchoolByIdQuery.run(id));
    }

    @GetMapping("/{id}/mentors")
    public Resources<Object> allMentorsProfile(@PathVariable UUID id) {
        List<ProfileProjection> projections = profileProjectionMapper.toProfileProjections(getProfilesBySchoolIdQuery.run(id));
        return resourceBuilder.wrappedFrom(projections, ProfileProjectionResource::new, ProfileProjectionResource.class);
    }

    @PostMapping
    public ResponseEntity<School> post(@Valid @RequestBody CreateSchoolRequest request) {
        SchoolResource schoolResource = new SchoolResource(createSchoolCommand.run(request));
        return schoolResource.toCreated();
    }

    @PutMapping("/{id}")
    public ResponseEntity<School> put(@PathVariable UUID id, @Valid @RequestBody UpdateSchoolRequest request) {
        SchoolResource schoolResource = new SchoolResource(updateSchoolCommand.run(id, request));
        return schoolResource.toUpdated();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        deleteSchoolCommand.run(id);
        return noContent().build();
    }
}