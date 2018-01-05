package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.assertions.ProfileExistsAssertion;
import inno.edu.api.domain.profile.commands.dtos.CreateExperienceRequest;
import inno.edu.api.domain.profile.commands.mappers.CreateExperienceRequestMapper;
import inno.edu.api.domain.profile.models.Experience;
import inno.edu.api.domain.profile.repositories.ExperienceRepository;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;

import java.util.UUID;

@Command
public class CreateExperienceCommand {
    private final UUIDGeneratorService uuidGeneratorService;

    private final CreateExperienceRequestMapper createExperienceRequestMapper;
    private final ProfileExistsAssertion profileExistsAssertion;

    private final ExperienceRepository experienceRepository;

    public CreateExperienceCommand(UUIDGeneratorService uuidGeneratorService, CreateExperienceRequestMapper createExperienceRequestMapper, ProfileExistsAssertion profileExistsAssertion, ExperienceRepository experienceRepository) {
        this.uuidGeneratorService = uuidGeneratorService;
        this.createExperienceRequestMapper = createExperienceRequestMapper;
        this.profileExistsAssertion = profileExistsAssertion;
        this.experienceRepository = experienceRepository;
    }

    public Experience run(UUID profileId, CreateExperienceRequest createExperienceRequest) {
        profileExistsAssertion.run(profileId);

        Experience experience = createExperienceRequestMapper.toExperience(createExperienceRequest);
        experience.setId(uuidGeneratorService.generate());
        experience.setProfileId(profileId);

        return experienceRepository.save(experience);
    }
}
