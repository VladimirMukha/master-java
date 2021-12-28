package app;

import app.dao.AddressDAOImpl;
import app.model.Address;
import app.service.AddressServiceImpl;
import app.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @InjectMocks
    private AddressServiceImpl addressService;
    @Mock
    private AddressDAOImpl addressDAO;
    @Mock
    private UserServiceImpl userService;

    private static Address address;

    @BeforeEach
    public void setUp() {
        address = Address.newBuilder()
                .id(1)
                .country("Russia")
                .city("Moscow")
                .index(456345)
                .street("Street")
                .building(100)
                .room(10)
                .userId(1)
                .build();
    }

    @Test
    public void testFindAddress() {
        given(addressDAO.findAddressById(address.getId())).willReturn(address);
        Address expected = addressService.findAddressById(address.getId());
        assertNotNull(expected);
    }

    @Test
    public void testEditAddress() {
        given(addressDAO.edit(address)).willReturn(address);
        Address expected = addressService.edit(address);
        assertNotNull(expected);
        verify(addressDAO).edit(any(Address.class));
    }

    @Test
    public void testAddAddress() {
        given(addressDAO.addAddress(address)).willReturn(address.getId());
        Integer idExpected = addressService.addAddress(address);
        assertNotNull(idExpected);
        verify(addressDAO).addAddress(any(Address.class));
    }
}
