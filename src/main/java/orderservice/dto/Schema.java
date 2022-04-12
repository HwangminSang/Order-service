package orderservice.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;

//각각
@Data
@Builder
public class Schema {

        private  String type;
        private List<Field> fields;
        private  boolean optional;
        private  String name;
}
