package pl.latusikl.trackit.locationservice.locationservice.persistance.types;

import org.hibernate.engine.jdbc.LobCreator;
import org.hibernate.id.IdentifierGenerationException;
import org.hibernate.id.ResultSetIdentifierConsumer;
import org.hibernate.type.PostgresUUIDType;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
import org.hibernate.usertype.ParameterizedType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.TimeZone;
import java.util.UUID;

public class PostgresIdAsUuid extends PostgresUUIDType implements ResultSetIdentifierConsumer, ParameterizedType {

	private String idColumn = "id";

	@Override
	public String getName() {
		return "pg-id-uuid";
	}

	@Override
	public void setParameterValues(Properties params) {
		idColumn = params.getProperty("column");
	}

	@Override
	public UUID consumeIdentifier(ResultSet resultSet) throws IdentifierGenerationException {
		try {
			return nullSafeGet(resultSet, idColumn, wrapperOptions());
		}
		catch (SQLException e) {
			throw new IdentifierGenerationException("Error converting type", e);
		}
	}

	private WrapperOptions wrapperOptions() {
		return new WrapperOptions() {
			@Override
			public boolean useStreamForLobBinding() {
				return false;
			}

			@Override
			public LobCreator getLobCreator() {
				return null;
			}

			@Override
			public SqlTypeDescriptor remapSqlTypeDescriptor(final SqlTypeDescriptor sqlTypeDescriptor) {
				return PostgresUUIDSqlTypeDescriptor.INSTANCE;
			}

			@Override
			public TimeZone getJdbcTimeZone() {
				return TimeZone.getDefault();
			}
		};
	}
}
