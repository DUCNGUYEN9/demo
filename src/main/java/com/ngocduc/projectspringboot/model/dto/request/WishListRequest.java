package com.ngocduc.projectspringboot.model.dto.request;

import com.ngocduc.projectspringboot.model.entity.Products;
import com.ngocduc.projectspringboot.model.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishListRequest {
    private long users;
    private long products;
}
