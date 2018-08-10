package com.yzn.sport.buyer;

import com.yzn.sport.pojo.Buyer;

public interface BuyerService {
    Buyer selectByUsername(String username);
}
