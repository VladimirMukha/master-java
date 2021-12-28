package app.service;

import app.dao.AddressDAO;
import app.model.Address;
import app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressServiceImpl implements AddressService{
    private AddressDAO addressDAO;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAddressDAO(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }

    @Transactional
    @Override
    public Address findAddressById(Integer id) {
        return addressDAO.findAddressById(id);
    }

    @Transactional
    @Override
    public Integer addAddress(Address address) {
        User user = userService.findById(address.getUserId());
        address.setUser(user);
        return addressDAO.addAddress(address);
    }

    @Transactional
    @Override
    public Address edit(Address address) {
        User user = userService.findById(address.getUserId());
        address.setUser(user);
        return addressDAO.edit(address);
    }
}
