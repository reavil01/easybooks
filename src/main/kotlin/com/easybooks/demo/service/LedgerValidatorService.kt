package com.easybooks.demo.service

import com.easybooks.demo.web.dto.ledger.LedgerSaveAndUpdateRequestDto
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class LedgerValidatorService {
    fun checkingValidationOfLedger(requestDto: LedgerSaveAndUpdateRequestDto) {
        if (requestDto.unitPrice > 0 &&
            requestDto.price != requestDto.unitPrice * requestDto.quantity
        ) {
            throw IllegalArgumentException("단가와 수량의 곱과 가격이 일치하지 않습니다.")
        }

        if (requestDto.price / 10 != requestDto.vat) {
            throw IllegalArgumentException("부가세가 ${requestDto.price} + ${requestDto.vat} 잘못되었습니다.")
        }

        if (requestDto.price + requestDto.vat != requestDto.total) {
            throw IllegalArgumentException("총액이 잘못되었습니다.")
        }
    }
}