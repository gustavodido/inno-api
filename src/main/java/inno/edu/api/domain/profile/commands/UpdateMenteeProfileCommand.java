package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.exceptions.ProfileNotFoundException;
import inno.edu.api.domain.profile.models.MenteeProfile;
import inno.edu.api.domain.profile.repositories.MenteeProfileRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Command
public class UpdateMenteeProfileCommand {
    private final MenteeProfileRepository profileRepository;

    public UpdateMenteeProfileCommand(MenteeProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public MenteeProfile run(UUID id, MenteeProfile profile) {
        MenteeProfile currentMenteeProfile = ofNullable(profileRepository.findOne(id))
                .orElseThrow(() -> new ProfileNotFoundException(id));

        currentMenteeProfile.setId(id);
        currentMenteeProfile.setEmail(profile.getEmail());
        currentMenteeProfile.setDescription(profile.getDescription());

        return profileRepository.save(currentMenteeProfile);
    }
}
