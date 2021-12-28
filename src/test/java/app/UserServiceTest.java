package app;

import app.dao.UserDAOImpl;
import app.model.User;
import app.model.enums.UserRole;
import app.model.enums.UserStatus;
import app.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserDAOImpl userDAO;

    private static User user;

    @BeforeEach
    public void setUp() {
        user = User.newBuilder()
                .id(1)
                .dateOfBirth(new Date())
                .email("mail@mail.com")
                .firstName("Vladimir")
                .lastName("Mukha")
                .password("11111111")
                .passwordConfirm("11111111")
                .role(UserRole.USER)
                .status(UserStatus.ACTIVE)
                .build();
    }

    @Test
    public void testFindUserById() {
        given(userDAO.findById(user.getId())).willReturn(user);
        User expected = userService.findById(user.getId());
        assertNotNull(expected);
    }

    @Test
    public void testFindUserByEmail() {
        given(userDAO.findByEmail(user.getEmail())).willReturn(user);
        User expected = userService.findByEmail(user.getEmail());
        assertNotNull(expected);
    }

    @Test
    public void testGetAllUsers() throws ParseException {
        User newUser = User.newBuilder()
                .id(2)
                .dateOfBirth(new Date())
                .email("maill@mail.com")
                .firstName("Vladimirr")
                .lastName("Mukhaa")
                .password("11111111")
                .passwordConfirm("11111111")
                .role(UserRole.USER)
                .status(UserStatus.ACTIVE)
                .build();

        List<User> list = new ArrayList<>();
        list.add(user);
        list.add(newUser);

        given(userDAO.findAll()).willReturn(list);
        List<User> expected = userService.allUsers();
        assertEquals(expected, list);
    }


}
