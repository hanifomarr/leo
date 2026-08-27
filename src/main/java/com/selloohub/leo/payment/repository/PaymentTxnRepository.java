package com.selloohub.leo.payment.repository;

import com.selloohub.leo.payment.model.PaymentTxn;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentTxnRepository extends MongoRepository<PaymentTxn, String> {

    boolean existsByGatewayRef(String gatewayRef);
}
