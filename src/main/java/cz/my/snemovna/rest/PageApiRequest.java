package cz.my.snemovna.rest;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import javax.ws.rs.QueryParam;
import java.io.Serializable;

/**
 * The class for api pagination support.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageApiRequest implements Serializable {

    @QueryParam("page")
    @NotNull
    @Min(0)
    private int page;

    @NotNull
    @Min(0)
    @QueryParam("size")
    private int size;

    @NotNull
    @QueryParam("property")
    private String property;

    @NotNull
    @QueryParam("direction")
    private Sort.Direction direction;
}
