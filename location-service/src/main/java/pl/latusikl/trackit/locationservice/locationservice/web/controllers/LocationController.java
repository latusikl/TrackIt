package pl.latusikl.trackit.locationservice.locationservice.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.LastLocationDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.LocationRangeDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.PointDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.geojson.LineStringGeometry;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.geojson.MapFeatureCollectionDto;
import pl.latusikl.trackit.locationservice.locationservice.web.dto.geojson.MapLineStringFeatureDto;
import pl.latusikl.trackit.locationservice.locationservice.web.service.LocationControllerService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {

	private final LocationControllerService locationControllerService;

	@GetMapping("/{deviceId}/last")
	@ResponseStatus(HttpStatus.OK)
	public LastLocationDto getLastKnownDeviceLocation(@PathVariable final String deviceId) {
		return locationControllerService.getLastKnown(deviceId);
	}

	@GetMapping("/{deviceId}")
	@ResponseStatus(HttpStatus.OK)
	public LocationRangeDto getFromTimeRange(@PathVariable final String deviceId,
											 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam final LocalDateTime rangeStart,
											 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam final LocalDateTime rangeEnd) {
		return locationControllerService.getFromRange(deviceId, rangeStart, rangeEnd);
	}

	@GetMapping("test")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public MapFeatureCollectionDto getTest() {
		var line = MapLineStringFeatureDto.builder()
										  .geometry(new LineStringGeometry(
												  List.of(PointDto.of(50.1, 18.1), PointDto.of(50.2, 18.2), PointDto.of(50.3, 18.3))))
										  .id(UUID.randomUUID()
												  .toString())
										  .build();

		return new MapFeatureCollectionDto(List.of(new ObjectMapper().valueToTree(line)));
	}

}
