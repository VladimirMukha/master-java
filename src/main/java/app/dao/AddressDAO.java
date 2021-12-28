package app.dao;

import app.model.Address;

public interface AddressDAO {
    Address edit(Address address);
    Address findAddressById(Integer id);
    Integer addAddress(Address address);
}
