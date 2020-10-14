package pl.latusikl.trackit.trackerservice.server.common.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor(onConstructor_ = {@JsonCreator})
public class CoordinatesDto {
	CardinalDirection direction;
	int degrees;
	double decimalMinutes;
}
