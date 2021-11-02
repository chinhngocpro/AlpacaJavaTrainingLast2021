package vn.alpaca.common.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class PaymentFilter {

  private Double minAmount;
  private Double maxAmount;
  private Date fromDate;
  private Date toDate;

  private Pagination pagination = new Pagination(5);
}
