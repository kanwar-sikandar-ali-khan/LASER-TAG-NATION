package com.lasertagnation.dto;

import com.lasertagnation.model.Vendor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaperMarketRequestBody {
    private String paperStock;
    private String brand;
    private String madeIn;
    private String dimension;
    private String gsm;
    private Vendor vendor;
}
