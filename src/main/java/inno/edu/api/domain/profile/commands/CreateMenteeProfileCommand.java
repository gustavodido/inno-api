package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.exceptions.MenteeProfileAlreadyCreatedException;
import inno.edu.api.domain.profile.models.MenteeProfile;
import inno.edu.api.domain.profile.repositories.MenteeProfileRepository;
import inno.edu.api.infrastructure.annotations.Command;

import static java.util.UUID.randomUUID;

@Command
public class CreateMenteeProfileCommand {
    private final MenteeProfileRepository profileRepository;

    public CreateMenteeProfileCommand(MenteeProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public MenteeProfile run(MenteeProfile profile) {
        if (profileRepository.existsByMenteeId(profile.getMenteeId())) {
            throw new MenteeProfileAlreadyCreatedException(profile.getMenteeId());
        }

        profile.setId(randomUUID());
        return profileRepository.save(profile);
    }
}
