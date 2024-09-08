package by.it_academy.jd2.validation.api;

import by.it_academy.jd2.dto.InfoFromUserDTO;
import by.it_academy.jd2.validation.ValidationResult;

public interface IValidateFormInfo {
    ValidationResult valid(InfoFromUserDTO infoFromUserDTO);
}
