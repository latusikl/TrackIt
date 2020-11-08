package pl.latusikl.trackit.locationservice.locationservice.persistance.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "location_data", schema = "track_it")
@NoArgsConstructor
public class LocationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "location_id", nullable = false, updatable = false, insertable = false, unique = true)
	private Long id;

	@Column(name = "device_id", nullable = false, length = 30)
	private String deviceId;

	@Column(name = "date_time_start", nullable = false)
	private LocalDateTime dateTimeStart;

	@Column(name = "longitude", nullable = false)
	private Double longitude;

	@Column(name = "latitude", nullable = false)
	private Double latitude;

	@Builder
	public LocationEntity(final String device_id, final LocalDateTime dateTimeStart, final Double longitude, final Double latitude) {
		this.deviceId = device_id;
		this.dateTimeStart = dateTimeStart;
		this.longitude = longitude;
		this.latitude = latitude;
	}
}
