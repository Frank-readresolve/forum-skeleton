package fr.formation.training.forum.dtos;

import javax.validation.constraints.*;

import fr.formation.training.forum.NotSameNameAndRating;

@NotSameNameAndRating
public class TechnologyAddDto {

    @NotBlank
    @Size(max = 50)
    private String name;

    @Positive
    private double rating;

    public String getName() {
	return name;
    }

    public double getRating() {
	return rating;
    }
}
