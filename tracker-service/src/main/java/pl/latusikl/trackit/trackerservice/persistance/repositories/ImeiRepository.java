package pl.latusikl.trackit.trackerservice.persistance.repositories;

public interface ImeiRepository {

	Long saveImeiToWhitelisted(final String... imei);

	Boolean isImeiWhitelisted(final String imei);

	Long removeImei(final String... imei);
}
