package utility.auxiliary;

import stored_classes.Flat;

import java.util.TreeSet;

public class Validator {
    /**
     * Проверяет валидность полей экземпляра класса Flat
     * @param flat Проверяемый экземпляр класса Flat
     * @return true, если все поля валидны; false в противном случае
     */
    public static boolean validate(Flat flat) {
        return flat.getId() > 0 && !flat.getName().isEmpty() && flat.getName() != null && flat.getCoordinates() != null && flat.getCreationDate() != null &&
                flat.getArea() > 0 && flat.getNumberOfRooms() > 0 && flat.getFurnish() != null && flat.getView() != null && flat.getTransport() != null &&
                flat.getHouse() != null && flat.getCoordinates().getX() < 600 && flat.getCoordinates().getX() != null && flat.getCoordinates().getY() != null &&
                flat.getHouse().getYear() > 0 && flat.getHouse().getNumberOfFloors() > 0 && flat.getHouse().getNumberOfLifts() > 0;
    }

    /**
     * Проверяет валидность полей каждого экземпляра класса Flat в коллекции типа TreeSet и, в случае невалидности, удаляет элемент из коллекции
     * @param flats Проверяемая коллекция
     */
    public static void validateAll(TreeSet<Flat> flats) {
        flats.removeIf(flat -> !Validator.validate(flat));
    }
}
