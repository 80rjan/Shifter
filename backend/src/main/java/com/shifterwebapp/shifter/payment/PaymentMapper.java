package com.shifterwebapp.shifter.payment;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentDto toDto(Payment payment);
    List<PaymentDto> toDto(List<Payment> payments);

    @InheritInverseConfiguration
    Payment toEntity(PaymentDto paymentDto);
    @InheritInverseConfiguration
    List<Payment> toEntity(List<PaymentDto> paymentDtos);
}
