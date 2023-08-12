package com.likelion.daroomi.nuroomi.dto;

import com.likelion.daroomi.nuroomi.domain.Options;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@AllArgsConstructor
@Getter
public class UsePointRequestDto {

    private Long consultantId;
    private Integer usePoint;
    private Options options;
    private Timestamp createTime;
}
