package com.ngocduc.projectspringboot.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUser {
    private String fullName;
    private String phone;
    private String address;

}
