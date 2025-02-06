package com.example.jjangushrine.domain.address.service;

import com.example.jjangushrine.domain.address.dto.request.AddressCreateReq;

import com.example.jjangushrine.domain.address.dto.request.AddressUpdateReq;
import com.example.jjangushrine.domain.address.dto.response.AddressListRes;
import com.example.jjangushrine.domain.address.dto.response.AddressRes;
import com.example.jjangushrine.domain.address.entity.Address;
import com.example.jjangushrine.domain.address.repository.AddressRepository;
import com.example.jjangushrine.domain.user.entity.User;
import com.example.jjangushrine.domain.user.enums.UserRole;
import com.example.jjangushrine.domain.user.service.UserService;
import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.common.ConflictException;
import com.example.jjangushrine.exception.common.ForbiddenException;
import com.example.jjangushrine.exception.common.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserService userService;

    @Transactional
    public void createAddress(AddressCreateReq addressCreateReq, String email) {
        User user = userService.findUserByEmail(email);
        Address address = addressCreateReq.to(user);

        setDefaultIfFirstAddress(address, user);

        addressRepository.save(address);
    }

    public AddressRes getAddressInfo(Long addressId, String email) {
        User user = userService.findUserByEmail(email);
        Address address = findAddressById(addressId);
        validateUser(address, user);

        return AddressRes.from(address);
    }

    public AddressListRes getAddressList(Pageable pageable, String email) {
        User user = userService.findUserByEmail(email);

        List<AddressRes> addresses = addressRepository
                .findAllByUserIdAndIsDeletedIsFalse(user.getId(), pageable)
                .map(AddressRes::from)
                .getContent();

        return AddressListRes.builder()
                .addresses(addresses)
                .build();
    }

    @Transactional
    public AddressRes updateAddress(AddressUpdateReq updateReq, Long addressId, String email) {
        User user = userService.findUserByEmail(email);
        Address address = findAddressById(addressId);
        validateUser(address, user);

        address.update(updateReq);

        return AddressRes.from(address);
    }

    @Transactional
    public void setDefaultAddress(Long addressId, String email) {
        User user = userService.findUserByEmail(email);
        Address address = findAddressById(addressId);
        validateUser(address, user);

        if (address.getIsDefault()) {
            throw new ConflictException(ErrorCode.DUPLICATE_DEFAULT);
        }

        addressRepository.setAllAddressesToNonDefault(user.getId());
        address.setDefault();
    }

    @Transactional
    public void deleteAddress(Long addressId, String email) {
        User user = userService.findUserByEmail(email);
        Address address = findAddressById(addressId);
        validateUser(address, user);

        address.softDelete();
    }

    private Address findAddressById(Long addressId) {
        return addressRepository.findByIdAndIsDeletedIsFalse(addressId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ADDRESS_NOT_FOUND));
    }

    private void validateUser(Address address, User user) {
        if (!address.getId().equals(user.getId())) {
            throw new ForbiddenException(ErrorCode.FORBIDDEN_ACCESS);
        }
    }

    private void setDefaultIfFirstAddress(Address address, User user) {
        boolean isFirstAddress = addressRepository
                .countByIdAndIsDeletedIsFalse(user.getId()) == 0;

        if (isFirstAddress) {
            address.setDefault();
        }
    }

    public Address findByUserId(Long ownerId, UserRole userRole) {
        return addressRepository.findByIdAndIsDefaultIsTrue(ownerId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ADDRESS_NOT_FOUND));
    }
}
