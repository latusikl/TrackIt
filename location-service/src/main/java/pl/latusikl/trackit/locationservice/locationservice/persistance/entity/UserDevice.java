package pl.latusikl.trackit.locationservice.locationservice.persistance.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user_devices")
@NoArgsConstructor
public class UserDevice {

	@Id
	@Column(name = "device_id", nullable = false, updatable = false, insertable = false, unique = true)
	private String deviceId;

	@Column(name = "device_name", nullable = false, length = 25)
	private String deviceName;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id", updatable = false)
	private UserData userData;

	@Enumerated(EnumType.STRING)
	@Column(name = "device_status", nullable = false)
	private DeviceStatus deviceStatus;

	@Builder
	public UserDevice(final String deviceId, final String deviceName, final DeviceStatus deviceStatus) {
		this.deviceId = deviceId;
		this.deviceName = deviceName;
		this.deviceStatus = deviceStatus;
	}
}
