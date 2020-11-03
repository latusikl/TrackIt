/* eslint-disable */

class DecimalDegreesConverter {

    degreeSymbol = "\u00B0";
    minutesSymbol = "\u2032";
    north = "N";
    south = "S";
    east = "E";
    west = "W";


    convertLongitude(decimalDegrees: string) {
        const decimalDegreesNumber = Number(decimalDegrees);
        if (decimalDegreesNumber < 0) {
            return this.convertDegrees(decimalDegreesNumber) + this.west;
        }
        return this.convertDegrees(decimalDegreesNumber) + this.east;
    }

    convertLatitude(decimalDegrees: string) {
        const decimalDegreesNumber = Number(decimalDegrees);
        if (decimalDegreesNumber < 0) {
            return this.convertDegrees(decimalDegreesNumber) + this.south;
        }
        return this.convertDegrees(decimalDegreesNumber) + this.north;
    }


    private convertDegrees(decimalDegrees: number): string {
        decimalDegrees = Math.abs(decimalDegrees);
        const degrees = Math.floor(decimalDegrees);
        const minutes = (decimalDegrees - degrees) * 60;
        return `${degrees}${this.degreeSymbol} ${minutes.toFixed()}${this.minutesSymbol}`;
    }

}

export default new DecimalDegreesConverter();
