package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.AvailabilityResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.availability.commands.CreateAvailabilityCommand;
import inno.edu.api.domain.availability.commands.UpdateAvailabilityCommand;
import inno.edu.api.domain.availability.exceptions.AvailabilityNotFoundException;
import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;
import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/api/availability", produces = "application/hal+json")
public class AvailabilityController {
    private final AvailabilityRepository availabilityRepository;

    private final ResourceBuilder resourceBuilder;
    private final CreateAvailabilityCommand createAvailabilityCommand;
    private final UpdateAvailabilityCommand updateAvailabilityCommand;

    public AvailabilityController(AvailabilityRepository availabilityRepository, ResourceBuilder resourceBuilder, CreateAvailabilityCommand createAvailabilityCommand, UpdateAvailabilityCommand updateAvailabilityCommand) {
        this.availabilityRepository = availabilityRepository;
        this.resourceBuilder = resourceBuilder;
        this.createAvailabilityCommand = createAvailabilityCommand;
        this.updateAvailabilityCommand = updateAvailabilityCommand;
    }

    @GetMapping
    public Resources<AvailabilityResource> all() {
        Iterable<Availability> availability = availabilityRepository.findAll();
        return resourceBuilder.from(availability, AvailabilityResource::new);
    }

    @GetMapping("/mentor/{mentorId}")
    public Resources<AvailabilityResource> allByMentor(@PathVariable UUID mentorId) {
        List<Availability> availability = availabilityRepository.findByMentorId(mentorId);
        return resourceBuilder.from(availability, AvailabilityResource::new);
    }

    @GetMapping("/university/{universityId}")
    public Resources<AvailabilityResource> allByUniversity(@PathVariable UUID universityId) {
        List<Availability> availability = availabilityRepository.findByUniversityId(universityId);
        return resourceBuilder.from(availability, AvailabilityResource::new);
    }

    @GetMapping("/{id}")
    public AvailabilityResource get(@PathVariable UUID id) {
        Optional<Availability> availability = ofNullable(availabilityRepository.findOne(id));
        return new AvailabilityResource(availability.orElseThrow(() -> new AvailabilityNotFoundException(id)));
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Availability availability) {
        AvailabilityResource availabilityResource = new AvailabilityResource(createAvailabilityCommand.run(availability));
        return availabilityResource.toCreated();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable UUID id, @RequestBody Availability availability) {
        AvailabilityResource availabilityResource = new AvailabilityResource(updateAvailabilityCommand.run(id, availability));
        return availabilityResource.toUpdated();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        if (!availabilityRepository.exists(id)) {
            throw new AvailabilityNotFoundException(id);
        }
        availabilityRepository.delete(id);

        return noContent().build();
    }
}
