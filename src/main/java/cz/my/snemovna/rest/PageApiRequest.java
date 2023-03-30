package cz.my.snemovna.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * The class for api pagination support.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageApiRequest implements Serializable {

    private int page;
    private int size;
    private String property;
    private Sort.Direction direction;
}
