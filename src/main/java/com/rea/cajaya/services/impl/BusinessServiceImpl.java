package com.rea.cajaya.services.impl;

import com.rea.cajaya.dtos.business.CreateBusinessRequest;
import com.rea.cajaya.models.Business;
import com.rea.cajaya.repository.BusinessRepository;
import com.rea.cajaya.services.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RequiredArgsConstructor
@Service
public class BusinessServiceImpl implements BusinessService {
    private final BusinessRepository businessRepository;

    @Override
    public Business createBusiness(CreateBusinessRequest request) {
        Business business = new Business();
        business.setName(request.getName());
        business.setPlan(request.getPlan());
        business.setCreated_at(
                LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires"))
        );

        return businessRepository.save(business);
    }
}
