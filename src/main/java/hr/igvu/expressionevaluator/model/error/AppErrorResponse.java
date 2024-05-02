package hr.igvu.expressionevaluator.model.error;


import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class AppErrorResponse {

    private int status;
    private String timestamp;
    private String title;
    private String path;

    @Builder.Default
    List<String> errors = new ArrayList<>();
}