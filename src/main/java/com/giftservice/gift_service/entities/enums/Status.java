package com.giftservice.gift_service.entities.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {

    AVAILABLE("Доступно"), UNAVAILABLE("Недоступно");

    private final String title;
}
