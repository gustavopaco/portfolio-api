package com.pacoprojects.portfolio.dto;

import java.io.Serializable;

/**
 * DTO for {@link com.pacoprojects.portfolio.model.Social}
 */
public record SocialDto(Long id,
                        String facebook,
                        String instagram,
                        String linkedin,
                        String github,
                        String twitter,
                        String youtube) implements Serializable {
}
