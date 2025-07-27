package com.api.orderms.controller.dto;

import java.util.List;
import java.util.Map;

public record ApiResonse<T>(Map<String, Object> summary,
                            List<T> data,
                            PaginationResponse pagination) {

}
