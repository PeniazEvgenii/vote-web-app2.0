package by.it_academy.jd2.mapper;

import java.time.*;

public class Tetst {
    public static void main(String[] args) {

        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("UTC-12:00"));
        LocalDate registrationDate = zonedDateTime.toLocalDate(); // сохранение только даты
        System.out.println(registrationDate);

        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime ldt = LocalDateTime.now();
        OffsetDateTime odt = ZonedDateTime.of(ldt, zoneId).toOffsetDateTime();
        System.out.println("offset2  " + odt);

        System.out.println(odt.toZonedDateTime());

        System.out.println(odt.atZoneSameInstant(ZoneId.of("UTC-12:00")));
        System.out.println(ZoneId.systemDefault());  //Europe/Moscow

        System.out.println(OffsetDateTime.now());
    }
}
