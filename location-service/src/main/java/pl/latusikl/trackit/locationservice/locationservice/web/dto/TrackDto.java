package pl.latusikl.trackit.locationservice.locationservice.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(staticName = "of")
public class TrackDto {

	private LocalDateTime start;
	private LocalDateTime end;
	private String name;

}
