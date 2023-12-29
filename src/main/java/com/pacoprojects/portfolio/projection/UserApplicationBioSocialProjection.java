package com.pacoprojects.portfolio.projection;

/**
 * Projection for {@link com.pacoprojects.portfolio.model.UserApplication}
 */
public interface UserApplicationBioSocialProjection {
    BioInfo getBio();

    SocialInfo getSocial();

    /**
     * Projection for {@link com.pacoprojects.portfolio.model.Bio}
     */
    interface BioInfo {
        Long getId();

        String getAvatarUrl();

        String getPresentation();

        String getTestimonial();

        String getJobTitle();

        String getFullName();
    }

    /**
     * Projection for {@link com.pacoprojects.portfolio.model.Social}
     */
    interface SocialInfo {
        Long getId();

        String getFacebook();

        String getInstagram();

        String getLinkedin();

        String getGithub();

        String getTwitter();

        String getYoutube();
    }
}
