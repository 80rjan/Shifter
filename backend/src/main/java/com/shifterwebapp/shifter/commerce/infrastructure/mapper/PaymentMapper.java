//package com.shifterwebapp.shifter.commerce.domain.mapper;
//
//import com.shifterwebapp.shifter.commerce.domain.Payment;
//import com.shifterwebapp.shifter.commerce.web.response.PaymentResponse;
//import org.mapstruct.InheritInverseConfiguration;
//import org.mapstruct.Mapper;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring")
//public interface PaymentMapper {
//
//    PaymentResponse toDto(Payment payment);
//    List<PaymentResponse> toDto(List<Payment> payments);
//
//    @InheritInverseConfiguration
//    Payment toEntity(PaymentResponse paymentDto);
//    @InheritInverseConfiguration
//    List<Payment> toEntity(List<PaymentResponse> paymentDtos);
//}
