package vn.alpaca.alpacajavatraininglast2021.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateUtil {

    public Date convertStringToDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd")
                    .parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }
}
