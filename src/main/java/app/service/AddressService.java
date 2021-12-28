package app.service;

import app.model.Address;
import app.model.Product;
import app.model.User;

public interface AddressService {
    Address findAddressById(Integer id);
    Integer addAddress(Address address);
    Address edit(Address address);
}
