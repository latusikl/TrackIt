package pl.latusikl.trackit.trackerservice.persistance.repositories;

public interface ImeiRepository {
	void saveImeiToWhitelisted(final String... imei);

	Boolean isImeiWhitelisted(final String imei);

	void removeImei(final String... imei);
}
