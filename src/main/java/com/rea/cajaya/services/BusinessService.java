package com.rea.cajaya.services;

import com.rea.cajaya.dtos.business.CreateBusinessRequest;
import com.rea.cajaya.models.Business;

public interface BusinessService {
    Business createBusiness(CreateBusinessRequest request);
}
