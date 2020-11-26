/* eslint-disable */
class TimeService {

    private static TIME_SEPARATOR = "T";
    private static SECONDS_AND_MILI = ":00.000Z";
    private static MILLISECONDS_PER_HOUR = 3_600_000
    private static MILLISECONDS_PER_MINUTE = 60_000

    convertToDateTimeIsoStringAsInUtc(
        dateString: string,
        timeString: string
    ): string {
        return dateString + TimeService.TIME_SEPARATOR + timeString + TimeService.SECONDS_AND_MILI;
    }

    currentDate(): string {
        return new Date().toLocaleDateString().split("/").reverse().join("-");
    }

    currentUserTime(): string {
        return new Date().toLocaleTimeString().substr(0, 5);
    }

    currentUserTimePlusMinute(): string {
        return new Date(new Date().getTime() + TimeService.MILLISECONDS_PER_MINUTE).toLocaleTimeString().substr(0, 5);
    }

    getHoursInRange(dateStart: string, timeStart: string, dateEnd: string, timeEnd: string): number {
        const startDateTime = this.getDate(dateStart, timeStart);
        const endDateTime = this.getDate(dateEnd, timeEnd);

        return (endDateTime.getTime() - startDateTime.getTime()) / TimeService.MILLISECONDS_PER_HOUR
    }

    isFirstDateTimeBiggerOrEqual(dateStart: string, timeStart: string, dateEnd: string, timeEnd: string): boolean {
        const startDateTime = this.getDate(dateStart, timeStart);
        const endDateTime = this.getDate(dateEnd, timeEnd);

        return startDateTime >= endDateTime;
    }

    getDate(date: string, time: string): Date {
        return new Date(this.convertToDateTimeIsoStringAsInUtc(date, time));
    }

    withAddedHours(date: string, time: string, hours: number): string {
        const value = this.getDate(date, time).getTime() + hours * TimeService.MILLISECONDS_PER_HOUR;
        return new Date(value).toISOString();
    }

}

export default new TimeService();
