package utility.auxiliary;

import stored_classes.Flat;

/**
 * Преобразует объекты в формат .csv
 */

public class Unparser {
    /**
     * Преобразует объект класса Flat в формат .csv
     * @param flat Преобразуемый объект
     * @return .csv строка
     */
    public static String FlatToCSV(Flat flat) {
        return flat.getId() + "," + flat.getName() + "," + flat.getCoordinates().getX() + "," + flat.getCoordinates().getY()
                + "," + flat.getCreationDate() + "," + flat.getArea() + "," + flat.getNumberOfRooms() + "," + flat.getFurnish()
                + "," + flat.getView() + "," + flat.getTransport() + "," + flat.getHouse().getName() + "," + flat.getHouse().getYear()
                + "," + flat.getHouse().getNumberOfFloors() + "," + flat.getHouse().getNumberOfLifts() + "\n";
    }
}
