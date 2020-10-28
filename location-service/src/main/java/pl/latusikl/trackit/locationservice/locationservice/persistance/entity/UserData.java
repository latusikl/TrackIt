package pl.latusikl.trackit.locationservice.locationservice.persistance.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import pl.latusikl.trackit.locationservice.locationservice.persistance.types.PostgresIdAsUuid;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "user_data")
@NoArgsConstructor
@TypeDefs({@TypeDef(name = "pg-id-uuid", typeClass = PostgresIdAsUuid.class)})
public class UserData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", insertable = false, updatable = false, unique = true)
	@Type(type = "pg-id-uuid", parameters = @Parameter(name = "column", value = "user_id"))
	private UUID userId;

	@Column(name = "user_email", nullable = false, length = 100)
	private String userEmail;

	@Column(name = "password", nullable = false, length = 250)
	private String password;

	@Builder
	public UserData(final String userEmail, final String password) {
		this.userEmail = userEmail;
		this.password = password;
	}
}
