package com.ngocduc.projectspringboot.service.serviceImp;

import com.ngocduc.projectspringboot.mapper.MapperAddress;
import com.ngocduc.projectspringboot.model.dto.request.AddressRequest;
import com.ngocduc.projectspringboot.model.dto.response.AddressResponse;
import com.ngocduc.projectspringboot.model.entity.Address;
import com.ngocduc.projectspringboot.model.entity.Users;
import com.ngocduc.projectspringboot.repository.AddressRepository;
import com.ngocduc.projectspringboot.repository.UsersRepository;
import com.ngocduc.projectspringboot.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImp implements AddressService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private MapperAddress mapperAddress;
    @Override
    public AddressResponse addNew(long userId, AddressRequest addressRequest) {
        Users users = usersRepository.findById(userId).get();
        Address address = new Address();
        address.setFull_address(addressRequest.getFullAddress());
        address.setPhone(addressRequest.getPhone());
        address.setUsers(users);
        address.setReceive_name(addressRequest.getReceiveName());
        return mapperAddress.mapperEntityToResponse(addressRepository.save(address));
    }

    @Override
    public List<AddressResponse> getAllListAddress(long userId) {
        Users users = usersRepository.findById(userId).get();

        List<Address> addressList = addressRepository.findAllByUsersIs(users);

        List<AddressResponse> addressResponseList = addressList.stream()
                .map(address -> mapperAddress.mapperEntityToResponse(address))
                .collect(Collectors.toList());


        return addressResponseList;
    }

    @Override
    public AddressResponse findByUsers_IdAndAddress_id(long userId, long addressId) {
        return mapperAddress.mapperEntityToResponse(addressRepository.findByUsers_IdAndAddress_id(userId,addressId));
    }
}
