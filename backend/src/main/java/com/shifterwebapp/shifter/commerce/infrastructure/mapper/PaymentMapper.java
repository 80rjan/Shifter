//package com.shifterwebapp.shifter.commerce.domain.mapper;
//
//import com.shifterwebapp.shifter.commerce.domain.Payment;
//import com.shifterwebapp.shifter.commerce.web.response.PaymentDto;
//import org.mapstruct.InheritInverseConfiguration;
//import org.mapstruct.Mapper;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring")
//public interface PaymentMapper {
//
//    PaymentDto toDto(Payment payment);
//    List<PaymentDto> toDto(List<Payment> payments);
//
//    @InheritInverseConfiguration
//    Payment toEntity(PaymentDto paymentDto);
//    @InheritInverseConfiguration
//    List<Payment> toEntity(List<PaymentDto> paymentDtos);
//}
