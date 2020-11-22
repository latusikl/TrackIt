package pl.latusikl.trackit.locationservice.locationservice.web.dto;

import lombok.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Value
public class MonthYearDto {

	@NotNull
	@Min(value = 2015, message = "Invalid year value.")
	private int year;

	@NotNull
	@Min(value = 0, message = "Invalid month value")
	@Max(value = 12, message = "Invalid month value")
	private int month;
}
