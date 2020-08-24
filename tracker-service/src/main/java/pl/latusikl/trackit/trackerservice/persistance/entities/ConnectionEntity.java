package pl.latusikl.trackit.trackerservice.persistance.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("connection")
@Data
@Builder
public class ConnectionEntity
{
    @Id
    private String connectionId;

    @Indexed
    private String deviceImei;

}
