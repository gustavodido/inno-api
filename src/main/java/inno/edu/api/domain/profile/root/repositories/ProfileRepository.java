package inno.edu.api.domain.profile.root.repositories;

import inno.edu.api.domain.profile.root.models.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ProfileRepository extends CrudRepository<Profile, UUID> {
    boolean existsByUserId(UUID userId);
    Profile findOneByUserId(UUID userId);
    List<Profile> findBySchoolId(UUID schoolId);
}
