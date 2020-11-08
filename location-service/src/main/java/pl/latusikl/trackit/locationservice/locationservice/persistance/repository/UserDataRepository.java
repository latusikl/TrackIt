package pl.latusikl.trackit.locationservice.locationservice.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import pl.latusikl.trackit.locationservice.locationservice.persistance.entity.UserData;

import java.util.Optional;
import java.util.UUID;

public interface UserDataRepository extends CrudRepository<UserData, UUID> {
	boolean existsByUserEmail(String userEmail);

	Optional<UserData> findByUserEmail(String userEmail);
	void deleteByUserId(UUID userId);
}
