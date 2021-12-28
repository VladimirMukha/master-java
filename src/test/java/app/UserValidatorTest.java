package app;

import app.model.User;
import app.model.enums.UserRole;
import app.model.enums.UserStatus;
import app.service.UserService;
import app.validators.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class UserValidatorTest {

    @InjectMocks
    private UserValidator userValidator;
    @Mock
    private UserService userService;


    private static User user;
    private static Errors errors;

    private static final String FIRST_NAME_VALID = "blue";
    private static final String FIRST_NAME_INVALID = "1blue";
    private static final String LAST_NAME_VALID = "blue";
    private static final String LAST_NAME_INVALID = "1blue";
    private static final String DATE_VALID = "1999-04-04";
    private static final String DATE_INVALID = "2021-04-04";
    private static final String EMAIL_VALID = "me@mail.ru";
    private static final String EMAIL_INVALID = "memail.ru";
    private static final String PASSWORD_VALID = "11111111";
    private static final String PASSWORD_INVALID = "1111111";
    private static final String PASSWORD_CONFIRM_VALID = "11111111";
    private static final String PASSWORD_CONFIRM_INVALID = "11111112";
    private static final String EMPTY_STRING = "";

    @BeforeEach
    public void setUp() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        user = User.newBuilder()
                .id(1)
                .dateOfBirth(sdf.parse(DATE_VALID))
                .email(EMAIL_VALID)
                .firstName(FIRST_NAME_VALID)
                .lastName(LAST_NAME_VALID)
                .password(PASSWORD_VALID)
                .passwordConfirm(PASSWORD_CONFIRM_VALID)
                .role(UserRole.USER)
                .status(UserStatus.ACTIVE)
                .build();

        errors = new BeanPropertyBindingResult(user, "user");
    }

    @Test
    public void validateUserValid() {
        userValidator.validate(user, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validateUserFirstNameEmpty() {
        user.setFirstName(EMPTY_STRING);
        userValidator.validate(user, errors);
        assertNotNull(errors.getFieldError("firstName"));
    }
}
