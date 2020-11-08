package pl.latusikl.trackit.locationservice.locationservice.persistance.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "device_info", schema = "track_it")
@NoArgsConstructor
public class DeviceInfoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "device_info_id", nullable = false, updatable = false, insertable = false, unique = true)
	private Long id;

	@Column(name = "device_id", nullable = false, length = 30)
	private String deviceId;

	@Column(name = "message", nullable = false, length = 300)
	private String message;

	@Column(name = "server_date_time")
	private LocalDateTime serverDateTime;

	@Column(name = "info_level")
	@Enumerated(EnumType.STRING)
	private InfoLevel infoLevel;

	@Builder
	public DeviceInfoEntity(final String deviceId, final String message, final LocalDateTime serverDateTime,
							final InfoLevel infoLevel) {
		this.deviceId = deviceId;
		this.message = message;
		this.serverDateTime = serverDateTime;
		this.infoLevel = infoLevel;
	}
}
