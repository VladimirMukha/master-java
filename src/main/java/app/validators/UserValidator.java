package app.validators;

import app.model.User;
import app.service.UserService;
import app.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Calendar;
import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {
    private UserService userService;

    @Autowired
    public void setUserService(@Qualifier("userService") UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        Pattern validName = Pattern.compile("^[a-zA-Z]+$");
        if (user.getFirstName() == null || user.getFirstName().equals("")) {
            errors.rejectValue("firstName", "NotEmpty");
        }
        //(user.getFirstName().length() > 1 && user.getFirstName().length() < 30
        //                && validName.matcher(user.getFirstName()).find()) в приватный метод (isFirstNameValid)
        else if (!(user.getFirstName().length() > 1 && user.getFirstName().length() < 30
                && validName.matcher(user.getFirstName()).find())) {
            errors.rejectValue("firstName", "firstName[invalidValue]");
        }

        if (user.getLastName() == null || user.getLastName().equals("")) {
            errors.rejectValue("lastName", "NotEmpty");
        } else if (!(user.getFirstName().length() > 1 && user.getFirstName().length() < 30
                && validName.matcher(user.getLastName()).find())) {
            errors.rejectValue("lastName", "lastName[invalidValue]");
        }

        if (user.getDateOfBirth() == null) {
            errors.rejectValue("dateOfBirth", "NotEmpty");
        } else {
            Calendar currentDate = Calendar.getInstance();
            Calendar birthday = Calendar.getInstance();
            birthday.setTime(user.getDateOfBirth());
            if ((currentDate.get(Calendar.YEAR) == birthday.get(Calendar.YEAR) &&
                    currentDate.get(Calendar.DAY_OF_YEAR) <= birthday.get(Calendar.DAY_OF_YEAR) &&
                    currentDate.get(Calendar.MONTH) <= birthday.get(Calendar.MONTH)) ||
                    (currentDate.get(Calendar.YEAR) < birthday.get(Calendar.YEAR))) {
                errors.rejectValue("dateOfBirth", "dateOfBirth[invalidValue]");
            }
        }

        if (user.getEmail() == null || user.getEmail().equals("")) {
            errors.rejectValue("email", "NotEmpty");
        }
        if (userService.findByEmail(user.getEmail()) != null) {
            if (!AppUtils.isAuthUser()) {
                errors.rejectValue("email", "email[duplicate]");
            }
        }
        Pattern validEmailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE);
        if (!validEmailRegex.matcher(user.getEmail()).find()) {
            errors.rejectValue("email", "email[invalidValue]");
        }

        if (user.getPassword() == null || user.getPassword().equals("")) {
            errors.rejectValue("password", "NotEmpty");
        } else if (user.getPassword().length() < 8 || user.getPassword().length() > 30) {
            errors.rejectValue("password", "password[invalidLength]");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "password[invalidConfirmation]");
        }
    }
}
