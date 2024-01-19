package com.ngocduc.projectspringboot.mapper;

import com.ngocduc.projectspringboot.model.dto.request.AddressRequest;
import com.ngocduc.projectspringboot.model.dto.response.AddressResponse;
import com.ngocduc.projectspringboot.model.entity.Address;

import org.springframework.stereotype.Component;

@Component
public class MapperAddress implements  MapperGeneric<Address, AddressRequest, AddressResponse>{

    @Override
    public Address mapperRequestToEntity(AddressRequest addressRequest) {
        return null;
    }

    @Override
    public AddressResponse mapperEntityToResponse(Address address) {
        return AddressResponse.builder()
                .userId(address.getUsers().getId())
                .addressId(address.getAddress_id())
                .fullAddress(address.getFull_address())
                .phone(address.getPhone())
                .receiveName(address.getReceive_name())
                .build();
    }
}
