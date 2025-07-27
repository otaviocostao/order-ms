package com.api.orderms.controller.dto;

import java.util.List;

public record ApiResonse<T>(List<T> data, PaginationResponse pagination) {

}
