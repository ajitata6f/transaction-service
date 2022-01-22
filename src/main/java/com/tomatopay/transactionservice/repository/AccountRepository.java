package com.tomatopay.transactionservice.repository;

import com.tomatopay.transactionservice.model.Account;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<Account, Integer> {

}