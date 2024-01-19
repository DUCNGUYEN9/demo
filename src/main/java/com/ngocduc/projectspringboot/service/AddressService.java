package com.ngocduc.projectspringboot.service;

import com.ngocduc.projectspringboot.model.dto.request.AddressRequest;
import com.ngocduc.projectspringboot.model.dto.response.AddressResponse;

import java.util.List;

public interface AddressService {
    AddressResponse addNew(long userId, AddressRequest addressRequest);
    List<AddressResponse> getAllListAddress(long userId);
    AddressResponse findByUsers_IdAndAddress_id(long userId,long addressId);
}
