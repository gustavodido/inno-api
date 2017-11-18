package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.exceptions.ProfileNotFoundException;
import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Command
public class UpdateMentorProfileCommand {
    private final MentorProfileRepository profileRepository;

    public UpdateMentorProfileCommand(MentorProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public MentorProfile run(UUID id, MentorProfile profile) {
        MentorProfile currentMentorProfile = ofNullable(profileRepository.findOne(id))
                .orElseThrow(() -> new ProfileNotFoundException(id));

        currentMentorProfile.setId(id);
        currentMentorProfile.setEmail(profile.getEmail());
        currentMentorProfile.setDescription(profile.getDescription());
        currentMentorProfile.setStatus(profile.getStatus());

        return profileRepository.save(currentMentorProfile);
    }
}
